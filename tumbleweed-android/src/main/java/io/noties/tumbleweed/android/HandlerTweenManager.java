package io.noties.tumbleweed.android;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import io.noties.tumbleweed.TweenManagerImpl;

@SuppressWarnings({"unused", "WeakerAccess"})
public class HandlerTweenManager extends TweenManagerImpl {

    @NonNull
    public static HandlerTweenManager create() {
        return create(1.F / 60);
    }

    @NonNull
    public static HandlerTweenManager create(float frame) {
        return create(frame, new Handler(Looper.getMainLooper()));
    }

    @NonNull
    public static HandlerTweenManager create(float frame, @NonNull Handler handler) {
        return new HandlerTweenManager(frame, handler);
    }

    private final long delay;
    private final Handler handler;
    private final Runnable runnable;
    private final TimeDelta timeDelta = TimeDelta.create();

    // @since 1.0.3 visibility is changed to be `protected`
    protected HandlerTweenManager(float frame, @NonNull Handler handler) {
        this.delay = (long) (frame * 1000.F + .5F);
        this.handler = handler;
        this.runnable = new InvalidateRunnable();
    }

    @Override
    protected void onStarted() {
        super.onStarted();

        timeDelta.delta();

        handler.post(runnable);
    }

    @Override
    protected void onStopped() {
        super.onStopped();

        handler.removeCallbacks(runnable);
    }

    private class InvalidateRunnable implements Runnable {

        @Override
        public void run() {

            update(timeDelta.delta());

            // validate that we are running before _posting_ again (update _possibly_ can
            // stop running state)
            if (isStarted) {
                handler.postDelayed(this, delay);
            }
        }
    }
}
