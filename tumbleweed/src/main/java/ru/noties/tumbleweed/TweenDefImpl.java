package ru.noties.tumbleweed;

import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

class TweenDefImpl<T> extends TweenDef<T> {

    final boolean isFrom;
    final T target;
    final TweenType<? extends T> tweenType;
    final BaseTweenDefImpl impl;

    final int targetSize;

    TweenEquation equation;
    TweenPath path;

    float[] targets;
    float[] waypoints;
    private float[] scale;

    private int waypointsCount;

    TweenDefImpl(boolean from, @Nullable T target, @Nullable TweenType<? extends T> tweenType, @FloatRange(from = 0) float duration) {
        this.isFrom = from;
        this.target = target;
        this.tweenType = tweenType;
        this.impl = new BaseTweenDefImpl().setDuration(duration);

        final int targetSize;
        if (tweenType != null) {
            targetSize = tweenType.getValuesSize();
        } else {
            targetSize = 0;
        }
        this.targetSize = targetSize;
    }

    @NonNull
    @Override
    public TweenDef<T> ease(@NonNull TweenEquation equation) {
        this.equation = equation;
        return this;
    }

    @NonNull
    @Override
    public TweenDef<T> path(@NonNull TweenPath path) {
        this.path = path;
        return this;
    }

    @NonNull
    @Override
    public TweenDef<T> target(float... targets) {

        validateTargetSize(targets.length);

        this.targets = targets;

        return this;
    }

    @NonNull
    @Override
    public TweenDef<T> waypoint(float... waypoints) {

        validateTargetSize(waypoints.length);

        ensureWaypoints((waypointsCount + 1) * targetSize);

        System.arraycopy(waypoints, 0, this.waypoints, waypointsCount * targetSize, targetSize);

        waypointsCount += 1;

        return this;
    }

    @NonNull
    @Override
    public TweenDef<T> scale(float... targets) {

        validateTargetSize(targets.length);

        this.scale = targets;

        return this;
    }

    @NonNull
    @Override
    public TweenDef<T> delay(float duration) {
        impl.delay(duration);
        return this;
    }

    @NonNull
    @Override
    public TweenDef<T> repeat(int count, float delay) {
        impl.repeat(count, delay);
        return this;
    }

    @NonNull
    @Override
    public TweenDef<T> repeatYoyo(int count, float delay) {
        impl.repeatYoyo(count, delay);
        return this;
    }

    @NonNull
    @Override
    public TweenDef<T> callback(@NonNull TweenCallback callback) {
        impl.callback(callback);
        return this;
    }

    @NonNull
    @Override
    public TweenDef<T> callback(@TweenCallback.Event int callbackEvents, @NonNull TweenCallback callback) {
        impl.callback(callbackEvents, callback);
        return this;
    }

    @NonNull
    @Override
    public TweenDef<T> userData(Object userData) {
        impl.userData(userData);
        return this;
    }

    @NonNull
    @Override
    public TweenDef<T> removeWhenFinished(boolean removeWhenFinished) {
        impl.removeWhenFinished(removeWhenFinished);
        return this;
    }

    @Override
    protected int repeatCount() {
        return impl.repeatCount;
    }

    @Override
    protected float delay() {
        return impl.delay;
    }

    @Override
    protected float fullDuration() {
        return impl.fullDuration();
    }

    @NonNull
    @Override
    public Tween build() {
        if (targetSize != 0 && scale != null) {
            for (int i = 0; i < targetSize; i++) {
                targets[i] = targets[i] * scale[i];
            }
        }
        return new Tween(this);
    }

    @NonNull
    @Override
    public Tween start() {
        final Tween tween = build();
        tween.start();
        return tween;
    }

    @NonNull
    @Override
    public Tween start(@NonNull TweenManager manager) {
        final Tween tween = build();
        tween.start(manager);
        return tween;
    }

    private void validateTargetSize(int actual) {
        if (actual != targetSize) {
            throw new RuntimeException("Target size mismatch. Expected: " + targetSize + ", actual: " + actual);
        }
    }

    private void ensureWaypoints(int newLength) {
        if (waypoints == null) {
            waypoints = new float[newLength];
        } else {
            if (newLength > waypoints.length) {
                final float[] cache = waypoints;
                waypoints = new float[newLength];
                System.arraycopy(cache, 0, waypoints, 0, cache.length);
            }
        }
    }
}
