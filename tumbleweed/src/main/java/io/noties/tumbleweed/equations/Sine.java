package io.noties.tumbleweed.equations;

import io.noties.tumbleweed.TweenEquation;

/**
 * Easing equation based on Robert Penner's work:
 * http://robertpenner.com/easing/
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
@SuppressWarnings("unused")
public enum Sine implements TweenEquation {

    IN {
        @Override
        public float compute(float t) {
            return (float) -Math.cos(t * (PI / 2)) + 1;
        }
    },

    OUT {
        @Override
        public float compute(float t) {
            return (float) Math.sin(t * (PI / 2));
        }
    },

    INOUT {
        @Override
        public float compute(float t) {
            return -0.5f * ((float) Math.cos(PI * t) - 1);
        }
    };

    private static final float PI = 3.14159265f;

    @Override
    public String toString() {
        return "Sine." + name();
    }
}
