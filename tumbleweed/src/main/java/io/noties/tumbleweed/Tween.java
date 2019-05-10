package io.noties.tumbleweed;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.noties.tumbleweed.equations.Quad;
import io.noties.tumbleweed.paths.CatmullRom;

/**
 * Core class of the Tween Engine. A Tween is basically an interpolation
 * between two values of an object attribute. However, the main interest of a
 * Tween is that you can apply an easing formula on this interpolation, in
 * order to smooth the transitions or to achieve cool effects like springs or
 * bounces.
 * <p/>
 * <p>
 * The Universal Tween Engine is called "universal" because it is able to apply
 * interpolations on every attribute from every possible object. Therefore,
 * every object in your application can be animated with cool effects: it does
 * not matter if your application is a game, a desktop interface or even a
 * console program! If it makes sense to animate something, then it can be
 * animated through this engine.
 * <p/>
 * <p>
 * This class contains many static factory methods to create and instantiate
 * new interpolations easily. The common way to create a Tween is by using one
 * of these factories:
 * <p/>
 * <p>
 * - Tween.to(...)<br/>
 * - Tween.from(...)<br/>
 * - Tween.set(...)<br/>
 * - Tween.call(...)
 * <p/>
 * <p>
 * <h2>Example - firing a Tween</h2>
 * <p>
 * The following example will move the target horizontal position from its
 * current value to x=200 and y=300, during 500ms, but only after a delay of
 * 1000ms. The animation will also be repeated 2 times (the starting position
 * is registered at the end of the delay, so the animation will automatically
 * restart from this registered position).
 * <p/>
 * <p>
 * <pre> {@code
 * Tween.to(myObject, POSITION_XY, 0.5f)
 *      .target(200, 300)
 *      .ease(Quad.INOUT)
 *      .delay(1.0f)
 *      .repeat(2, 0.2f)
 *      .start(myManager);
 * }</pre>
 * <p>
 * Tween life-cycles can be automatically managed for you, thanks to the
 * {@link TweenManager} class. If you choose to manage your tween when you start
 * it, then you don't need to care about it anymore. <b>Tweens are
 * <i>fire-and-forget</i>: don't think about them anymore once you started
 * them (if they are managed of course).</b>
 * <p/>
 * <p>
 * You need to periodicaly update the tween engine, in order to compute the new
 * values. If your tweens are managed, only update the manager; else you need
 * to call {@link #update(float)} on your tweens periodically.
 * <p/>
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 * @see TweenManager
 * @see TweenEquation
 * @see Timeline
 */
public final class Tween extends BaseTween {

    @SuppressWarnings("unused")
    public static final int INFINITY = -1;

    /**
     * Factory creating a new standard interpolation. This is the most common
     * type of interpolation. The starting values are retrieved automatically
     * after the delay (if any).
     * <br/><br/>
     * <p>
     * <b>You need to set the target values of the interpolation by using one
     * of the target() methods</b>. The interpolation will run from the
     * starting values to these target values.
     * <br/><br/>
     * <p>
     * The common use of Tweens is "fire-and-forget": you do not need to care
     * for tweens once you added them to a TweenManager, they will be updated
     * automatically, and cleaned once finished. Common call:
     * <br/><br/>
     * <p>
     * <pre> {@code
     * Tween.to(myObject, POSITION, 1.0f)
     *      .target(50, 70)
     *      .ease(Quad.INOUT)
     *      .start(myManager);
     * }</pre>
     * <p>
     * Several options such as delay, repetitions and callbacks can be added to
     * the tween.
     *
     * @param target    The target view of the interpolation.
     * @param tweenType The desired type of interpolation.
     * @param duration  The duration of the interpolation, in milliseconds.
     * @return The generated Tween.
     */
    @NonNull
    public static <T> TweenDef<T> to(@NonNull T target, @NonNull TweenType<T> tweenType, float duration) {
        return new TweenDefImpl<>(false, target, tweenType, duration)
                .ease(Quad.INOUT)
                .path(CatmullRom.instance());
    }

    /**
     * Factory creating a new reversed interpolation. The ending values are
     * retrieved automatically after the delay (if any).
     * <br/><br/>
     * <p>
     * <b>You need to set the starting values of the interpolation by using one
     * of the target() methods</b>. The interpolation will run from the
     * starting values to these target values.
     * <br/><br/>
     * <p>
     * The common use of Tweens is "fire-and-forget": you do not need to care
     * for tweens once you added them to a TweenManager, they will be updated
     * automatically, and cleaned once finished. Common call:
     * <br/><br/>
     * <p>
     * <pre> {@code
     * Tween.from(myObject, POSITION, 1.0f)
     *      .target(0, 0)
     *      .ease(Quad.INOUT)
     *      .start(myManager);
     * }</pre>
     * <p>
     * Several options such as delay, repetitions and callbacks can be added to
     * the tween.
     *
     * @param target    The target object of the interpolation.
     * @param tweenType The desired type of interpolation.
     * @param duration  The duration of the interpolation, in milliseconds.
     * @return The generated Tween.
     */
    @SuppressWarnings("unused")
    @NonNull
    public static <T> TweenDef<T> from(@NonNull T target, @NonNull TweenType<T> tweenType, float duration) {
        return new TweenDefImpl<>(true, target, tweenType, duration)
                .ease(Quad.INOUT)
                .path(CatmullRom.instance());
    }

    /**
     * Factory creating a new instantaneous interpolation (thus this is not
     * really an interpolation).
     * <br/><br/>
     * <p>
     * <b>You need to set the target values of the interpolation by using one
     * of the target() methods</b>. The interpolation will set the target
     * attribute to these values after the delay (if any).
     * <br/><br/>
     * <p>
     * The common use of Tweens is "fire-and-forget": you do not need to care
     * for tweens once you added them to a TweenManager, they will be updated
     * automatically, and cleaned once finished. Common call:
     * <br/><br/>
     * <p>
     * <pre> {@code
     * Tween.set(myObject, POSITION)
     *      .target(50, 70)
     *      .delay(1.0f)
     *      .start(myManager);
     * }</pre>
     * <p>
     * Several options such as delay, repetitions and callbacks can be added to
     * the tween.
     *
     * @param target    The target object of the interpolation.
     * @param tweenType The desired type of interpolation.
     * @return The generated Tween.
     */
    @SuppressWarnings("unused")
    @NonNull
    public static <T> TweenDef<T> set(@NonNull T target, @NonNull TweenType<T> tweenType) {
        return new TweenDefImpl<>(false, target, tweenType, .0F)
                .ease(Quad.INOUT);
    }

    /**
     * Factory creating a new timer. The given addCallback will be triggered on
     * each iteration start, after the delay.
     * <br/><br/>
     * <p>
     * The common use of Tweens is "fire-and-forget": you do not need to care
     * for tweens once you added them to a TweenManager, they will be updated
     * automatically, and cleaned once finished. Common call:
     * <br/><br/>
     * <p>
     * <pre> {@code
     * Tween.call(myCallback)
     *      .delay(1.0f)
     *      .repeat(10, 1000)
     *      .start(myManager);
     * }</pre>
     *
     * @param callback The addCallback that will be triggered on each iteration
     *                 start.
     * @return The generated Tween.
     * @see TweenCallback
     */
    @SuppressWarnings("unused")
    @NonNull
    public static TweenDef call(@NonNull TweenCallback callback) {
        return new TweenDefImpl<>(false, null, null, .0F)
                .addCallback(TweenCallback.START, callback);
    }

    /**
     * Convenience method to create an empty tween. Such object is only useful
     * when placed inside animation sequences (see {@link Timeline}), in which
     * it may act as a beacon, so you can set a addCallback on it in order to
     * trigger some action at the right moment.
     *
     * @return The generated Tween.
     * @see Timeline
     */
    @SuppressWarnings("WeakerAccess")
    @NonNull
    public static TweenDef mark() {
        //noinspection unchecked
        return new TweenDefImpl<>(false, null, null, .0F);
    }

    @NonNull
    @Override
    public Tween start() {
        super.start();
        return this;
    }

    @NonNull
    @Override
    public Tween start(@NonNull TweenManager manager) {
        super.start(manager);
        return this;
    }

    // Main
    private final Object target;
    private final TweenType<Object> tweenType;
    private final TweenEquation equation;
    private final TweenPath path;
    private final TweenAction<Object> tweenAction;

    // General
    private final boolean isFrom;
    //    private boolean isRelative;
    private final int combinedAttrsCount;
    private final int waypointsCount;


    private final float[] startValues;
    private final float[] targetValues;
    private final float[] waypoints;

    // Buffers
    private final float[] accessorBuffer;
    private final float[] pathBuffer;

    Tween(@NonNull TweenDefImpl def) {
        super(def.impl);
        this.target = def.target;
        //noinspection unchecked
        this.tweenType = def.tweenType;
        this.equation = def.equation;
        this.path = def.path;
        //noinspection unchecked
        this.tweenAction = def.action;
        this.isFrom = def.isFrom;
        this.combinedAttrsCount = def.targetSize;
        this.startValues = new float[combinedAttrsCount];
        this.targetValues = def.targets;
        this.waypoints = def.waypoints;

        this.waypointsCount = waypoints != null
                ? waypoints.length / combinedAttrsCount
                : 0;

        this.accessorBuffer = new float[combinedAttrsCount];
        this.pathBuffer = waypointsCount > 0
                ? new float[(2 + waypointsCount) * combinedAttrsCount]
                : null;
    }

    /**
     * Gets the target object.
     */
    @SuppressWarnings("unused")
    @Nullable
    public Object getTarget() {
        return target;
    }

    @Override
    protected void initializeOverride() {

        if (target == null) {
            return;
        }

        tweenType.getValues(target, startValues);

        for (int i = 0; i < combinedAttrsCount; i++) {
            if (isFrom) {
                float tmp = startValues[i];
                startValues[i] = targetValues[i];
                targetValues[i] = tmp;
            }
        }
    }

    @Override
    protected void updateOverride(int step, int lastStep, boolean isIterationStep, float delta) {

        if (target == null
                || equation == null) {
            return;
        }

        // Case iteration end has been reached

        if (!isIterationStep && step > lastStep) {
            setValues(isReverse(lastStep) ? startValues : targetValues);
            return;
        }

        if (!isIterationStep && step < lastStep) {
            setValues(isReverse(lastStep) ? targetValues : startValues);
            return;
        }

        // Validation
        if (!isIterationStep) {
            throw new IllegalStateException("Assertion failed, isIterationStep");
        }

        final float currentTime = getCurrentTime();

        if (!(currentTime >= 0)) {
            throw new IllegalStateException("Assertion failed, currentTime >= 0");
        }

        if (!(currentTime <= duration)) {
            throw new IllegalStateException("Assertion failed, currentTime <= duration");
        }

        // Case duration equals zero

        if (duration < 0.00000000001f && delta > -0.00000000001f) {
            setValues(isReverse(step) ? targetValues : startValues);
            return;
        }

        if (duration < 0.00000000001f && delta < 0.00000000001f) {
            setValues(isReverse(step) ? startValues : targetValues);
            return;
        }

        // Normal behavior

        float time = isReverse(step) ? duration - currentTime : currentTime;
        float t = equation.compute(time / duration);

        if (waypointsCount == 0 || path == null) {
            for (int i = 0; i < combinedAttrsCount; i++) {
                accessorBuffer[i] = startValues[i] + t * (targetValues[i] - startValues[i]);
            }

        } else {

            for (int i = 0; i < combinedAttrsCount; i++) {
                pathBuffer[0] = startValues[i];
                pathBuffer[1 + waypointsCount] = targetValues[i];
                for (int ii = 0; ii < waypointsCount; ii++) {
                    pathBuffer[ii + 1] = waypoints[ii * combinedAttrsCount + i];
                }

                accessorBuffer[i] = path.compute(t, pathBuffer, waypointsCount + 2);
            }
        }

        setValues(accessorBuffer);
    }

    @Override
    protected void forceStartValues() {
        if (target != null) {
            setValues(startValues);
        }
    }

    @Override
    protected void forceEndValues() {
        if (target != null) {
            setValues(targetValues);
        }
    }

    @Override
    public boolean containsTarget(@NonNull Object target) {
        return this.target == target;
    }

    private void setValues(@NonNull float[] values) {
        tweenType.setValues(target, values);
        if (tweenAction != null) {
            tweenAction.apply(target);
        }
    }
}
