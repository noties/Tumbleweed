package ru.noties.tumbleweed.android.types;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;

import ru.noties.tumbleweed.TweenType;

public abstract class Alpha<T> implements TweenType<T> {

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
    };

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
    };
}
