package ru.noties.tumbleweed.android.types;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;

import ru.noties.tumbleweed.TweenType;

public abstract class Alpha<T> implements TweenType<T> {

    @NonNull
    public static final Alpha<View> VIEW = new Alpha<View>() {
        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull View view, @NonNull float[] values) {
            values[0] = view.getAlpha();
        }

        @Override
        public void setValues(@NonNull View view, @NonNull float[] values) {
            view.setAlpha(values[0]);
        }

        @Override
        public String toString() {
            return "Alpha.VIEW";
        }
    };

    /**
     * Please note that alpha values in range [0..1] are used (not 0-255)
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    public static final Alpha<Drawable> DRAWABLE = new Alpha<Drawable>() {
        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull Drawable drawable, @NonNull float[] values) {
            values[0] = drawable.getAlpha() / 255.F;
        }

        @Override
        public void setValues(@NonNull Drawable drawable, @NonNull float[] values) {
            drawable.setAlpha((int) (values[0] * 255 + .5F));
        }

        @Override
        public String toString() {
            return "Alpha.DRAWABLE";
        }
    };

    /**
     * Please note that alpha values in range [0..1] are used (not 0-255)
     */
    @NonNull
    public static final Alpha<Paint> PAINT = new Alpha<Paint>() {
        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull Paint paint, @NonNull float[] values) {
            values[0] = paint.getAlpha() / 255.F;
        }

        @Override
        public void setValues(@NonNull Paint paint, @NonNull float[] values) {
            paint.setAlpha((int) (values[0] * 255 + .5F));
        }

        @Override
        public String toString() {
            return "Alpha.PAINT";
        }
    };
}
