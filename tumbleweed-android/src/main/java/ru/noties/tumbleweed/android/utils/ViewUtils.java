package ru.noties.tumbleweed.android.utils;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewTreeObserver;

public abstract class ViewUtils {

    public interface Action<V extends View> {
        void apply(@NonNull V view);
    }

    public static <V extends View> void whenReady(@NonNull final V v, @NonNull final Action<V> action) {
        if (v.getWidth() == 0
                || v.getHeight() == 0) {
            v.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    v.getViewTreeObserver().removeOnPreDrawListener(this);
                    action.apply(v);
                    return true;
                }
            });
        } else {
            action.apply(v);
        }
    }

    private ViewUtils() {
    }
}
