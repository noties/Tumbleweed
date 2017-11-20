package ru.noties.tumbleweed;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * BaseTween is the base class of Tween and Timeline. It defines the
 * iteration engine used to play animations for any number of times, and in
 * any direction, at any speed.
 * <p/>
 * <p>
 * It is responsible for calling the different callbacks at the right moments,
 * and for making sure that every callbacks are triggered, even if the update
 * engine gets a big delta time at once.
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 * @see Tween
 * @see Timeline
 */
@SuppressWarnings("unused")
public abstract class BaseTween {

    private final int repeatCount;
    private final float repeatDelay;
    private final float delay;

    private final boolean isYoyo;

    @SuppressWarnings("WeakerAccess")
    protected final float duration;
    private final float fullDuration;

    private int step;
    private boolean isIterationStep;

    // Timings
    private float currentTime;
    private float deltaTime;

    private boolean isStarted; // true when the object is started
    private boolean isInitialized; // true after the delay
    private boolean isFinished; // true when all repetitions are done
    private boolean isKilled; // true if kill() was called
    private boolean isPaused; // true if pause() was called

    // Misc
    private final TweenCallback callback;
    private final int callbackTriggers;
    private Object userData;

    // Package access
    private boolean isAutoRemoveEnabled;
    private boolean isAutoStartEnabled;

    BaseTween(@NonNull BaseTweenDefImpl def) {
        this.delay = def.delay;
        this.duration = def.duration;
        this.fullDuration = def.fullDuration();
        this.repeatCount = def.repeatCount;
        this.repeatDelay = def.repeatDelay;
        this.isYoyo = def.isYoyo;
        this.callback = def.callback;
        this.callbackTriggers = def.callbackEvents;
        this.userData = def.userData;
        this.isAutoRemoveEnabled = def.removeWhenFinished;
    }

    /**
     * Starts or restarts the object unmanaged. You will need to take care of
     * its life-cycle. If you want the tween to be managed for you, use a
     * {@link TweenManager}.
     *
     * @return The current object, for chaining instructions.
     */
    @NonNull
    public BaseTween start() {
        isAutoStartEnabled = true;
        currentTime = 0;
        isStarted = true;
        return this;
    }

    /**
     * Convenience method to add an object to a manager. Its life-cycle will be
     * handled for you. Relax and enjoy the animation.
     *
     * @return The current object, for chaining instructions.
     */
    @NonNull
    public BaseTween start(@NonNull TweenManager manager) {
        isAutoStartEnabled = true;
        manager.add(this);
        return this;
    }

    /**
     * Kills the tween or timeline. If you are using a TweenManager, this object
     * will be removed automatically.
     */
    @SuppressWarnings("WeakerAccess")
    public void kill() {
        isKilled = true;
    }

    /**
     * Stops and resets the tween or timeline, and sends it to its pool, for
     * +	 * later reuse. Note that if you use a {@link TweenManager}, this method
     * +	 * is automatically called once the animation is finished.
     */
    public void free() {
    }

    /**
     * Pauses the tween or timeline. Further update calls won't have any effect.
     */
    public void pause() {
        isPaused = true;
    }

    /**
     * Resumes the tween or timeline. Has no effect is it was no already paused.
     */
    public void resume() {
        isPaused = false;
    }

    /**
     * Gets the delay of the tween or timeline. Nothing will happen before
     * this delay.
     */
    public float getDelay() {
        return delay;
    }

    /**
     * Gets the duration of a single iteration.
     */
    public float getDuration() {
        return duration;
    }

    /**
     * Gets the number of iterations that will be played.
     */
    public int getRepeatCount() {
        return repeatCount;
    }

    /**
     * Gets the delay occuring between two iterations.
     */
    public float getRepeatDelay() {
        return repeatDelay;
    }

    /**
     * Returns the complete duration, including initial delay and repetitions.
     * The formula is as follows:
     * <pre>
     * fullDuration = delay + duration + (repeatDelay + duration) * repeatCount
     * </pre>
     */
    @SuppressWarnings("WeakerAccess")
    public float getFullDuration() {
        return fullDuration;
    }

    /**
     * Gets the attached data, or null if none.
     */
    @Nullable
    public Object getUserData() {
        return userData;
    }

    public void setUserData(@Nullable Object userData) {
        this.userData = userData;
    }

    public boolean autoRemoveEnabled() {
        return isAutoRemoveEnabled;
    }

    public boolean autoStartEnabled() {
        return isAutoStartEnabled;
    }

    /**
     * Gets the local time.
     */
    @SuppressWarnings("WeakerAccess")
    public float getCurrentTime() {
        return currentTime;
    }

    /**
     * Returns true if the tween or timeline has been started.
     */
    public boolean isStarted() {
        return isStarted;
    }

    /**
     * Returns true if the tween or timeline has been initialized. Starting
     * values for tweens are stored at initialization time. This initialization
     * takes place right after the initial delay, if any.
     */
    public boolean isInitialized() {
        return isInitialized;
    }

    /**
     * Returns true if the tween is finished (i.e. if the tween has reached
     * its end or has been killed). If you don't use a TweenManager, you may
     * want to call {@link #free()} to reuse the object later.
     */
    public boolean isFinished() {
        return isFinished || isKilled;
    }

    /**
     * Returns true if the iterations are played as yoyo. Yoyo means that
     * every two iterations, the animation will be played backwards.
     */
    public boolean isYoyo() {
        return isYoyo;
    }

    /**
     * Returns true if the tween or timeline is currently paused.
     */
    public boolean isPaused() {
        return isPaused;
    }

    protected abstract void forceStartValues();

    protected abstract void forceEndValues();

    protected abstract boolean containsTarget(@NonNull Object target);

    protected void initializeOverride() {
    }

    protected void updateOverride(int step, int lastStep, boolean isIterationStep, float delta) {
    }

    @SuppressWarnings("WeakerAccess")
    protected void forceToStart() {
        currentTime = -delay;
        step = -1;
        isIterationStep = false;
        if (isReverse(0)) forceEndValues();
        else forceStartValues();
    }

    @SuppressWarnings("WeakerAccess")
    protected void forceToEnd(float time) {
        currentTime = time - getFullDuration();
        step = repeatCount * 2 + 1;
        isIterationStep = false;
        if (isReverse(repeatCount * 2)) forceStartValues();
        else forceEndValues();
    }

    @SuppressWarnings("WeakerAccess")
    protected void callCallback(int type) {
        if (callback != null && (callbackTriggers & type) > 0) callback.onEvent(type, this);
    }

    @SuppressWarnings("WeakerAccess")
    protected boolean isReverse(int step) {
        return isYoyo && Math.abs(step % 4) == 2;
    }

    @SuppressWarnings("WeakerAccess")
    protected boolean isValid(int step) {
        return (step >= 0 && step <= repeatCount * 2) || repeatCount < 0;
    }

    protected void killTarget(@NonNull Object target) {
        if (containsTarget(target)) {
            kill();
        }
    }

    /**
     * Updates the tween or timeline state. <b>You may want to use a
     * TweenManager to update objects for you.</b>
     * <p>
     * Slow motion, fast motion and backward play can be easily achieved by
     * tweaking this delta time. Multiply it by -1 to play the animation
     * backward, or by 0.5 to play it twice slower than its normal speed.
     *
     * @param delta A delta time between now and the last call.
     */
    public void update(float delta) {

        if (!isStarted
                || isPaused
                || isKilled) {
            return;
        }

        deltaTime = delta;

        if (!isInitialized) {
            initialize();
        }

        if (isInitialized) {
            testRelaunch();
            updateStep();
            testCompletion();
        }

        currentTime += deltaTime;
        deltaTime = 0;
    }

    private void initialize() {
        if (currentTime + deltaTime >= delay) {
            initializeOverride();
            isInitialized = true;
            isIterationStep = true;
            step = 0;
            deltaTime -= delay - currentTime;
            currentTime = 0;
            callCallback(TweenCallback.BEGIN);
            callCallback(TweenCallback.START);
        }
    }

    private void testRelaunch() {
        if (!isIterationStep && repeatCount >= 0 && step < 0 && currentTime + deltaTime >= 0) {

            if (!(step == -1)) {
                throw new IllegalStateException("Assertion failed, step == -1");
            }

            isIterationStep = true;
            step = 0;
            float delta = 0 - currentTime;
            deltaTime -= delta;
            currentTime = 0;
            callCallback(TweenCallback.BEGIN);
            callCallback(TweenCallback.START);
            updateOverride(step, step - 1, isIterationStep, delta);

        } else if (!isIterationStep && repeatCount >= 0 && step > repeatCount * 2 && currentTime + deltaTime < 0) {

            if (!(step == repeatCount * 2 + 1)) {
                throw new IllegalStateException("Assertion failed, step == repeatCount * 2 + 1");
            }

            isIterationStep = true;
            step = repeatCount * 2;
            float delta = 0 - currentTime;
            deltaTime -= delta;
            currentTime = duration;
            callCallback(TweenCallback.BACK_BEGIN);
            callCallback(TweenCallback.BACK_START);
            updateOverride(step, step + 1, isIterationStep, delta);
        }
    }

    private void updateStep() {
        while (isValid(step)) {
            if (!isIterationStep && currentTime + deltaTime <= 0) {
                isIterationStep = true;
                step -= 1;

                float delta = 0 - currentTime;
                deltaTime -= delta;
                currentTime = duration;

                if (isReverse(step)) forceStartValues();
                else forceEndValues();
                callCallback(TweenCallback.BACK_START);
                updateOverride(step, step + 1, isIterationStep, delta);

            } else if (!isIterationStep && currentTime + deltaTime >= repeatDelay) {
                isIterationStep = true;
                step += 1;

                float delta = repeatDelay - currentTime;
                deltaTime -= delta;
                currentTime = 0;

                if (isReverse(step)) forceEndValues();
                else forceStartValues();
                callCallback(TweenCallback.START);
                updateOverride(step, step - 1, isIterationStep, delta);

            } else if (isIterationStep && currentTime + deltaTime < 0) {
                isIterationStep = false;
                step -= 1;

                float delta = 0 - currentTime;
                deltaTime -= delta;
                currentTime = 0;

                updateOverride(step, step + 1, isIterationStep, delta);
                callCallback(TweenCallback.BACK_END);

                if (step < 0 && repeatCount >= 0) callCallback(TweenCallback.BACK_COMPLETE);
                else currentTime = repeatDelay;

            } else if (isIterationStep && currentTime + deltaTime > duration) {
                isIterationStep = false;
                step += 1;

                float delta = duration - currentTime;
                deltaTime -= delta;
                currentTime = duration;

                updateOverride(step, step - 1, isIterationStep, delta);
                callCallback(TweenCallback.END);

                if (step > repeatCount * 2 && repeatCount >= 0)
                    callCallback(TweenCallback.COMPLETE);
                currentTime = 0;

            } else if (isIterationStep) {
                float delta = deltaTime;
                deltaTime -= delta;
                currentTime += delta;
                updateOverride(step, step, isIterationStep, delta);
                break;

            } else {
                float delta = deltaTime;
                deltaTime -= delta;
                currentTime += delta;
                break;
            }
        }
    }

    private void testCompletion() {
        isFinished = repeatCount >= 0 && (step > repeatCount * 2 || step < 0);
    }
}
