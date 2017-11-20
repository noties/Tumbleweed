package ru.noties.tumbleweed.android;

import android.animation.TimeInterpolator;
import android.support.annotation.NonNull;

import ru.noties.tumbleweed.TweenEquation;

public class TweenInterpolator implements TimeInterpolator {

    private final TweenEquation equation;

    public TweenInterpolator(@NonNull TweenEquation equation) {
        this.equation = equation;
    }

    @Override
    public float getInterpolation(float input) {
        return equation.compute(input);
    }
}
