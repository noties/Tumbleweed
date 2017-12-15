package ru.noties.tumbleweed.android.types;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;

import ru.noties.tumbleweed.TweenType;

@SuppressWarnings("unused")
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public abstract class Elevation implements TweenType<View> {

    @NonNull
    public static final Elevation I = new Elevation() {
        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull View view, @NonNull float[] values) {
            values[0] = view.getElevation();
        }

        @Override
        public void setValues(@NonNull View view, @NonNull float[] values) {
            view.setElevation(values[0]);
        }

        @Override
        public String toString() {
            return "Elevation.I";
        }
    };
}
