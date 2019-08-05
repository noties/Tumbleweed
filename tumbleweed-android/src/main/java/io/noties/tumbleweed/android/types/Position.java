package io.noties.tumbleweed.android.types;

import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import io.noties.tumbleweed.TweenType;

/**
 * @since 1.0.1
 */
@SuppressWarnings("unused")
public abstract class Position implements TweenType<View> {

    @NonNull
    public static final Position X = new Position() {
        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull View view, @NonNull float[] values) {
            values[0] = view.getX();
        }

        @Override
        public void setValues(@NonNull View view, @NonNull float[] values) {
            view.setX(values[0]);
        }

        @Override
        public String toString() {
            return "Position.X";
        }
    };

    @NonNull
    public static final Position Y = new Position() {
        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull View view, @NonNull float[] values) {
            values[0] = view.getY();
        }

        @Override
        public void setValues(@NonNull View view, @NonNull float[] values) {
            view.setY(values[0]);
        }

        @Override
        public String toString() {
            return "Position.Y";
        }
    };

    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static final Position Z = new Position() {
        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull View view, @NonNull float[] values) {
            values[0] = view.getZ();
        }

        @Override
        public void setValues(@NonNull View view, @NonNull float[] values) {
            view.setZ(values[0]);
        }

        @Override
        public String toString() {
            return "Position.Z";
        }

    };

    @NonNull
    public static final Position XY = new Position() {
        @Override
        public int getValuesSize() {
            return 2;
        }

        @Override
        public void getValues(@NonNull View view, @NonNull float[] values) {
            values[0] = view.getX();
            values[1] = view.getY();
        }

        @Override
        public void setValues(@NonNull View view, @NonNull float[] values) {
            view.setX(values[0]);
            view.setY(values[1]);
        }

        @Override
        public String toString() {
            return "Position.XY";
        }
    };

    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static final Position XYZ = new Position() {
        @Override
        public int getValuesSize() {
            return 3;
        }

        @Override
        public void getValues(@NonNull View view, @NonNull float[] values) {
            values[0] = view.getX();
            values[1] = view.getY();
            values[2] = view.getZ();
        }

        @Override
        public void setValues(@NonNull View view, @NonNull float[] values) {
            view.setX(values[0]);
            view.setY(values[1]);
            view.setZ(values[2]);
        }

        @Override
        public String toString() {
            return "Position.XYZ";
        }
    };
}
