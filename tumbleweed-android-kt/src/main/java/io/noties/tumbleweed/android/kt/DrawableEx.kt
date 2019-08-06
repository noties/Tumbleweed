/**
 * @since 2.0.0
 */
package io.noties.tumbleweed.android.kt

import android.graphics.drawable.Drawable
import io.noties.tumbleweed.Tween
import io.noties.tumbleweed.TweenDef
import io.noties.tumbleweed.TweenType
import io.noties.tumbleweed.android.DrawableTweenManager

fun Drawable.tweenManager() = DrawableTweenManager.create(this)

fun Drawable.tweenManager(frame: Float) = DrawableTweenManager.create(this, frame)

/**
 * Create a [TweenDef] with caller Drawable as the target. Please note that resulting [TweenDef]
 * doesn\'t have duration specified and it must be set explicitly via [TweenDef.duration] method call.
 */
fun Drawable.tween(type: TweenType<Drawable>): TweenDef<Drawable> =
        Tween.to(this, type)

fun Drawable.tween(type: TweenType<Drawable>, duration: Float): TweenDef<Drawable> =
        Tween.to(this, type, duration)

fun Drawable.applyIntrinsicBounds() {
    setBounds(0, 0, intrinsicWidth, intrinsicHeight)
}

/**
 * Will set intrinsic bounds if only current bounds are empty
 * @since 2.1.0
 */
fun Drawable.applyIntrinsicBoundsIfEmpty() {
    if (bounds.isEmpty) {
        applyIntrinsicBounds()
    }
}