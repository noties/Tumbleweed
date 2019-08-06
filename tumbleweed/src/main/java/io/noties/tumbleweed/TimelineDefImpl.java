package io.noties.tumbleweed;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

class TimelineDefImpl extends TimelineDef {

    enum Mode {
        SEQUENCE,
        PARALLEL
    }

    private final List<BaseTweenDef> children = new ArrayList<>(4);

    private final Mode mode;
    private final BaseTweenDefImpl impl = new BaseTweenDefImpl();
    private final float defaultTweenDuration; // @since 2.1.0
    private final boolean hasDefaultTweenDuration; // @since 2.1.0

    TimelineDefImpl(@NonNull Mode mode) {
        this.mode = mode;
        this.current = this;
        this.defaultTweenDuration = 0;
        this.hasDefaultTweenDuration = false;
    }

    TimelineDefImpl(@NonNull Mode mode, float defaultTweenDuration) {
        this.mode = mode;
        this.current = this;
        this.defaultTweenDuration = processDuration(defaultTweenDuration);
        this.hasDefaultTweenDuration = Float.compare(defaultTweenDuration, 0.0F) > 0;
    }

    @NonNull
    @Override
    public TimelineDef push(@NonNull TweenDef<?> tween) {
        // @since 2.1.0 we check if duration should be set manually from a default
        // one that we have
        initDefaultDurationIfNeeded(tween);
        current.add(tween);
        return this;
    }

    @NonNull
    @Override
    public TimelineDef push(@NonNull TimelineDef timeline) {
        if (timeline.current != timeline) {
            throw new RuntimeException("You forgot to call a few 'end()' statements in your pushed timeline");
        }
        timeline.parent = current;
        current.add(timeline);
        return this;
    }

    @NonNull
    @Override
    public TimelineDef push(@NonNull BaseTweenDef baseTweenDef) {
        if (baseTweenDef instanceof TimelineDef) {
            push((TimelineDef) baseTweenDef);
        } else {
            // we could've casted to TweenDef, but as BaseTweenDef is open for subclassing
            // we just cannot _assume_ that received tween here is TweenDef (and not some other type)
            if (baseTweenDef instanceof TweenDef) {
                push((TweenDef) baseTweenDef);
            } else {
                current.add(baseTweenDef);
            }
        }
        return this;
    }

    @NonNull
    @Override
    public TimelineDef pushPause(float duration) {
        current.add(Tween.mark().delay(duration));
        return this;
    }

    @NonNull
    @Override
    public TimelineDef beginSequence() {
        final TimelineDef timelineDef = new TimelineDefImpl(Mode.SEQUENCE);
        timelineDef.parent = current;
        current.add(timelineDef);
        current = timelineDef;
        return this;
    }

    @NonNull
    @Override
    public TimelineDef beginParallel() {
        final TimelineDef timelineDef = new TimelineDefImpl(Mode.PARALLEL);
        timelineDef.parent = current;
        current.add(timelineDef);
        current = timelineDef;
        return this;
    }

    @NonNull
    @Override
    public TimelineDef end() {
        if (current == this) {
            throw new RuntimeException("Nothing to end...");
        }
        current = current.parent;
        return this;
    }

    @NonNull
    @Override
    public Timeline build() {

        final boolean isSequence = Mode.SEQUENCE == mode;

        float duration = 0.F;

        final int size = children.size();

        final List<BaseTween> tweens = new ArrayList<>(size);

        BaseTweenDef baseTweenDef;

        for (int i = 0; i < size; i++) {

            baseTweenDef = children.get(i);

            if (baseTweenDef.repeatCount() < 0) {
                throw new RuntimeException("You can't push an object with infinite repetitions in a timeline");
            }

            if (isSequence) {
                final float delay = duration;
                duration += baseTweenDef.fullDuration();
                baseTweenDef.delay(baseTweenDef.delay() + delay);
            } else {
                duration = Math.max(duration, baseTweenDef.fullDuration());
            }

            tweens.add(baseTweenDef.build());
        }

        impl.duration = duration;

        return new Timeline(impl, duration, tweens);
    }

    @NonNull
    @Override
    public Timeline start() {
        final Timeline timeline = build();
        timeline.start();
        return timeline;
    }

    @NonNull
    @Override
    public Timeline start(@NonNull TweenManager tweenManager) {
        final Timeline timeline = build();
        timeline.start(tweenManager);
        return timeline;
    }

    @NonNull
    @Override
    public TimelineDef delay(float duration) {
        impl.delay(duration);
        return this;
    }

    @NonNull
    @Override
    public TimelineDef repeat(int count, float delay) {
        impl.repeat(count, delay);
        return this;
    }

    @NonNull
    @Override
    public TimelineDef repeatYoyo(int count, float delay) {
        impl.repeatYoyo(count, delay);
        return this;
    }

    @NonNull
    @Override
    public TimelineDef addCallback(@NonNull TweenCallback callback) {
        impl.addCallback(callback);
        return this;
    }

    @NonNull
    @Override
    public TimelineDef addCallback(@TweenCallback.Event int callbackEvents, @NonNull TweenCallback callback) {
        impl.addCallback(callbackEvents, callback);
        return this;
    }

    @NonNull
    @Override
    public TimelineDef userData(Object userData) {
        impl.userData(userData);
        return this;
    }

    @NonNull
    @Override
    public TimelineDef removeWhenFinished(boolean removeWhenFinished) {
        impl.removeWhenFinished(removeWhenFinished);
        return this;
    }

    @Override
    public int repeatCount() {
        return impl.repeatCount();
    }

    @Override
    public float delay() {
        return impl.delay();
    }

    // We must override this in order to correctly nest timelines
    @Override
    public float fullDuration() {

        final boolean isSequence = Mode.SEQUENCE == mode;

        float duration = .0F;

        BaseTweenDef baseTweenDef;

        for (int i = 0, size = children.size(); i < size; i++) {

            baseTweenDef = children.get(i);

            if (baseTweenDef.repeatCount() < 0) {
                throw new RuntimeException("You can't push an object with infinite repetitions in a timeline");
            }

            if (isSequence) {
                duration += baseTweenDef.fullDuration();
            } else {
                duration = Math.max(duration, baseTweenDef.fullDuration());
            }
        }

        return duration;
    }

    @Override
    protected void add(@NonNull BaseTweenDef tweenDef) {
        children.add(tweenDef);
    }

    private void initDefaultDurationIfNeeded(@NonNull TweenDef<?> tweenDef) {
        // we must have default duration set
        // tweenDef must be actionable
        // tweenDef must not have a duration set
        if (tweenDef.isActionable()
                && hasDefaultTweenDuration
                && Float.compare(tweenDef.duration(), 0.0F) == 0) {
            tweenDef.duration(defaultTweenDuration);
        }
    }
}
