package ru.noties.tumbleweed;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * A Timeline can be used to create complex animations made of sequences and
 * parallel sets of Tweens.
 * <p/>
 * <p>
 * The following example will create an animation sequence composed of 5 parts:
 * <p/>
 * <p>
 * 1. First, opacity and scale are set to 0 (with Tween.set() calls).<br/>
 * 2. Then, opacity and scale are animated in parallel.<br/>
 * 3. Then, the animation is paused for 1s.<br/>
 * 4. Then, position is animated to x=100.<br/>
 * 5. Then, rotation is animated to 360°.
 * <p/>
 * <p>
 * This animation will be repeated 5 times, with a 500ms delay between each
 * iteration:
 * <br/><br/>
 * <p>
 * <pre> {@code
 * Timeline.createSequence()
 *     .push(Tween.set(myObject, OPACITY).target(0))
 *     .push(Tween.set(myObject, SCALE).target(0, 0))
 *     .beginParallel()
 *          .push(Tween.to(myObject, OPACITY, 0.5f).target(1).ease(Quad.INOUT))
 *          .push(Tween.to(myObject, SCALE, 0.5f).target(1, 1).ease(Quad.INOUT))
 *     .end()
 *     .pushPause(1.0f)
 *     .push(Tween.to(myObject, POSITION_X, 0.5f).target(100).ease(Quad.INOUT))
 *     .push(Tween.to(myObject, ROTATION, 0.5f).target(360).ease(Quad.INOUT))
 *     .repeat(5, 0.5f)
 *     .start(myManager);
 * }</pre>
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 * @see Tween
 * @see TweenManager
 * @see TweenCallback
 */
public final class Timeline extends BaseTween {

    /**
     * Creates a new timeline with a 'sequence' behavior. Its children will
     * be delayed so that they are triggered one after the other.
     */
    @NonNull
    public static TimelineDef createSequence() {
        return new TimelineDefImpl(TimelineDefImpl.Mode.SEQUENCE);
    }

    /**
     * Creates a new timeline with a 'parallel' behavior. Its children will be
     * triggered all at once.
     */
    @SuppressWarnings("unused")
    @NonNull
    public static TimelineDef createParallel() {
        return new TimelineDefImpl(TimelineDefImpl.Mode.PARALLEL);
    }

    private final float duration;
    private final List<BaseTween> children;

    Timeline(@NonNull BaseTweenDefImpl impl, float duration, @NonNull List<BaseTween> children) {
        super(impl);
        this.duration = duration;
        this.children = children;
    }

    @NonNull
    @Override
    public Timeline start(@NonNull TweenManager manager) {
        super.start(manager);
        return this;
    }

    @Override
    @NonNull
    public Timeline start() {
        super.start();

        for (int i = 0; i < children.size(); i++) {
            BaseTween obj = children.get(i);
            obj.start();
        }

        return this;
    }

    @Override
    protected void updateOverride(int step, int lastStep, boolean isIterationStep, float delta) {

        if (!isIterationStep && step > lastStep) {

            // do we need it?
            if (!(delta >= 0)) {
                throw new IllegalStateException("Assertion failed, delta >= 0");
            }

            final float dt = isReverse(lastStep) ? -delta - 1 : delta + 1;
            for (int i = 0, n = children.size(); i < n; i++) {
                children.get(i).update(dt);
            }

            return;
        }

        if (!isIterationStep && step < lastStep) {

            if (!(delta <= 0)) {
                throw new IllegalStateException("Assertion failed, delta <= 0");
            }

            final float dt = isReverse(lastStep) ? delta + 1 : -delta - 1;
            for (int i = children.size() - 1; i >= 0; i--) {
                children.get(i).update(dt);
            }

            return;
        }

        if (!isIterationStep) {
            throw new IllegalStateException("Assertion failed, isIterationStep");
        }

        if (step > lastStep) {
            if (isReverse(step)) {
                forceEndValues();
                for (int i = 0, n = children.size(); i < n; i++) {
                    children.get(i).update(delta);
                }
            } else {
                forceStartValues();
                for (int i = 0, n = children.size(); i < n; i++) {
                    children.get(i).update(delta);
                }
            }

        } else if (step < lastStep) {
            if (isReverse(step)) {
                forceStartValues();
                for (int i = children.size() - 1; i >= 0; i--) {
                    children.get(i).update(delta);
                }
            } else {
                forceEndValues();
                for (int i = children.size() - 1; i >= 0; i--) {
                    children.get(i).update(delta);
                }
            }

        } else {

            float dt = isReverse(step) ? -delta : delta;
            if (delta >= 0) {
                for (int i = 0, n = children.size(); i < n; i++) {
                    children.get(i).update(dt);
                }
            } else {
                for (int i = children.size() - 1; i >= 0; i--) {
                    children.get(i).update(dt);
                }
            }
        }
    }

    @Override
    protected void forceStartValues() {
        for (int i = children.size() - 1; i >= 0; i--) {
            children.get(i).forceToStart();
        }
    }

    @Override
    protected void forceEndValues() {
        for (int i = 0, n = children.size(); i < n; i++) {
            children.get(i).forceToEnd(duration);
        }
    }

    @Override
    public boolean containsTarget(@NonNull Object target) {
        boolean result = false;
        for (BaseTween tween : children) {
            if (tween.containsTarget(target)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
