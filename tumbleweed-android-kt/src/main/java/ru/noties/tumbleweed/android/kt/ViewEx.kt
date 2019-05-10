/**
 * @since 2.0.0-SNAPSHOT
 */
package ru.noties.tumbleweed.android.kt

import android.graphics.Point
import android.view.View
import android.view.ViewTreeObserver
import ru.noties.tumbleweed.android.ViewTweenManager
import ru.noties.tumbleweed.android.utils.ViewUtils

public fun View.tweenManager() = ViewTweenManager.get(this)

public fun View.tweenManager(init: ViewTweenManager.Action) = ViewTweenManager.get(this, init)

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