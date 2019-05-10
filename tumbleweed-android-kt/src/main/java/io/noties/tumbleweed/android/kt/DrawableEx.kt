package io.noties.tumbleweed.android.kt

import android.graphics.drawable.Drawable
import io.noties.tumbleweed.android.DrawableTweenManager

public fun Drawable.tweenManager() = DrawableTweenManager.create(this)

public fun Drawable.tweenManager(frame: Float) = DrawableTweenManager.create(this, frame)

public fun Drawable.applyIntrinsicBounds() {
    setBounds(0, 0, intrinsicWidth, intrinsicHeight)
}