package io.noties.tumbleweed.android.kt

import io.noties.tumbleweed.BaseTweenDef
import io.noties.tumbleweed.android.ViewTweenManager

/**
 * Will start when attached View has measured dimensions
 * @see [whenReady]
 * @since 2.1.0
 */
fun ViewTweenManager.startWhenReady(body: ViewTweenManager.() -> BaseTweenDef) {
    view().whenReady {
        body(this).start(this)
    }
}