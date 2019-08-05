package io.noties.tumbleweed.equations;

import io.noties.tumbleweed.TweenEquation;

/**
 * Easing equation based on Robert Penner\'s work:
 * http://robertpenner.com/easing/
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public enum Elastic implements TweenEquation {

    IN {
        @Override
        public float compute(float t) {
            float a = param_a;
            float p = param_p;
            if (t == 0) return 0;
            if (t == 1) return 1;
            float s = p / 4;
            return -(a * (float) Math.pow(2, 10 * (t -= 1)) * (float) Math.sin((t - s) * (2 * PI) / p));
        }
    },

    OUT {
        @Override
        public float compute(float t) {
            float a = param_a;
            float p = param_p;
            if (t == 0) return 0;
            if (t == 1) return 1;
            float s = p / 4;
            return a * (float) Math.pow(2, -10 * t) * (float) Math.sin((t - s) * (2 * PI) / p) + 1;
        }
    },

    INOUT {
        @Override
        public float compute(float t) {
            float a = param_a;
            float p = param_p * 1.5F;
            if (t == 0) return 0;
            if ((t *= 2) == 2) return 1;
            float s = p / 4;
            if (t < 1)
                return -.5f * (a * (float) Math.pow(2, 10 * (t -= 1)) * (float) Math.sin((t - s) * (2 * PI) / p));
            return a * (float) Math.pow(2, -10 * (t -= 1)) * (float) Math.sin((t - s) * (2 * PI) / p) * .5f + 1;
        }
    };

    private static final float PI = 3.14159265f;

    private static final float param_a = 1.F;
    private static final float param_p = .3F;

    @Override
    public String toString() {
        return "Elastic." + name();
    }
}
