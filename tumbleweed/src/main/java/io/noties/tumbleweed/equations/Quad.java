package io.noties.tumbleweed.equations;

import io.noties.tumbleweed.TweenEquation;

/**
 * Easing equation based on Robert Penner\'s work:
 * http://robertpenner.com/easing/
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
@SuppressWarnings("unused")
public enum Quad implements TweenEquation {

    IN {
        @Override
        public float compute(float t) {
            return t * t;
        }
    },

    OUT {
        @Override
        public float compute(float t) {
            return -t * (t - 2);
        }
    },

    INOUT {
        @Override
        public float compute(float t) {
            if ((t *= 2) < 1) return 0.5f * t * t;
            return -0.5f * ((--t) * (t - 2) - 1);
        }
    };

    @Override
    public String toString() {
        return "Quad." + name();
    }
}
