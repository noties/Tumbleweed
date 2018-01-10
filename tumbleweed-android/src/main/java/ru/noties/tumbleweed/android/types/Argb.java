package ru.noties.tumbleweed.android.types;

import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.Size;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import ru.noties.tumbleweed.TweenType;

@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class Argb<T> implements TweenType<T> {

    @NonNull
    public static float[] toArray(@ColorInt int color) {
        return toArray(color, new float[4]);
    }

    @NonNull
    public static float[] toArray(@ColorInt int color, @Size(4) @NonNull float[] values) {
        values[0] = ((color >> 24) & 0xff) / 255.0f;
        values[1] = (float) (Math.pow(((color >> 16) & 0xff) / 255.0f, 2.2));
        values[2] = (float) (Math.pow(((color >> 8) & 0xff) / 255.0f, 2.2));
        values[3] = (float) (Math.pow((color & 0xff) / 255.0f, 2.2));
        return values;
    }

    @ColorInt
    public static int fromArray(@Size(4) @NonNull float[] values) {
        return Math.round(values[0] * 255.F) << 24
                | Math.round((float) Math.pow(values[1], 1.0 / 2.2) * 255.0f) << 16
                | Math.round((float) Math.pow(values[2], 1.0 / 2.2) * 255.0f) << 8
                | Math.round((float) Math.pow(values[3], 1.0 / 2.2) * 255.0f);
    }

    @ColorInt
    protected abstract int getColor(@NonNull T t);

    protected abstract void setColor(@NonNull T t, @ColorInt int color);

    @NonNull
    public static final Argb<View> BACKGROUND = new Argb<View>() {
        @Override
        protected int getColor(@NonNull View view) {
            final int color;
            final Drawable bg = view.getBackground();
            if (bg instanceof ColorDrawable) {
                color = ((ColorDrawable) bg).getColor();
            } else {
                color = 0;
            }
            return color;
        }

        @Override
        protected void setColor(@NonNull View view, @ColorInt int color) {
            view.setBackgroundColor(color);
        }

        @Override
        public String toString() {
            return "Argb.BACKGROUND";
        }
    };

    @NonNull
    public static final Argb<Paint> PAINT = new Argb<Paint>() {
        @Override
        protected int getColor(@NonNull Paint paint) {
            return paint.getColor();
        }

        @Override
        protected void setColor(@NonNull Paint paint, @ColorInt int color) {
            paint.setColor(color);
        }

        @Override
        public String toString() {
            return "Argb.PAINT";
        }
    };

    @NonNull
    public static final Argb<TextView> TEXT_COLOR = new Argb<TextView>() {
        @Override
        protected int getColor(@NonNull TextView textView) {
            return textView.getCurrentTextColor();
        }

        @Override
        protected void setColor(@NonNull TextView textView, int color) {
            textView.setTextColor(color);
        }

        @Override
        public String toString() {
            return "Argb.TEXT_COLOR";
        }
    };

    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static final Argb<Window> STATUS_BAR = new Argb<Window>() {

        @Override
        protected int getColor(@NonNull Window window) {
            return window.getStatusBarColor();
        }

        @Override
        protected void setColor(@NonNull Window window, int color) {
            window.setStatusBarColor(color);
        }

        @Override
        public String toString() {
            return "Argb.STATUS_BAR";
        }
    };

    @NonNull
    public static final Argb<ColorDrawable> COLOR_DRAWABLE = new Argb<ColorDrawable>() {
        @Override
        protected int getColor(@NonNull ColorDrawable colorDrawable) {
            return colorDrawable.getColor();
        }

        @Override
        protected void setColor(@NonNull ColorDrawable colorDrawable, int color) {
            colorDrawable.setColor(color);
        }

        @Override
        public String toString() {
            return "Argb.COLOR_DRAWABLE";
        }
    };

    @Override
    public int getValuesSize() {
        return 4;
    }

    @Override
    public void getValues(@NonNull T t, @NonNull float[] values) {
        toArray(getColor(t), values);
    }

    @Override
    public void setValues(@NonNull T t, @NonNull float[] values) {
        setColor(t, fromArray(values));
    }
}
