package ru.noties.tumbleweed;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * A TweenManager updates all your tweens and timelines at once.
 * Its main interest is that it handles the tween/timeline life-cycles for you,
 * as well as the pooling constraints (if object pooling is enabled).
 * <p/>
 * <p>
 * Just give it a bunch of tweens or timelines and call update() periodically,
 * you don't need to care for anything else! Relax and enjoy your animations.
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 * @see Tween
 * @see Timeline
 */
@SuppressWarnings("unused")
public abstract class TweenManager {

    @NonNull
    public static TweenManager create() {
        return new TweenManagerImpl();
    }

    /**
     * Adds a tween or timeline to the manager and starts or restarts it.
     *
     * @return The manager, for instruction chaining.
     */
    @SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
    @NonNull
    public abstract TweenManager add(@NonNull BaseTween object);

    /**
     * Returns true if the manager contains any valid interpolation associated
     * to the given target object.
     */
    public abstract boolean containsTarget(@NonNull Object target);


    /**
     * Kills every managed tweens and timelines.
     */
    public abstract void killAll();

    /**
     * Kills every tweens associated to the given target. Will also kill every
     * timelines containing a tween associated to the given target.
     */
    public abstract void killTarget(@NonNull Object target);

    /**
     * Pauses the manager. Further update calls won't have any effect.
     */
    public abstract void pause();

    /**
     * Resumes the manager, if paused.
     */
    public abstract void resume();

    public abstract boolean isPaused();

    public abstract boolean isStarted();

    // isStarted && !isPaused
    public abstract boolean isRunning();

    /**
     * Updates every tweens with a delta time ang handles the tween life-cycles
     * automatically. If a tween is finished, it will be removed from the
     * manager. The delta time represents the elapsed time between now and the
     * last update call. Each tween or timeline manages its local time, and adds
     * this delta to its local time to update itself.
     * <p/>
     * <p>
     * Slow motion, fast motion and backward play can be easily achieved by
     * tweaking this delta time. Multiply it by -1 to play the animation
     * backward, or by 0.5 to play it twice slower than its normal speed.
     */
    public abstract void update(float delta);

    public abstract void dispose();

    public abstract boolean isDisposed();

    public abstract int tweenCount();

    @NonNull
    public abstract List<BaseTween> tweens();
}
