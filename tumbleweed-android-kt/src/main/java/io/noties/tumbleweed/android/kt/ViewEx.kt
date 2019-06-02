/**
 * @since 2.0.0
 */
package io.noties.tumbleweed.android.kt

import android.graphics.Point
import android.view.View
import android.view.ViewTreeObserver
import io.noties.tumbleweed.Tween
import io.noties.tumbleweed.TweenDef
import io.noties.tumbleweed.TweenType
import io.noties.tumbleweed.android.ViewTweenManager
import io.noties.tumbleweed.android.utils.ViewUtils

public fun View.tweenManager() = ViewTweenManager.get(this)

public fun View.tweenManager(init: ViewTweenManager.Action) = ViewTweenManager.get(this, init)

public fun View.tweenManagerKillAll() = ViewTweenManager.getKillAll(this)

/**
 * Create a [TweenDef] with caller View as the target. Please note that returned [TweenDef] doesn\'t
 * have specified duration and it must be set explicitly via [TweenDef.duration] method call.
 */
public fun View.tween(type: TweenType<View>): TweenDef<View> =
        Tween.to(this, type)

public fun View.tween(type: TweenType<View>, duration: Float): TweenDef<View> =
        Tween.to(this, type, duration)

public inline fun View.whenReady(crossinline action: () -> Unit) {
    // we assume that if we have width (aka laid-out) when we are _ready_
    if (width > 0) {
        action()
    } else {
        viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                viewTreeObserver.removeOnPreDrawListener(this)
                action()
                return true
            }
        })
    }
}

public fun View.relativeTo(parent: View, point: Point = Point()) = ViewUtils.relativeTo(parent, this, point)