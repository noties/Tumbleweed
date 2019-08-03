package io.noties.tumbleweed;

import android.support.annotation.NonNull;

public class BaseTweenDefImpl extends BaseTweenDef {

    final TweenCallbacks.Builder callbacksBuilder = new TweenCallbacks.Builder();

    float duration;

    float delay;
    int repeatCount;
    float repeatDelay;
    boolean isYoyo;

    Object userData;

    boolean removeWhenFinished = true;

    BaseTweenDefImpl() {
    }

    @NonNull
    BaseTweenDefImpl setDuration(float duration) {
        this.duration = processDuration(duration);
        return this;
    }

    @NonNull
    @Override
    public BaseTween build() {
        throw new RuntimeException("Children should override `build()`");
    }

    @NonNull
    @Override
    public BaseTween start() {
        throw new RuntimeException("Children should override `start()`");
    }

    @NonNull
    @Override
    public BaseTween start(@NonNull TweenManager tweenManager) {
        throw new RuntimeException("Children should override `start(TweenManager)`");
    }

    @NonNull
    @Override
    public BaseTweenDef delay(float duration) {
        this.delay = duration;
        return this;
    }

    @NonNull
    @Override
    public BaseTweenDef repeat(int count, float delay) {
        this.repeatCount = count;
        this.repeatDelay = delay;
        return this;
    }

    @NonNull
    @Override
    public BaseTweenDef repeatYoyo(int count, float delay) {
        this.repeatCount = count;
        this.repeatDelay = delay;
        this.isYoyo = true;
        return this;
    }

    @NonNull
    @Override
    public BaseTweenDef addCallback(@NonNull TweenCallback callback) {
        callbacksBuilder.add(callback);
        return this;
    }

    @NonNull
    @Override
    public BaseTweenDef addCallback(@TweenCallback.Event int callbackEvents, @NonNull TweenCallback callback) {
        callbacksBuilder.add(callbackEvents, callback);
        return this;
    }

    @NonNull
    @Override
    public BaseTweenDef userData(Object userData) {
        this.userData = userData;
        return this;
    }

    @NonNull
    @Override
    public BaseTweenDef removeWhenFinished(boolean removeWhenFinished) {
        this.removeWhenFinished = removeWhenFinished;
        return this;
    }

    @Override
    public int repeatCount() {
        return repeatCount;
    }

    @Override
    public float delay() {
        return delay;
    }

    @Override
    public float fullDuration() {
        final float out;
        if (repeatCount < 0) {
            out = -1.F;
        } else {
            out = delay + duration + ((repeatDelay + duration) * repeatCount);
        }
        return out;
    }
}
