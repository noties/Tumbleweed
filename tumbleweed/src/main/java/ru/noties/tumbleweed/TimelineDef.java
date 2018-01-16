package ru.noties.tumbleweed;

import android.support.annotation.NonNull;

@SuppressWarnings("unused")
public abstract class TimelineDef extends BaseTweenDef {

    @NonNull
    public abstract TimelineDef push(@NonNull TweenDef<?> tween);

    @NonNull
    public abstract TimelineDef push(@NonNull TimelineDef timeline);

    @SuppressWarnings("SameParameterValue")
    @NonNull
    public abstract TimelineDef pushPause(float duration);

    @NonNull
    public abstract TimelineDef beginSequence();

    @NonNull
    public abstract TimelineDef beginParallel();

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



    @SuppressWarnings("WeakerAccess")
    protected TimelineDef current;

    @SuppressWarnings("WeakerAccess")
    protected TimelineDef parent;

    protected abstract void add(@NonNull BaseTweenDef tweenDef);
}
