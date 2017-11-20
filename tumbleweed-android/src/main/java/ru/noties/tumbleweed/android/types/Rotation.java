package ru.noties.tumbleweed.android.types;

import android.support.annotation.NonNull;
import android.view.View;

import ru.noties.tumbleweed.TweenType;

public abstract class Rotation implements TweenType<View> {

    public static final Rotation X = new Rotation() {
        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull View view, @NonNull float[] values) {
            values[0] = view.getRotationX();
        }

        @Override
        public void setValues(@NonNull View view, @NonNull float[] values) {
            view.setRotationX(values[0]);
        }
    };

    public static final Rotation Y = new Rotation() {
        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull View view, @NonNull float[] values) {
            values[0] = view.getRotationY();
        }

        @Override
        public void setValues(@NonNull View view, @NonNull float[] values) {
            view.setRotationY(values[0]);
        }
    };

    public static final Rotation XY = new Rotation() {
        @Override
        public int getValuesSize() {
            return 2;
        }

        @Override
        public void getValues(@NonNull View view, @NonNull float[] values) {
            values[0] = view.getRotationX();
            values[1] = view.getRotationY();
        }

        @Override
        public void setValues(@NonNull View view, @NonNull float[] values) {
            view.setRotationX(values[0]);
            view.setRotationY(values[1]);
        }
    };

    public static final Rotation XY_JOINT = new Rotation() {
        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull View view, @NonNull float[] values) {
            values[0] = view.getRotation();
        }

        @Override
        public void setValues(@NonNull View view, @NonNull float[] values) {
            view.setRotation(values[0]);
        }
    };
}
