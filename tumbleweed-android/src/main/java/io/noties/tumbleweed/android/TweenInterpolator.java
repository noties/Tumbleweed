package io.noties.tumbleweed.android;

import android.animation.TimeInterpolator;
import android.support.annotation.NonNull;

import io.noties.tumbleweed.TweenEquation;

/**
 * Can be used with <i>native</i> Android animations as an interpolator. For example:
 * {@code valueAnimator.setInterpolator(new TweenInterpolator(Back.INOUT);}
 */
@SuppressWarnings("unused")
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
