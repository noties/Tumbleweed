package ru.noties.tumbleweed.android;

import android.support.annotation.NonNull;

import java.util.List;

import ru.noties.tumbleweed.BaseTween;
import ru.noties.tumbleweed.TweenManager;

public class AndroidTweenManager extends TweenManager {

    @NonNull
    @Override
    public TweenManager add(@NonNull BaseTween object) {
        return null;
    }

    @Override
    public boolean containsTarget(@NonNull Object target) {
        return false;
    }

    @Override
    public void killAll() {

    }

    @Override
    public void killTarget(@NonNull Object target) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int getRunningTweensCount() {
        return 0;
    }

    @Override
    public int getRunningTimelinesCount() {
        return 0;
    }

    @NonNull
    @Override
    public List<BaseTween> getObjects() {
        return null;
    }
}
