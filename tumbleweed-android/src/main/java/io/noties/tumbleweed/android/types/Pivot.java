package io.noties.tumbleweed.android.types;

import android.view.View;

import androidx.annotation.NonNull;

import io.noties.tumbleweed.TweenType;

/**
 * @since 1.0.1
 */
@SuppressWarnings("unused")
public abstract class Pivot implements TweenType<View> {

    @NonNull
    public static final Pivot X = new Pivot() {
        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull View view, @NonNull float[] values) {
            values[0] = view.getPivotX();
        }

        @Override
        public void setValues(@NonNull View view, @NonNull float[] values) {
            view.setPivotX(values[0]);
        }

        @Override
        public String toString() {
            return "Pivot.X";
        }
    };

    @NonNull
    public static final Pivot Y = new Pivot() {
        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull View view, @NonNull float[] values) {
            values[0] = view.getPivotY();
        }

        @Override
        public void setValues(@NonNull View view, @NonNull float[] values) {
            view.setPivotY(values[0]);
        }

        @Override
        public String toString() {
            return "Pivot.Y";
        }
    };

    @NonNull
    public static final Pivot XY = new Pivot() {
        @Override
        public int getValuesSize() {
            return 2;
        }

        @Override
        public void getValues(@NonNull View view, @NonNull float[] values) {
            values[0] = view.getPivotX();
            values[1] = view.getPivotY();
        }

        @Override
        public void setValues(@NonNull View view, @NonNull float[] values) {
            view.setPivotX(values[0]);
            view.setPivotY(values[1]);
        }

        @Override
        public String toString() {
            return "Pivot.XY";
        }
    };
}
