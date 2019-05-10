package io.noties.tumbleweed;

import android.support.annotation.NonNull;

public interface TweenAction<T> {
    void apply(@NonNull T t);
}
