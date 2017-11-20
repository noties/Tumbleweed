package ru.noties.tumbleweed;

import android.support.annotation.NonNull;

public class BaseTweenDefImpl extends BaseTweenDef {

    float duration;

    float delay;
    int repeatCount;
    float repeatDelay;
    boolean isYoyo;
    int callbackEvents;
    TweenCallback callback;
    Object userData;
    boolean removeWhenFinished = true;

    BaseTweenDefImpl() {
    }

    @NonNull
    BaseTweenDefImpl setDuration(float duration) {
        this.duration = duration;
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
    public BaseTweenDef callback(@NonNull TweenCallback callback) {
        this.callbackEvents = TweenCallback.ANY;
        this.callback = callback;
        return this;
    }

    @NonNull
    @Override
    public BaseTweenDef callback(@TweenCallback.Event int callbackEvents, @NonNull TweenCallback callback) {
        this.callbackEvents = callbackEvents;
        this.callback = callback;
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
    protected int repeatCount() {
        return repeatCount;
    }

    @Override
    protected float delay() {
        return delay;
    }

    @Override
    protected float fullDuration() {
        final float out;
        if (repeatCount < 0) {
            out = -1.F;
        } else {
            out = delay + duration + (repeatDelay + duration) * repeatCount;
        }
        return out;
    }
}
