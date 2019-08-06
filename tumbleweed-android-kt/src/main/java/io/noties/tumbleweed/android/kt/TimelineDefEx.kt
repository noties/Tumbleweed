package io.noties.tumbleweed.android.kt

import io.noties.tumbleweed.TimelineDef
import io.noties.tumbleweed.Tween
import io.noties.tumbleweed.TweenDef
import io.noties.tumbleweed.TweenType

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

/**
 * Configure Timeline to push multiple tweens for a single target
 * @since 2.1.0
 */
fun <T> TimelineDef.with(target: T, body: TimelineDefEx<T>.() -> Unit): TimelineDef {
    body(TimelineDefEx(this, target))
    return this
}

/**
 * Push a nested Timeline inside another Timeline
 * @since 2.1.0
 */
fun TimelineDef.then(timeline: TimelineDef, body: TimelineDef.() -> Unit): TimelineDef {
    body(timeline).also { push(timeline) }
    return this
}