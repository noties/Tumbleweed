package io.noties.tumbleweed.sample.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Px;

import io.noties.tumbleweed.Timeline;
import io.noties.tumbleweed.TimelineDef;
import io.noties.tumbleweed.Tween;
import io.noties.tumbleweed.TweenEquation;
import io.noties.tumbleweed.TweenManager;
import io.noties.tumbleweed.TweenType;
import io.noties.tumbleweed.android.DrawableTweenManager;
import io.noties.tumbleweed.android.types.Argb;
import io.noties.tumbleweed.equations.Cubic;
import io.noties.tumbleweed.equations.Linear;

public class MaterialDrawable extends SquareDrawable implements Animatable {


    private static final float MIN_SWEEP = 20.F;

    private static final float MAX_SWEEP = 300.F;

    private static final float DURATION = 1.F;


    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final RectF rectF = new RectF();

    private final int[] colors;


    private final TweenManager tweenManager;

    private float angle = 60.F;

    private float rotation = .0F;

    private float sweepAngle = MIN_SWEEP;


    @SuppressWarnings({"unused", "WeakerAccess"})
    public MaterialDrawable(@ColorInt int[] colors, @Px int strokeWidth) {
        this.tweenManager = DrawableTweenManager.create(this);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(colors[0]);
        this.colors = colors;
    }

    @Override
    public void start() {

        if (!tweenManager.containsTarget(this)) {
            createTimeline();
        }

        if (!isRunning()) {
            tweenManager.resume();
        }
    }

    @Override
    public void stop() {

        if (isRunning()) {
            tweenManager.pause();
        }
    }

    @Override
    public boolean isRunning() {
        return tweenManager.isRunning();
    }

    @Override
    protected void onSideCalculated(int side) {

        rectF.set(0, 0, side, side);

        final int halfStroke = (int) (paint.getStrokeWidth() / 2 + .5F);

        rectF.inset(halfStroke, halfStroke);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        canvas.rotate(rotation, rectF.centerX(), rectF.centerY());
        canvas.drawArc(rectF, angle, sweepAngle, false, paint);
    }

    private void createTimeline() {

        createMainTween();

        createColorTween();

        createRotationTween();
    }

    private void createMainTween() {

        final TimelineDef timelineDef = Timeline.createSequence();

        float targetAngle = angle;

        final TweenEquation equation = Cubic.INOUT;

        while (true) {
            targetAngle = targetAngle + MAX_SWEEP - MIN_SWEEP;
            timelineDef
                    .push(Tween.to(this, new SweepAngle(), DURATION).target(MAX_SWEEP).ease(equation))
                    .push(Timeline.createParallel()
                            .push(Tween.to(this, new SweepAngle(), DURATION).target(MIN_SWEEP).ease(equation))
                            .push(Tween.to(this, new Angle(), DURATION).target(targetAngle).ease(equation)));

            if ((targetAngle % 360) == angle) {
                break;
            }
        }

        timelineDef
                .repeat(-1, 0)
                .start(tweenManager);
    }

    private void createColorTween() {

        final TimelineDef timelineDef = Timeline.createSequence();

        float[] color;
        for (int i = 1; i < colors.length; i++) {
            color = Argb.toArray(colors[i]);
            timelineDef
                    .push(Tween.to(paint, Argb.PAINT, DURATION).target(color))
                    .pushPause(DURATION);
        }

        // loop the color
        timelineDef
                .push(Tween.to(paint, Argb.PAINT, DURATION).target(Argb.toArray(colors[0])))
                .pushPause(DURATION);

        timelineDef
                .delay(2)
                .repeat(-1, 0)
                .start(tweenManager);
    }

    private void createRotationTween() {
        Tween.to(this, new Rotation(), 2.F)
                .target(359)
                .ease(Linear.INOUT)
                .repeat(-1, 0)
                .start(tweenManager);
    }

    private static class SweepAngle implements TweenType<MaterialDrawable> {

        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull MaterialDrawable materialDrawable, @NonNull float[] values) {
            values[0] = materialDrawable.sweepAngle;
        }

        @Override
        public void setValues(@NonNull MaterialDrawable materialDrawable, @NonNull float[] values) {
            materialDrawable.sweepAngle = values[0];
        }
    }

    private static class Angle implements TweenType<MaterialDrawable> {

        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull MaterialDrawable materialDrawable, @NonNull float[] values) {
            values[0] = materialDrawable.angle;
        }

        @Override
        public void setValues(@NonNull MaterialDrawable materialDrawable, @NonNull float[] values) {
            materialDrawable.angle = values[0];
        }
    }

    private static class Rotation implements TweenType<MaterialDrawable> {

        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull MaterialDrawable materialDrawable, @NonNull float[] values) {
            values[0] = materialDrawable.rotation;
        }

        @Override
        public void setValues(@NonNull MaterialDrawable materialDrawable, @NonNull float[] values) {
            materialDrawable.rotation = values[0];
        }
    }
}
