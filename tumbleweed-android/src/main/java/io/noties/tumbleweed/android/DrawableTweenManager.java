package io.noties.tumbleweed.android;

import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.annotation.NonNull;

import io.noties.tumbleweed.TweenManagerImpl;

@SuppressWarnings("WeakerAccess")
public class DrawableTweenManager extends TweenManagerImpl {

    @NonNull
    public static DrawableTweenManager create(@NonNull Drawable drawable) {
        return create(drawable, 1.F / 60);
    }

    /**
     * Factory method to create an instance of io.noties.tumbleweed.TweenManager to operate
     * on android Drawable. Due to the fact that we rely on Drawable.Callback to dispatch redraw
     * messages, supplied drawable must have Callback set.
     *
     * @param drawable to operate on
     * @param frame    redraw interval in seconds
     * @return a new instance of {@link DrawableTweenManager}
     */
    @NonNull
    public static DrawableTweenManager create(@NonNull Drawable drawable, float frame) {
        return new DrawableTweenManager(drawable, frame);
    }

    private final Drawable drawable;
    private final long frame;
    private final Runnable runnable;
    private final TimeDelta timeDelta = TimeDelta.create();

    // @since 1.0.3 visibility is changed to be `protected`
    protected DrawableTweenManager(@NonNull Drawable drawable, float frame) {
        this.drawable = drawable;
        this.frame = (long) (frame * 1000L + .5F);
        this.runnable = new InvalidateRunnable();
    }

    @Override
    protected void onStarted() {
        super.onStarted();

        if (drawable.getCallback() == null) {
            isStarted = false;
        } else {

            // mark current frame
            timeDelta.delta();

            // we should validate that +1L would do the scheduling
            drawable.scheduleSelf(runnable, SystemClock.uptimeMillis() + 1L);
        }
    }

    @Override
    protected void onStopped() {
        super.onStopped();

        drawable.unscheduleSelf(runnable);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        // first check for callback (higher priority), then check if we are running
        if (drawable.getCallback() == null) {
            onStopped();
        } else if (isStarted && !isPaused()) {
            drawable.invalidateSelf();
        }
    }

    private class InvalidateRunnable implements Runnable {

        @Override
        public void run() {

            update(timeDelta.delta());

            // it's actually important to check that we are running, as it's possible
            // that `update` stops the animation (for example drawable was detached),
            // so, without check for `isStarted` we might be calling update even when we are
            // not running
            if (isStarted) {
                drawable.scheduleSelf(this, SystemClock.uptimeMillis() + frame);
            }
        }
    }
}
