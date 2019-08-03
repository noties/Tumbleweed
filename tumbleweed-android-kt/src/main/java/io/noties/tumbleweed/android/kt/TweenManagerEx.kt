package io.noties.tumbleweed.android.kt

import io.noties.tumbleweed.BaseTweenDef
import io.noties.tumbleweed.TweenManager

/**
 * @since 2.1.0-SNAPSHOT
 */
fun <T : TweenManager> T.start(body: T.() -> BaseTweenDef) {
    body(this).start(this)
}