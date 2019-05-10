package ru.noties.tumbleweed.android;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.view.View;
import android.view.ViewTreeObserver;

import ru.noties.tumbleweed.TweenManagerImpl;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ViewTweenManager extends TweenManagerImpl {

    /**
     * @see #get(View, Action)
     * @since 2.0.0-SNAPSHOT
     */
    public interface Action {
        void apply(@NonNull ViewTweenManager viewTweenManager);
    }

    /**
     * Implementation of {@link Action} to kill all currently running tweens. Should be
     * supplied to {@link #get(View, Action)} method call.
     *
     * @see #get(View, Action)
     * @since 2.0.0-SNAPSHOT
     */
    public static final Action KILL_ALL = new Action() {
        @Override
        public void apply(@NonNull ViewTweenManager viewTweenManager) {
            viewTweenManager.killAll();
        }
    };

    /**
     * A method to obtain a ViewTweenManager for specified view. Ensures that only one
     * ViewTweenManager is associated with specified view. Internally sets tag to cache
     * ViewTweenManager instance.
     *
     * @see #get(View, Action)
     * @since 2.0.0-SNAPSHOT
     */
    @NonNull
    @UiThread
    public static ViewTweenManager get(@NonNull View container) {

        ViewTweenManager manager =
                (ViewTweenManager) container.getTag(R.id.tumbleweed_internal_view_tween_manager);

        if (manager == null) {
            // will call `setTag` inside
            manager = new ViewTweenManager(R.id.tumbleweed_internal_view_tween_manager, container);
        }

        return manager;
    }

    /**
     * Method to obtain ViewTweenManager associated with a view and process it
     * with supplied {@link Action}. Can be useful
     * when a new set of tweens must be started but currently? running must be stopped:
     * <p>
     * {@code ViewTweenManager.get(container, ViewTweenManager.KILL_ALL)}.
     * <p>
     * {@code ViewTweenManager.KILL_ALL} is a predefined {@link Action}
     * that kills all currently running tweens.
     *
     * @param init {@link Action} to be called when ViewTweenManager is obtained
     * @see #get(View)
     * @see #KILL_ALL
     * @since 2.0.0-SNAPSHOT
     */
    @NonNull
    @UiThread
    public static ViewTweenManager get(@NonNull View container, @NonNull Action init) {
        final ViewTweenManager viewTweenManager = get(container);
        init.apply(viewTweenManager);
        return viewTweenManager;
    }

    /**
     * Creates a new instance of ViewTweenManager with each call. Consider caching created
     * ViewTweenManager instance or retrieve it via {@link #get(View)} method call.
     */
    @NonNull
    @UiThread
    public static ViewTweenManager create(@NonNull View container) {
        return new ViewTweenManager(0, container);
    }

    /**
     * @see #get(View)
     * @deprecated 2.0.0-SNAPSHOT use {@link #get(View)} method call instead as there is no real
     * need to have multiple ViewTweenManagers associated with a view
     */
    @NonNull
    @UiThread
    @Deprecated
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

                // still check for `isStarted` before posting an update
                if (isStarted) {
                    container.postInvalidateOnAnimation();
                }
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
