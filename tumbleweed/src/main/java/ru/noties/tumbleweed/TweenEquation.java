package ru.noties.tumbleweed;

/**
 * Base class for every easing equation. You can create your own equations
 * and directly use them in the Tween engine by inheriting from this class.
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 * @see Tween
 */
public interface TweenEquation {

    /**
     * Computes the next value of the interpolation.
     *
     * @param t The current time, between 0 and 1.
     * @return The current value.
     */
    float compute(float t);
}
