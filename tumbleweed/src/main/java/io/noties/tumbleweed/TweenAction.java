package io.noties.tumbleweed;

import androidx.annotation.NonNull;

public interface TweenAction<T> {
    void apply(@NonNull T t);
}
