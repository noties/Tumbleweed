package io.noties.tumbleweed;

import android.support.annotation.NonNull;

public abstract class TweenDef<T> extends BaseTweenDef {

    @NonNull
    public abstract TweenDef<T> ease(@NonNull TweenEquation equation);

    @NonNull
    public abstract TweenDef<T> path(@NonNull TweenPath path);

    @NonNull
    public abstract TweenDef<T> target(@NonNull T target);

    @NonNull
    public abstract TweenDef<T> target(float... targets);

    @NonNull
    public abstract TweenDef<T> waypoint(@NonNull T target);

    @NonNull
    public abstract TweenDef<T> waypoint(float... waypoints);

    /**
     * Convenience method to scale _target_ values. Does not affect start values.
     * Scaling will be applied by simple formula: {@code target[i] = target[i] * scale[i % scales.length; },
     * so one can specify one scaling value to be applied to all targets, etc.
     *
     * If, for example, one have a flat array with x &amp; y coordinates, scaling can be applied by
     * providing 2 arguments: 1st will be applied to x value and 2nd to y.
     *
     * @param targets scaling values
     * @return instance
     */
    @SuppressWarnings("unused")
    @NonNull
    public abstract TweenDef<T> scale(float... targets);

    @SuppressWarnings("unused")
    @NonNull
    public abstract TweenDef<T> action(@NonNull TweenAction<T> action);

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
    public abstract TweenDef<T> addCallback(@NonNull TweenCallback callback);

    @NonNull
    @Override
    public abstract TweenDef<T> addCallback(
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
