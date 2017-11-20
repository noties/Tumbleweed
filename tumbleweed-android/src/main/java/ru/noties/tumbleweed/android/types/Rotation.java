package ru.noties.tumbleweed.android.types;

import android.support.annotation.NonNull;
import android.view.View;

import ru.noties.tumbleweed.TweenType;

public class Rotation implements TweenType<View> {

    public static final Rotation I = new Rotation();

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
}
