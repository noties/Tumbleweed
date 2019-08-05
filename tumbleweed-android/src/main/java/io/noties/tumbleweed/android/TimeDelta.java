package io.noties.tumbleweed.android;

import android.os.SystemClock;

import androidx.annotation.NonNull;

@SuppressWarnings("WeakerAccess")
public abstract class TimeDelta {

    @NonNull
    public static TimeDelta create() {
        return new Impl();
    }

    public abstract float delta();


    private static class Impl extends TimeDelta {

        private long lastCallTime;

        @Override
        public float delta() {

            final long now = SystemClock.uptimeMillis();

            final float delta;

            if (lastCallTime == 0L) {
                delta = .0F;
            } else {
                delta = (now - lastCallTime) / 1000.F;
            }

            lastCallTime = now;

            return delta;
        }
    }
}
