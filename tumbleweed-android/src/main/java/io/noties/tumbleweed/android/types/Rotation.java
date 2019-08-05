package io.noties.tumbleweed.android.types;

import android.view.View;

import androidx.annotation.NonNull;

import io.noties.tumbleweed.TweenType;

@SuppressWarnings("unused")
public abstract class Rotation implements TweenType<View> {

    @NonNull
    public static final Rotation I = new Rotation() {
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

        @Override
        public String toString() {
            return "Rotation.I";
        }
    };

    /**
     * @since 1.0.1
     */
    @NonNull
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

        @Override
        public String toString() {
            return "Rotation.X";
        }
    };

    /**
     * @since 1.0.1
     */
    @NonNull
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

        @Override
        public String toString() {
            return "Rotation.Y";
        }
    };

    /**
     * @since 1.0.1
     */
    @NonNull
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

        @Override
        public String toString() {
            return "Rotation.XY";
        }
    };
}
