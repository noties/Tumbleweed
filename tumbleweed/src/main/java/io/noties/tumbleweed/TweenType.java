package io.noties.tumbleweed;

import android.support.annotation.NonNull;

public interface TweenType<T> {

    int getValuesSize();

    void getValues(@NonNull T t, @NonNull float[] values);

    void setValues(@NonNull T t, @NonNull float[] values);
}
