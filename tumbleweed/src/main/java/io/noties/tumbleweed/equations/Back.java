package io.noties.tumbleweed.equations;

import io.noties.tumbleweed.TweenEquation;

/**
 * Easing equation based on Robert Penner\'s work:
 * http://robertpenner.com/easing/
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
@SuppressWarnings("unused")
public enum Back implements TweenEquation {

    IN {
        @Override
        public float compute(float t) {
            final float s = param_s;
            return t * t * ((s + 1) * t - s);
        }
    },

    OUT {
        @Override
        public float compute(float t) {
            final float s = param_s;
            return (t -= 1) * t * ((s + 1) * t + s) + 1;
        }
    },

    INOUT {
        @Override
        public float compute(float t) {
            float s = param_s;
            if ((t *= 2) < 1) return 0.5f * (t * t * (((s *= (1.525f)) + 1) * t - s));
            return 0.5f * ((t -= 2) * t * (((s *= (1.525f)) + 1) * t + s) + 2);
        }
    };

    private static final float param_s = 1.70158f;


    @Override
    public String toString() {
        return "Back." + name();
    }
}
