package ru.noties.tumbleweed.android.types;

import android.support.annotation.NonNull;
import android.view.View;

import ru.noties.tumbleweed.TweenType;

public abstract class Scale implements TweenType<View> {

    public static final Scale XY = new Scale() {
        @Override
        public int getValuesSize() {
            return 2;
        }

        @Override
        public void getValues(@NonNull View view, @NonNull float[] values) {
            values[0] = view.getScaleX();
            values[1] = view.getScaleY();
        }

        @Override
        public void setValues(@NonNull View view, @NonNull float[] values) {
            view.setScaleX(values[0]);
            view.setScaleY(values[1]);
        }

        @Override
        public String toString() {
            return "Scale.XY";
        }
    };

    public static final Scale PIVOT_XY = new Scale() {
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
    };
}
