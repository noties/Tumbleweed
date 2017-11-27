package ru.noties.tumbleweed.android;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewTreeObserver;

import ru.noties.tumbleweed.TweenManagerImpl;

@SuppressWarnings("WeakerAccess")
public class ViewTweenManager extends TweenManagerImpl {

    @NonNull
    public static ViewTweenManager create(@NonNull View container) {
        return new ViewTweenManager(container);
    }

    private View container;

    private final OnPreDrawListener onPreDrawListener = new OnPreDrawListener();
    private final OnAttachStateChangeListener onAttachStateChangeListener
            = new OnAttachStateChangeListener();
    private final TimeDelta timeDelta = TimeDelta.create();

    ViewTweenManager(@NonNull View container) {
        this.container = container;
        container.addOnAttachStateChangeListener(onAttachStateChangeListener);
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
