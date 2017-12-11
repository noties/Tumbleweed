package ru.noties.tumbleweed.equations;

import ru.noties.tumbleweed.TweenEquation;

/**
 * Easing equation based on Robert Penner's work:
 * http://robertpenner.com/easing/
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
@SuppressWarnings("unused")
public enum Circ implements TweenEquation {

    IN {
        @Override
        public float compute(float t) {
            return -((float) Math.sqrt(1 - t * t) - 1);
        }
    },

    OUT {
        @Override
        public float compute(float t) {
            return (float) Math.sqrt(1 - (t -= 1) * t);
        }
    },

    INOUT {
        @Override
        public float compute(float t) {
            if ((t *= 2) < 1) return -0.5f * ((float) Math.sqrt(1 - t * t) - 1);
            return 0.5f * ((float) Math.sqrt(1 - (t -= 2) * t) + 1);
        }
    };

    @Override
    public String toString() {
        return "Circ." + name();
    }
}
