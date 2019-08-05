package io.noties.tumbleweed.equations;

import io.noties.tumbleweed.TweenEquation;

/**
 * Easing equation based on Robert Penner\'s work:
 * http://robertpenner.com/easing/
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
@SuppressWarnings("unused")
public enum Expo implements TweenEquation {

    IN {
        @Override
        public float compute(float t) {
            return (t == 0) ? 0 : (float) Math.pow(2, 10 * (t - 1));
        }
    },

    OUT {
        @Override
        public float compute(float t) {
            return (t == 1) ? 1 : -(float) Math.pow(2, -10 * t) + 1;
        }
    },

    INOUT {
        @Override
        public float compute(float t) {
            if (t == 0) return 0;
            if (t == 1) return 1;
            if ((t *= 2) < 1) return 0.5f * (float) Math.pow(2, 10 * (t - 1));
            return 0.5f * (-(float) Math.pow(2, -10 * --t) + 2);
        }
    };

    @Override
    public String toString() {
        return "Expo." + name();
    }
}
