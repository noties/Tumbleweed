package io.noties.tumbleweed;

import androidx.annotation.NonNull;

/**
 * Adapter class to simplify callbacks. Please note that this class ignores all `BACK_*` events.
 * This is due to the fact that `BACK_*` events are called when Tween or TweenManager are updated
 * with negative time deltas. All predefined TweenManagers in Tumbleweed move progress only further
 * and no back events can ever happen. This is why a simplified version is added. If you plan
 * to have negative time deltas you can still use regular {@link BaseTweenDef#addCallback(int, TweenCallback)}
 * method.
 *
 * @see #onBegin(BaseTween)
 * @see #onStart(BaseTween)
 * @see #onEnd(BaseTween)
 * @see #onComplete(BaseTween)
 * @since 2.1.0-SNAPSHOT
 */
public abstract class TweenCallbackAdapter implements TweenCallback {

    @Override
    public final void onEvent(int type, @NonNull BaseTween source) {
        if ((TweenCallback.BEGIN & type) > 0) {
            onBegin(source);
        } else if ((TweenCallback.START & type) > 0) {
            onStart(source);
        } else if ((TweenCallback.END & type) > 0) {
            onEnd(source);
        } else if ((TweenCallback.COMPLETE & type) > 0) {
            onComplete(source);
        }
    }

    /**
     * Will be triggered when Tween is started for the first time. This callback won\'t be
     * delivered on tween repeats (unlike {@link #onStart(BaseTween)}).
     */
    public void onBegin(@NonNull BaseTween source) {
    }

    /**
     * Will be called when Tween is first started and on each repeated launch. Each repetition will
     * have {@link #onEnd(BaseTween)} call. For a more generic callback that will be triggered only at
     * the start (ignoring repetitions) use {@link #onBegin(BaseTween)}
     */
    public void onStart(@NonNull BaseTween source) {
    }

    /**
     * Will be called on at the end of each repetition and when Tween is complete. For a more
     * generic callback that will be triggered only with Tween completion use {@link #onComplete(BaseTween)}
     */
    public void onEnd(@NonNull BaseTween source) {
    }

    /**
     * Will be called when Tween has finished (including all repetitions). After this callback a Tween
     * will no longer be run
     */
    public void onComplete(@NonNull BaseTween source) {
    }
}
