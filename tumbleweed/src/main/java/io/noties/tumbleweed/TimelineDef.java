package io.noties.tumbleweed;

import androidx.annotation.NonNull;

@SuppressWarnings("unused")
public abstract class TimelineDef extends BaseTweenDef {

    @NonNull
    public abstract TimelineDef push(@NonNull TweenDef<?> tween);

    @NonNull
    public abstract TimelineDef push(@NonNull TimelineDef timeline);

    /**
     * @since 1.0.2
     */
    @NonNull
    public abstract TimelineDef push(@NonNull BaseTweenDef baseTweenDef);

    @SuppressWarnings("SameParameterValue")
    @NonNull
    public abstract TimelineDef pushPause(float duration);

    /**
     * @deprecated 2.1.0 consider using explicit factory method to push a nested Timeline -
     * {@code .push(Timeline)}
     */
    @Deprecated
    @NonNull
    public abstract TimelineDef beginSequence();

    /**
     * @deprecated 2.1.0 consider using explicit factory method to push a nested Timeline -
     * {@code .push(Timeline)}
     */
    @Deprecated
    @NonNull
    public abstract TimelineDef beginParallel();

    /**
     * @deprecated 2.1.0 consider using explicit factory method to push a nested Timeline -
     * {@code .push(Timeline)}
     */
    @Deprecated
    @NonNull
    public abstract TimelineDef end();

    @NonNull
    @Override
    public abstract Timeline build();

    @NonNull
    @Override
    public abstract Timeline start();

    @NonNull
    @Override
    public abstract Timeline start(@NonNull TweenManager tweenManager);

    @NonNull
    @Override
    public abstract TimelineDef delay(float duration);

    @NonNull
    @Override
    public abstract TimelineDef repeat(int count, float delay);

    @NonNull
    @Override
    public abstract TimelineDef repeatYoyo(int count, float delay);

    @NonNull
    @Override
    public abstract TimelineDef addCallback(@NonNull TweenCallback callback);

    @NonNull
    @Override
    public abstract TimelineDef addCallback(@TweenCallback.Event int callbackEvents, @NonNull TweenCallback callback);

    @NonNull
    @Override
    public abstract TimelineDef userData(Object userData);

    @NonNull
    @Override
    public abstract TimelineDef removeWhenFinished(boolean removeWhenFinished);

    // NB, internal logic of timeline-def must be reworked after creation of groups
    // is removed from timeline-def (beginSequence, etc)
    @Deprecated
    @SuppressWarnings("WeakerAccess")
    protected TimelineDef current;

    @Deprecated
    @SuppressWarnings("WeakerAccess")
    protected TimelineDef parent;

    protected abstract void add(@NonNull BaseTweenDef tweenDef);
}
