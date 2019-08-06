# 2.1.0

* migrated to androidx

## tumbleweed-android-kt
* `View#tweenManager` changed to a property (was a method)
* Deprecated `View#tweenManager(Action)` and `View#tweenManagerKillAll`
* `Drawable#applyIntrinsicBoundsIfEmpty` new method (apply intrinsic bounds if current bounds are empty)
* `TweenManager#start` and `ViewTweenManager#startWhenReady` new extension methods
* `Timeline#with` extension method to create multiple tweens for a single target
* `Timeline#then` extension method to push a nested timeline
* `onBegin`, `onStart`, `onEnd`, `onComplete` extension methods for Tweens (simplified callbacks addition)

## tumbleweed-android
* `ViewTweenManager#view()` getter to obtain associated view

## tumbleweed
* Default duration for direct Tween children in a Timeline (create with 
`Timeline.createSequence(float)` and `Timeline.createParallel(float)`)
* Duration validation (cannot be negative/NaN)
* Deprecated `beginSequence`, `beginParallel` and `end` in Timeline (consider using 
Timeline creation explicitly via `Timeline.createSequence` and `Timeline.createParallel`, 
or Kotlin extension method `then`)
* Removed deprecated `ViewTweenManager#get(int,View)`
* Deprecate `ViewTweenManager#Action` class and all factory method associated with it
* Add `TweenCallbackAdapter` abstract class to simplify callbacks (`onBegin`, `onStart`, `onEnd`, `onComplete`)

```kotlin
val view = /*obtain view*/

view.tweenManager.start {
    // `this` for ViewTweenManager
    this.killAll()
    
    // last statement returns Tween
    /*return*/ view.tween(Alpha.VIEW, 0.25F).target(1.0F)
}

// start when associated view is ready (has dimensions)
view.tweenManager.startWhenReady {
    
    killAll()
    
    // default duration for pushed Tweens (only _direct child_ tweens are affected)
    Timeline.createParallel(0.25F)
        // extension to create tween for a target (view is this case)
        .with(view) {
            // create tweens 
            // do not specify duration (default supplied 0.25F will be used
            to(Alpha.VIEW).target(1.0F)
            
            // use specified duration (default 0.25F won't be used here)
            to(Scale.XY, 0.125F).target(0.5F, 0.5F)
        }
        // default duration will be used for this tween
        .push(Tween.to(view, Rotation.I).target(90.0F))
        // nested timelines won't have default duration set (must be done for each timeline individually)
        .push(Timeline.createSequence(0.45F).push(/**/))
}

view.tweenManager.startWhenReady {

    Timeline.createSequence()
            // `then` extension method to push configured timeline
            .then(Timeline.createSequence(0.5F)) {
                // `with` extension method to configure tweens for a single target
                with(view) {
                    // rotate 45 degrees
                    to(Rotation.I).target(45.0F).ease(Bounce.OUT)

                    // change background to green
                    to(Argb.BACKGROUND).target(*Color.GREEN.toArgbArray())
                }
                // still possible to push _regularly_, this one goes to the `Timeline.createSequence(0.5F)` timeline
                push(view.tween(Alpha.VIEW).target(0.45F))
            }
            .then(Timeline.createParallel(0.75F)) {
                with(view) {
                    to(Rotation.I).target(0.0F).ease(Bounce.OUT)
                    to(Argb.BACKGROUND).target(*Color.RED.toArgbArray())
                }
            }
            // onComplete is an extension method to add a TweenCallback for COMPLETE event
            // there are also `onBegin`, `onStart`, `onEnd`
            .onComplete {
                // done
            }
            // this one is for the root sequence `Timeline.createSequence()`
            .repeat(-1, 1.0F)

}
```

# 2.0.0
* package name change to `io.noties.tumbleweed.*`
* maven artifact group-id change to `io.noties`
* add `ViewTweenManager#get(View)` method (preferred one to obtain instance)
* add `AnimatorTweenManager`
* add `tumbleweed-android-kt` module with Kotlin utility extensions