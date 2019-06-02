/**
 * @since 2.0.0
 */
package io.noties.tumbleweed.android.kt

import android.graphics.drawable.Drawable
import io.noties.tumbleweed.Tween
import io.noties.tumbleweed.TweenDef
import io.noties.tumbleweed.TweenType
import io.noties.tumbleweed.android.DrawableTweenManager

public fun Drawable.tweenManager() = DrawableTweenManager.create(this)

public fun Drawable.tweenManager(frame: Float) = DrawableTweenManager.create(this, frame)

/**
 * Create a [TweenDef] with caller Drawable as the target. Please note that resulting [TweenDef]
 * doesn\'t have duration specified and it must be set explicitly via [TweenDef.duration] method call.
 */
public fun Drawable.tween(type: TweenType<Drawable>): TweenDef<Drawable> =
        Tween.to(this, type)

public fun Drawable.tween(type: TweenType<Drawable>, duration: Float): TweenDef<Drawable> =
        Tween.to(this, type, duration)

public fun Drawable.applyIntrinsicBounds() {
    setBounds(0, 0, intrinsicWidth, intrinsicHeight)
}