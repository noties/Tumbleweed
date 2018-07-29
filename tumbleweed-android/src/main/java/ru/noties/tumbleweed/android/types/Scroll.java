package ru.noties.tumbleweed.android.types;

import android.support.annotation.NonNull;
import android.view.View;

import ru.noties.tumbleweed.TweenType;

/**
 * @since 1.0.3
 */
@SuppressWarnings("unused")
public abstract class Scroll implements TweenType<View> {

    @NonNull
    public static final Scroll X = new Scroll() {
        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull View view, @NonNull float[] values) {
            values[0] = view.getScrollX();
        }

        @Override
        public void setValues(@NonNull View view, @NonNull float[] values) {
            view.setScrollX((int) (values[0] + .5F));
        }

        @Override
        public String toString() {
            return "Scroll.X";
        }
    };

    @NonNull
    public static final Scroll Y = new Scroll() {
        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull View view, @NonNull float[] values) {
            values[0] = view.getScrollY();
        }

        @Override
        public void setValues(@NonNull View view, @NonNull float[] values) {
            view.setScrollY((int) (values[0] + .5F));
        }

        @Override
        public String toString() {
            return "Scroll.Y";
        }
    };

    @NonNull
    public static final Scroll XY = new Scroll() {
        @Override
        public int getValuesSize() {
            return 2;
        }

        @Override
        public void getValues(@NonNull View view, @NonNull float[] values) {
            values[0] = view.getScrollX();
            values[1] = view.getScrollY();
        }

        @Override
        public void setValues(@NonNull View view, @NonNull float[] values) {
            view.scrollTo(
                    (int) (values[0] + .5F),
                    (int) (values[1] + .5F)
            );
        }

        @Override
        public String toString() {
            return "Scroll.XY";
        }
    };
}
