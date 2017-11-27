package ru.noties.tumbleweed;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class TweenManagerImpl extends TweenManager {

    private final List<BaseTween> children = new ArrayList<>(8);

    private boolean isPaused;
    private boolean isDisposed;

    protected boolean isStarted;

    @SuppressWarnings("WeakerAccess")
    public TweenManagerImpl() {

    }

    @NonNull
    @Override
    public TweenManager add(@NonNull BaseTween object) {

        if (isDisposed()) {
            throw new RuntimeException("Cannot add tween to a disposed TweenManager");
        }

        if (!children.contains(object)) {
            children.add(object);
        }

        if (object.autoStartEnabled()) {
            object.start();
        }

        // trigger start if we haven't already
        if (!isStarted) {
            onStarted();
        }

        return this;
    }

    @Override
    public boolean containsTarget(@NonNull Object target) {
        boolean result = false;
        for (BaseTween tween : children) {
            if (tween.containsTarget(target)) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public void killAll() {
        for (BaseTween tween : children) {
            tween.kill();
        }
    }

    @Override
    public void killTarget(@NonNull Object target) {
        for (BaseTween tween : children) {
            tween.killTarget(target);
        }
    }

    @Override
    public void pause() {

        if (isDisposed()) {
            throw new RuntimeException("Cannot pause disposed TweenManager");
        }

        isPaused = true;

        // stop if we have started
        if (isStarted) {
            onStopped();
        }
    }

    @Override
    public void resume() {

        if (isDisposed()) {
            throw new RuntimeException("Cannot resume disposed TweenManager");
        }

        isPaused = false;

        // we will use isStarted flag to detect if we need to start again
        if (children.size() > 0) {
            onStarted();
        }
    }

    @Override
    public boolean isPaused() {
        return isPaused;
    }

    @Override
    public boolean isStarted() {
        return isStarted;
    }

    @Override
    public void update(float delta) {

        // clear finished
        // it's also a bit not optimal to iterate over all children on each pre call
        clearFinished();

        // all cleared
        if (children.size() == 0) {
            onStopped();
            return;
        }


        if (!isPaused) {
            if (delta >= 0) {
                for (int i = 0, size = children.size(); i < size; i++) {
                    children.get(i).update(delta);
                }
            } else {
                for (int i = children.size() - 1; i >= 0; i--) {
                    children.get(i).update(delta);
                }
            }
        }
    }

    @Override
    @CallSuper
    public void dispose() {
        if (!isDisposed) {

            onStopped();

            killAll();

            children.clear();

            isDisposed = true;
        }
    }

    @Override
    public boolean isDisposed() {
        return isDisposed;
    }

    @Override
    public int tweenCount() {
        return children.size();
    }

    private void clearFinished() {

        BaseTween baseTween;

        for (int i = children.size() - 1; i >= 0; i--) {

            baseTween = children.get(i);

            // this part is a bit confusing... kill marks tween to be finished
            // but if autoRemoveEnabled is false, then tween won't be removed..
            if (baseTween.isFinished() && baseTween.autoRemoveEnabled()) {
                children.remove(i);
            }
        }
    }

    @CallSuper
    protected void onStarted() {
        isStarted = true;
    }

    @CallSuper
    protected void onStopped() {
        isStarted = false;
    }
}
