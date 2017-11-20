package ru.noties.tumbleweed;

import android.support.annotation.NonNull;

@SuppressWarnings("UnusedReturnValue")
public abstract class BaseTweenDef {

    @NonNull
    public abstract BaseTween build();

    @NonNull
    public abstract BaseTween start();

    @NonNull
    public abstract BaseTween start(@NonNull TweenManager tweenManager);

    @NonNull
    public abstract BaseTweenDef delay(float duration);

    @NonNull
    public abstract BaseTweenDef repeat(int count, float delay);

    @NonNull
    public abstract BaseTweenDef repeatYoyo(int count, float delay);

    @NonNull
    public abstract BaseTweenDef callback(@NonNull TweenCallback callback);

    @NonNull
    public abstract BaseTweenDef callback(
            @TweenCallback.Event int callbackEvents,
            @NonNull TweenCallback callback
    );

    @NonNull
    public abstract BaseTweenDef userData(Object userData);

    @NonNull
    public abstract BaseTweenDef removeWhenFinished(boolean removeWhenFinished);

    protected abstract int repeatCount();

    protected abstract float delay();

    protected abstract float fullDuration();
}
