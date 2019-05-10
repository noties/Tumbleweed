package io.noties.tumbleweed.equations;

import io.noties.tumbleweed.TweenEquation;

/**
 * Easing equation based on Robert Penner's work:
 * http://robertpenner.com/easing/
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
@SuppressWarnings("unused")
public enum Linear implements TweenEquation {

    INOUT {
        @Override
        public float compute(float t) {
            return t;
        }
    };

    @Override
    public String toString() {
        return "Linear." + name();
    }
}
