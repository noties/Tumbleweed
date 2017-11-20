package ru.noties.tumbleweed;

import android.support.annotation.NonNull;

public abstract class TweenDef<T> extends BaseTweenDef {

    @NonNull
    public abstract TweenDef<T> ease(@NonNull TweenEquation equation);

    @NonNull
    public abstract TweenDef<T> path(@NonNull TweenPath path);

    @NonNull
    public abstract TweenDef<T> target(float... targets);

    @NonNull
    public abstract TweenDef<T> waypoint(float... waypoints);

    // will scale everything: target, waypoints, so we can specify relative values and then provide base unit
    @SuppressWarnings("unused")
    @NonNull
    public abstract TweenDef<T> scale(float... targets);

    @NonNull
    @Override
    public abstract TweenDef<T> delay(float duration);

    @NonNull
    @Override
    public abstract TweenDef<T> repeat(int count, float delay);

    @NonNull
    @Override
    public abstract TweenDef<T> repeatYoyo(int count, float delay);

    @NonNull
    @Override
    public abstract TweenDef<T> callback(@NonNull TweenCallback callback);

    @NonNull
    @Override
    public abstract TweenDef<T> callback(
            @TweenCallback.Event int callbackEvents,
            @NonNull TweenCallback callback
    );

    @NonNull
    @Override
    public abstract TweenDef<T> userData(Object userData);

    @NonNull
    public abstract TweenDef<T> removeWhenFinished(boolean removeWhenFinished);

    @NonNull
    @Override
    public abstract Tween build();

    @NonNull
    @Override
    public abstract Tween start();

    @NonNull
    @Override
    public abstract Tween start(@NonNull TweenManager manager);
}
