package io.noties.tumbleweed.android.kt

import io.noties.tumbleweed.BaseTween
import io.noties.tumbleweed.BaseTweenDef
import io.noties.tumbleweed.TweenCallback

/**
 * Adds [TweenCallback.BEGIN] callback. Will be called once the tween has started
 * @see onComplete
 * @since 2.1.0-SNAPSHOT
 */
fun <T : BaseTweenDef> T.onBegin(onBegin: (BaseTween) -> Unit): T {
    addCallback(TweenCallback.BEGIN) { _, source ->
        onBegin(source)
    }
    return this
}

/**
 * Adds [TweenCallback.START] callback. Will be called once tween started and on each repetition
 * call (if tween is configured with [BaseTweenDef.repeat] or [BaseTweenDef.repeatYoyo]
 * @see onEnd
 * @since 2.1.0-SNAPSHOT
 */
fun <T : BaseTweenDef> T.onStart(onStart: (BaseTween) -> Unit): T {
    addCallback(TweenCallback.START) { _, source ->
        onStart(source)
    }
    return this
}

/**
 * Adds [TweenCallback.END] callback. Will be called once tween finished and on completion of
 * each repetition if tween is configured with [BaseTweenDef.repeat] or [BaseTweenDef.repeatYoyo]
 * @see onStart
 * @since 2.1.0-SNAPSHOT
 */
fun <T : BaseTweenDef> T.onEnd(onEnd: (BaseTween) -> Unit): T {
    addCallback(TweenCallback.END) { _, source ->
        onEnd(source)
    }
    return this
}

/**
 * Adds [TweenCallback.COMPLETE] callback. Will be called on tween completion
 * @see onBegin
 * @since 2.1.0-SNAPSHOT
 */
fun <T : BaseTweenDef> T.onComplete(onComplete: (BaseTween) -> Unit): T {
    addCallback(TweenCallback.COMPLETE) { _, source ->
        onComplete(source)
    }
    return this
}