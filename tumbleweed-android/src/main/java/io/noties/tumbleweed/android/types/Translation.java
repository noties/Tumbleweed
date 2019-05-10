package io.noties.tumbleweed.android.types;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;

import io.noties.tumbleweed.TweenType;

@SuppressWarnings("unused")
public abstract class Translation implements TweenType<View> {

    @NonNull
    public static final Translation X = new Translation() {
        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull View view, @NonNull float[] values) {
            values[0] = view.getTranslationX();
        }

        @Override
        public void setValues(@NonNull View view, @NonNull float[] values) {
            view.setTranslationX(values[0]);
        }

        @Override
        public String toString() {
            return "Translation.X";
        }
    };

    @NonNull
    public static final Translation Y = new Translation() {
        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull View view, @NonNull float[] values) {
            values[0] = view.getTranslationY();
        }

        @Override
        public void setValues(@NonNull View view, @NonNull float[] values) {
            view.setTranslationY(values[0]);
        }

        @Override
        public String toString() {
            return "Translation.Y";
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    public static final Translation Z = new Translation() {
        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull View view, @NonNull float[] values) {
            values[0] = view.getTranslationZ();
        }

        @Override
        public void setValues(@NonNull View view, @NonNull float[] values) {
            view.setTranslationZ(values[0]);
        }

        @Override
        public String toString() {
            return "Translation.Z";
        }
    };

    @NonNull
    public static final Translation XY = new Translation() {
        @Override
        public int getValuesSize() {
            return 2;
        }

        @Override
        public void getValues(@NonNull View view, @NonNull float[] values) {
            values[0] = view.getTranslationX();
            values[1] = view.getTranslationY();
        }

        @Override
        public void setValues(@NonNull View view, @NonNull float[] values) {
            view.setTranslationX(values[0]);
            view.setTranslationY(values[1]);
        }

        @Override
        public String toString() {
            return "Translation.XY";
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    public static final Translation XYZ = new Translation() {
        @Override
        public int getValuesSize() {
            return 3;
        }

        @Override
        public void getValues(@NonNull View view, @NonNull float[] values) {
            values[0] = view.getTranslationX();
            values[1] = view.getTranslationY();
            values[2] = view.getTranslationZ();
        }

        @Override
        public void setValues(@NonNull View view, @NonNull float[] values) {
            view.setTranslationX(values[0]);
            view.setTranslationY(values[1]);
            view.setTranslationZ(values[2]);
        }

        @Override
        public String toString() {
            return "Translation.XYZ";
        }
    };
}
