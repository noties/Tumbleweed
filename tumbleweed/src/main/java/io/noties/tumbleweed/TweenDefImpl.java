package io.noties.tumbleweed;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class TweenDefImpl<T> extends TweenDef<T> {

    final boolean isFrom;
    final T target;
    final TweenType<T> tweenType;
    final BaseTweenDefImpl impl;

    final int targetSize;

    TweenEquation equation;
    TweenPath path;

    float[] targets;
    float[] waypoints;
    private float[] scale;

    TweenAction<T> action;

    private int waypointsCount;

    private boolean isActionable;

    private boolean isBuilt;

    TweenDefImpl(
            boolean from,
            @Nullable T target,
            @Nullable TweenType<T> tweenType,
            @FloatRange(from = 0) float duration) {
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
    public TweenDef<T> duration(float duration) {

        checkState();

        this.impl.setDuration(duration);

        return this;
    }

    @NonNull
    @Override
    public TweenDef<T> ease(@NonNull TweenEquation equation) {

        checkState();

        this.equation = equation;

        return this;
    }

    @NonNull
    @Override
    public TweenDef<T> path(@NonNull TweenPath path) {

        checkState();

        this.path = path;

        return this;
    }

    @NonNull
    @Override
    public TweenDef<T> target(@NonNull T target) {
        final float[] values = new float[tweenType.getValuesSize()];
        tweenType.getValues(target, values);
        target(values);
        return this;
    }

    @NonNull
    @Override
    public TweenDef<T> target(float... targets) {

        checkState();

        validateTargetSize(targets.length);

        this.targets = targets;

        return this;
    }

    @NonNull
    @Override
    public TweenDef<T> waypoint(@NonNull T target) {
        final float[] waypoint = new float[tweenType.getValuesSize()];
        tweenType.getValues(target, waypoint);
        waypoint(waypoint);
        return this;
    }

    @NonNull
    @Override
    public TweenDef<T> waypoint(float... waypoints) {

        checkState();

        validateTargetSize(waypoints.length);

        ensureWaypoints((waypointsCount + 1) * targetSize);

        System.arraycopy(waypoints, 0, this.waypoints, waypointsCount * targetSize, targetSize);

        waypointsCount += 1;

        return this;
    }

    @NonNull
    @Override
    public TweenDef<T> scale(float... targets) {

        checkState();

        this.scale = targets;

        return this;
    }

    @NonNull
    @Override
    public TweenDef<T> action(@NonNull TweenAction<T> action) {
        this.action = action;
        return this;
    }

    @NonNull
    @Override
    public TweenDef<T> delay(float duration) {

        checkState();

        impl.delay(duration);

        return this;
    }

    @NonNull
    @Override
    public TweenDef<T> repeat(int count, float delay) {

        checkState();

        impl.repeat(count, delay);

        return this;
    }

    @NonNull
    @Override
    public TweenDef<T> repeatYoyo(int count, float delay) {

        checkState();

        impl.repeatYoyo(count, delay);

        return this;
    }

    @NonNull
    @Override
    public TweenDef<T> addCallback(@NonNull TweenCallback callback) {

        checkState();

        impl.addCallback(callback);

        return this;
    }

    @NonNull
    @Override
    public TweenDef<T> addCallback(@TweenCallback.Event int callbackEvents, @NonNull TweenCallback callback) {

        checkState();

        impl.addCallback(callbackEvents, callback);

        return this;
    }

    @NonNull
    @Override
    public TweenDef<T> userData(Object userData) {

        checkState();

        impl.userData(userData);

        return this;
    }

    @NonNull
    @Override
    public TweenDef<T> removeWhenFinished(boolean removeWhenFinished) {

        checkState();

        impl.removeWhenFinished(removeWhenFinished);

        return this;
    }

    @Override
    public boolean isActionable() {
        return isActionable;
    }

    @Override
    public float duration() {
        return impl.duration;
    }

    @Override
    public int repeatCount() {
        return impl.repeatCount;
    }

    @Override
    public float delay() {
        return impl.delay;
    }

    @Override
    public float fullDuration() {
        return impl.fullDuration();
    }

    @NonNull
    @Override
    public Tween build() {

        checkState();

        isBuilt = true;

        final int scaleSize = scale != null
                ? scale.length
                : 0;

        if (targetSize > 0
                && scaleSize > 0) {
            for (int i = 0; i < targetSize; i++) {
                targets[i] = targets[i] * scale[i % scaleSize];
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

    // @since 2.1.0-SNAPSHOT
    @SuppressWarnings("SameParameterValue")
    @NonNull
    TweenDefImpl<T> isActionable(boolean isActionable) {
        this.isActionable = isActionable;
        return this;
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

    private void checkState() {
        if (isBuilt) {
            throw new RuntimeException("TweenDef has already been built.");
        }
    }
}
