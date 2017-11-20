package ru.noties.tumbleweed.android.types;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import ru.noties.tumbleweed.TweenType;

public abstract class Color<T> implements TweenType<T> {

    public static abstract class Argb<T> extends Color<T> {

        @Override
        public int getValuesSize() {
            return 4;
        }

        @Override
        public void getValues(@NonNull T t, @NonNull float[] values) {
            argbToValues(getColor(t), values);
        }

        @Override
        public void setValues(@NonNull T t, @NonNull float[] values) {
            setColor(t, valuesToArgb(values));
        }
    }

    @ColorInt
    protected abstract int getColor(@NonNull T t);

    protected abstract void setColor(@NonNull T t, @ColorInt int color);

    public static float[] argb(@ColorInt int color) {
        final float[] values = new float[4];
        argbToValues(color, values);
        return values;
    }

    protected static void argbToValues(@ColorInt int color, @NonNull float[] values) {
        values[0] = ((color >> 24) & 0xff) / 255.0f;
        values[1] = (float) (Math.pow(((color >> 16) & 0xff) / 255.0f, 2.2));
        values[2] = (float) (Math.pow(((color >> 8) & 0xff) / 255.0f, 2.2));
        values[3] = (float) (Math.pow((color & 0xff) / 255.0f, 2.2));
    }

    @ColorInt
    protected int valuesToArgb(@NonNull float[] values) {
        return
                (Math.round(values[0] * 255.F) << 24)
                        | (Math.round((float) Math.pow(values[1], 1.0 / 2.2) * 255.0f) << 16)
                        | (Math.round((float) Math.pow(values[2], 1.0 / 2.2) * 255.0f) << 8)
                        | Math.round((float) Math.pow(values[3], 1.0 / 2.2) * 255.0f);
    }
}
