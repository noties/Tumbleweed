package io.noties.tumbleweed.sample

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import io.noties.tumbleweed.Timeline
import io.noties.tumbleweed.android.kt.*
import io.noties.tumbleweed.android.types.*
import io.noties.tumbleweed.equations.Bounce

class MainActivityK : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = findViewById<View>(Window.ID_ANDROID_CONTENT)
        view.setBackgroundColor(Color.RED)

        // `view.tweenManager` is an extension property for a view
        // `startWhenReady` is an extension method to start configured tween
        //      when associated view has dimensions
        view.tweenManager.startWhenReady {

            // `this` is ViewTweenManager
            this.killAll()

            /*return */Timeline.createSequence()
                // `then` extension method to push configured timeline
                // `0.5F` is the default duration for tweens without duration set
                .then(Timeline.createSequence(0.5F)) {
                    // `with` extension method to configure tweens for a single target
                    with(view) {

                        // move half-width/half-width left/top
                        to(Translation.XY).target(-view.width / 2.0F, -view.height / 2.0F)

                        // return to original Y position
                        to(Translation.Y).target(0.0F)

                        // return to original X position
                        to(Translation.X).target(0.0F)

                        // set pivot to the 0,0 (no animation)
                        set(Pivot.XY).target(0.0F, 0.0F)

                        // rotate 45 degrees
                        to(Rotation.I).target(45.0F).ease(Bounce.OUT)

                        // change background to green
                        to(Argb.BACKGROUND).target(*Color.GREEN.toArgbArray())
                    }
                    // still possible to push _regularly_
                    push(view.tween(Alpha.VIEW).target(0.45F))
                }
                .then(Timeline.createParallel(0.75F)) {
                    with(view) {
                        to(Rotation.I).target(0.0F).ease(Bounce.OUT)
                        to(Argb.BACKGROUND).target(*Color.RED.toArgbArray())
                    }
                    push(view.tween(Alpha.VIEW).target(1.0F))
                }
                // onComplete is an extension method to add a TweenCallback for COMPLETE event
                // there are also `onBegin`, `onStart`, `onEnd`
                .onComplete {
                    // done
                }
                .repeat(-1, 1.0F)
        }
    }
}