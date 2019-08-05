package io.noties.tumbleweed.paths;

import androidx.annotation.NonNull;

import io.noties.tumbleweed.TweenPath;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
@SuppressWarnings("unused")
public class Linear implements TweenPath {

    private static final Linear INSTANCE = new Linear();

    @NonNull
    public static Linear instance() {
        return INSTANCE;
    }

    @Override
    public float compute(float t, float[] points, int pointsCnt) {
        int segment = (int) Math.floor((pointsCnt - 1) * t);
        segment = Math.max(segment, 0);
        segment = Math.min(segment, pointsCnt - 2);

        t = t * (pointsCnt - 1) - segment;

        return points[segment] + t * (points[segment + 1] - points[segment]);
    }
}
