package io.noties.tumbleweed.android.kt

import android.view.View
import io.noties.tumbleweed.*
import io.noties.tumbleweed.android.ViewTweenManager
import io.noties.tumbleweed.android.types.Alpha
import io.noties.tumbleweed.android.types.Scale

class TimelineDefEx<T>(private val timeline: TimelineDef, private val target: T) {

    fun to(type: TweenType<T>): TweenDef<T> {
        return Tween.to(target, type).also {
            timeline.push(it)
        }
    }

    fun to(type: TweenType<T>, duration: Float): TweenDef<T> {
        return Tween.to(target, type, duration).also {
            timeline.push(it)
        }
    }

    fun from(type: TweenType<T>): TweenDef<T> {
        return Tween.from(target, type).also {
            timeline.push(it)
        }
    }

    fun from(type: TweenType<T>, duration: Float): TweenDef<T> {
        return Tween.to(target, type, duration).also {
            timeline.push(it)
        }
    }

    fun set(type: TweenType<T>): TweenDef<T> {
        return Tween.set(target, type).also {
            timeline.push(it)
        }
    }
}

fun <T> TimelineDef.push(target: T, body: TimelineDefEx<T>.() -> Unit): TimelineDef {
    body(TimelineDefEx(this, target))
    return this
}

fun main() {

    val view = null as View

    view.tweenManager.start {

        killAll()

        Timeline.createParallel(0.25F)
                .push(view) {
                    to(Alpha.VIEW).target(0.0F)
                    to(Scale.XY).target(0.0F, 0.0F)
                }
    }
}