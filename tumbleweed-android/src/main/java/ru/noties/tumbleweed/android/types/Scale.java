package ru.noties.tumbleweed.android.types;

import android.support.annotation.NonNull;
import android.view.View;

import ru.noties.tumbleweed.TweenType;

public abstract class Scale implements TweenType<View> {

    public static final Scale X = new Scale() {
        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull View view, @NonNull float[] values) {
            values[0] = view.getScaleX();
        }

        @Override
        public void setValues(@NonNull View view, @NonNull float[] values) {
            view.setScaleX(values[0]);
        }
    };

    public static final Scale Y = new Scale() {
        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull View view, @NonNull float[] values) {
            values[0] = view.getScaleY();
        }

        @Override
        public void setValues(@NonNull View view, @NonNull float[] values) {
            view.setScaleY(values[0]);
        }
    };

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
    };

    public static final Scale XY_JOINT = new Scale() {
        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull View view, @NonNull float[] values) {
            values[0] = view.getScaleX();
        }

        @Override
        public void setValues(@NonNull View view, @NonNull float[] values) {
            final float scale = values[0];
            view.setScaleX(scale);
            view.setScaleY(scale);
        }
    };
}
