package io.noties.tumbleweed.android;

import android.animation.Animator;
import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;

import io.noties.tumbleweed.BaseTween;
import io.noties.tumbleweed.TweenManager;
import io.noties.tumbleweed.TweenManagerImpl;

/**
 * {@link TweenManager} implementation that uses android.animation.Animator
 * underneath. As one example - can be used in transitions
 *
 * @since 2.0.0
 */
public class AnimatorTweenManager extends TweenManagerImpl {

    @NonNull
    public static AnimatorTweenManager create() {
        return new AnimatorTweenManager();
    }

    private final TimeDelta timeDelta = TimeDelta.create();

    private Animator animator;

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onStarted() {
        // no op
        // tweens will automatically start tweenManager which is not what we want here
        // we postpone start until animator starts
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onStopped() {
        // no op
    }

    /**
     * One {@link AnimatorTweenManager} can have one android.animation.Animator. If there is a
     * animator running it will be cancelled.
     */
    @NonNull
    @UiThread
    public Animator animator() {

        // clear-up previous
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }

        animator = createAnimator();

        return animator;
    }

    @NonNull
    private Animator createAnimator() {

        final ValueAnimator animator = ValueAnimator.ofFloat(.0F, 1.F);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                update(timeDelta.delta());
            }
        });
        animator.setEvaluator(new FloatEvaluator());
        animator.setDuration(duration());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

                // mark time
                timeDelta.delta();

                // pass onStarted to super
                AnimatorTweenManager.super.onStarted();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                AnimatorTweenManager.super.onStopped();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                AnimatorTweenManager.super.onStopped();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // no op...
            }
        });

        return animator;
    }

    private long duration() {

        // infinite tweens are problematic here as we have no way to automatically
        // dispose tweenManager, this is why we forbid it by throwing an exception

        float duration = 0.F;

        for (BaseTween tween : tweens()) {

            if (tween.getRepeatCount() < 0) {
                throw new IllegalStateException("Cannot add infinite tweens, tween: " + tween);
            }

            if (Float.compare(tween.getFullDuration(), duration) > 0) {
                duration = tween.getFullDuration();
            }
        }

        // we could also forbid zero as duration...

        return (long) (duration * 1000L + .5F);
    }
}
