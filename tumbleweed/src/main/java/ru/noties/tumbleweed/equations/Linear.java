package ru.noties.tumbleweed.equations;

import ru.noties.tumbleweed.TweenEquation;

/**
 * Easing equation based on Robert Penner's work:
 * http://robertpenner.com/easing/
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
@SuppressWarnings("unused")
public abstract class Linear extends TweenEquation {

    public static final Linear INOUT = new Linear() {
        @Override
        public float compute(float t) {
            return t;
        }

        @Override
        public String toString() {
            return "Linear.INOUT";
        }
    };
}
