package ru.noties.tumbleweed.android;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.view.View;
import android.view.ViewTreeObserver;

import ru.noties.tumbleweed.TweenManagerImpl;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ViewTweenManager extends TweenManagerImpl {

    // creates new instance each time called
    @NonNull
    @UiThread
    public static ViewTweenManager create(@NonNull View container) {
        return new ViewTweenManager(0, container);
    }

    // attaches ViewTweenManager to specified View as a tag (creates if it's not created)
    @NonNull
    @UiThread
    public static ViewTweenManager get(@IdRes int key, @NonNull View container) {
        ViewTweenManager manager = (ViewTweenManager) container.getTag(key);
        if (manager == null) {
            // will call `setTag` inside
            manager = new ViewTweenManager(key, container);
        }
        return manager;
    }

    private final int key;
    private View container;

    private final OnPreDrawListener onPreDrawListener = new OnPreDrawListener();
    private final OnAttachStateChangeListener onAttachStateChangeListener
            = new OnAttachStateChangeListener();
    private final TimeDelta timeDelta = TimeDelta.create();

    // @since 1.0.3 visibility is changed to be `protected`
    protected ViewTweenManager(@IdRes int key, @NonNull View container) {
        this.key = key;
        this.container = container;
        container.addOnAttachStateChangeListener(onAttachStateChangeListener);

        if (key != 0) {
            container.setTag(key, this);
        }
    }

    @Override
    protected void onStarted() {
        super.onStarted();

        if (!isDisposed()) {

            // mark current time
            timeDelta.delta();

            // add on pre draw listener to properly update this manager instance
            container.getViewTreeObserver().addOnPreDrawListener(onPreDrawListener);

            // directly call invalidate
            container.invalidate();
        }
    }

    @Override
    protected void onStopped() {
        super.onStopped();

        if (!isDisposed()) {
            container.getViewTreeObserver().removeOnPreDrawListener(onPreDrawListener);
        }
    }

    @Override
    public void dispose() {
        if (!isDisposed()) {

            final ViewTreeObserver observer = container.getViewTreeObserver();
            if (observer.isAlive()) {
                observer.removeOnPreDrawListener(onPreDrawListener);
            }

            if (key != 0) {
                container.setTag(key, null);
            }

            container.removeOnAttachStateChangeListener(onAttachStateChangeListener);

            container = null;
        }
        super.dispose();
    }

    @Override
    public boolean isDisposed() {
        return super.isDisposed() || container == null;
    }

    private class OnPreDrawListener implements ViewTreeObserver.OnPreDrawListener {

        @Override
        public boolean onPreDraw() {

            // as we cannot remove invalidate call after we have stopped,
            // we will additionally check if we are running
            //
            // we could use `postOnAnimation(Runnable)`, but it introduces small overhead
            if (!isDisposed()
                    && isStarted) {

                update(timeDelta.delta());

                container.postInvalidateOnAnimation();
            }

            return true;
        }
    }

    private class OnAttachStateChangeListener implements View.OnAttachStateChangeListener {

        @Override
        public void onViewAttachedToWindow(View v) {
            // no op
        }

        @Override
        public void onViewDetachedFromWindow(View v) {
            dispose();
        }
    }
}
