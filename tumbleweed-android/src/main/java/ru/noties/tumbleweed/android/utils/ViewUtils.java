package ru.noties.tumbleweed.android.utils;

import android.graphics.Point;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewTreeObserver;

@SuppressWarnings({"WeakerAccess", "unused"})
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

    @NonNull
    public static Point relativeTo(@NonNull View parent, @NonNull View who) {
        return relativeTo(parent, who, new Point());
    }

    @NonNull
    public static Point relativeTo(@NonNull View parent, @NonNull View who, @NonNull Point point) {
        point.x += who.getLeft();
        point.y += who.getTop();
        if (who != parent
                && who.getParent() instanceof View) {
            relativeTo(parent, (View) who.getParent(), point);
        }
        return point;
    }

    private ViewUtils() {
    }
}
