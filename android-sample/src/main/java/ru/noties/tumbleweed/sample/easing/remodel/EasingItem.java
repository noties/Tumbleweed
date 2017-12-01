package ru.noties.tumbleweed.sample.easing.remodel;

import android.support.annotation.NonNull;

import ru.noties.tumbleweed.TweenEquation;

public abstract class EasingItem {

    public static class Header extends EasingItem {

        private final String text;

        public Header(@NonNull String text) {
            this.text = text;
        }

        @NonNull
        public String text() {
            return text;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Header header = (Header) o;

            return text.equals(header.text);
        }

        @Override
        public int hashCode() {
            return text.hashCode();
        }
    }

    public static class Easing extends EasingItem {

        private final String name;
        private final TweenEquation equation;
        private final int hashCode;

        // I have no idea why, but hashCodes are the same for the same ordinals (for different classes)
        public Easing(@NonNull Enum<? extends TweenEquation> equation) {
            this.name = equation.name();
            this.equation = (TweenEquation) equation;
            this.hashCode = (equation.getClass().getName() + "." + equation.name()).hashCode();
        }

        @NonNull
        public String name() {
            return name;
        }

        @NonNull
        public TweenEquation equation() {
            return equation;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Easing easing = (Easing) o;

            return equation.equals(easing.equation);
        }

        @Override
        public int hashCode() {
            return hashCode;
        }
    }

    private EasingItem() {
    }
}
