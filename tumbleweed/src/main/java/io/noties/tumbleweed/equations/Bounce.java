package io.noties.tumbleweed.equations;

import io.noties.tumbleweed.TweenEquation;

/**
 * Easing equation based on Robert Penner\'s work:
 * http://robertpenner.com/easing/
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
@SuppressWarnings("unused")
public enum Bounce implements TweenEquation {

    IN {
        @Override
        public float compute(float t) {
            return 1 - OUT.compute(1 - t);
        }
    },

    OUT {
        @Override
        public float compute(float t) {
            if (t < (1 / 2.75)) {
                return 7.5625f * t * t;
            } else if (t < (2 / 2.75)) {
                return 7.5625f * (t -= (1.5f / 2.75f)) * t + .75f;
            } else if (t < (2.5 / 2.75)) {
                return 7.5625f * (t -= (2.25f / 2.75f)) * t + .9375f;
            } else {
                return 7.5625f * (t -= (2.625f / 2.75f)) * t + .984375f;
            }
        }
    },

    INOUT {
        @Override
        public float compute(float t) {
            if (t < 0.5f) return IN.compute(t * 2) * .5f;
            else return OUT.compute(t * 2 - 1) * .5f + 0.5f;
        }
    };

    @Override
    public String toString() {
        return "Bounce." + name();
    }
}
