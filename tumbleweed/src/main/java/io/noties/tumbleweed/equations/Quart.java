package io.noties.tumbleweed.equations;

import io.noties.tumbleweed.TweenEquation;

/**
 * Easing equation based on Robert Penner's work:
 * http://robertpenner.com/easing/
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
@SuppressWarnings("unused")
public enum Quart implements TweenEquation {

    IN {
        @Override
        public float compute(float t) {
            return t * t * t * t;
        }
    },

    OUT {
        @Override
        public float compute(float t) {
            return -((t -= 1) * t * t * t - 1);
        }
    },

    INOUT {
        @Override
        public float compute(float t) {
            if ((t *= 2) < 1) return 0.5f * t * t * t * t;
            return -0.5f * ((t -= 2) * t * t * t - 2);
        }
    };

    @Override
    public String toString() {
        return "Quart." + name();
    }
}
