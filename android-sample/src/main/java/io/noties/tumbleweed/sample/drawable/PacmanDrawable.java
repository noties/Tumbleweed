package io.noties.tumbleweed.sample.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.support.annotation.NonNull;

import io.noties.tumbleweed.Timeline;
import io.noties.tumbleweed.TimelineDef;
import io.noties.tumbleweed.Tween;
import io.noties.tumbleweed.TweenManager;
import io.noties.tumbleweed.TweenType;
import io.noties.tumbleweed.android.DrawableTweenManager;
import io.noties.tumbleweed.android.types.Alpha;
import io.noties.tumbleweed.android.types.Argb;
import io.noties.tumbleweed.equations.Cubic;

public class PacmanDrawable extends SquareDrawable implements Animatable {

    private static final float START_ANGLE = 0;

    private static final float MAX_SWEEP = 270;
    private static final float MAX_ANGLE = 45;

    private static final float DURATION = .5F;

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final Paint dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final RectF rectF = new RectF();

    private final RectF dotRectF = new RectF();

    private final TweenManager tweenManager;

    private final int[] colors;

    private float sweep = 360;
    private float angle = START_ANGLE;

    @SuppressWarnings("WeakerAccess")
    public PacmanDrawable(@NonNull int[] colors) {
        this.tweenManager = DrawableTweenManager.create(this);
        this.colors = colors;

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(colors[0]);

        dotPaint.setStyle(Paint.Style.FILL);
        dotPaint.setColor(colors[1]);
        dotPaint.setAlpha(0);
    }

    @Override
    protected void onSideCalculated(int side) {

        rectF.set(0, 0, side, side);

        final float dotSide = side / 4;

        dotRectF.right = side - (dotSide / 4);
        dotRectF.left = dotRectF.right - dotSide;

        dotRectF.top = ((side - dotSide) / 2);
        dotRectF.bottom = dotRectF.top + dotSide;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {

        final float radius = dotRectF.width() / 2;
        canvas.drawRoundRect(dotRectF, radius, radius, dotPaint);

        canvas.drawArc(rectF, angle, sweep, true, paint);
    }

    @Override
    public void start() {

        if (!tweenManager.containsTarget(this)) {
            createTween();
        }

        if (!isRunning()) {
            tweenManager.resume();
        }
    }

    @Override
    public void stop() {
        tweenManager.pause();
    }

    @Override
    public boolean isRunning() {
        return tweenManager.isRunning();
    }

    private void createTween() {

        Tween.to(this, new PacmanType(), DURATION)
                .target(MAX_SWEEP, MAX_ANGLE)
                .ease(Cubic.INOUT)
                .repeatYoyo(-1, 0)
                .start(tweenManager);

        createBodyColorTween();

        createDotTween();
    }

    private void createBodyColorTween() {

        final TimelineDef timelineDef = Timeline.createSequence();

        float[] color;
        for (int i = 1; i < colors.length; i++) {
            color = Argb.toArray(colors[i]);
            timelineDef
                    .pushPause(DURATION)
                    .push(Tween.to(paint, Argb.PAINT, DURATION).target(color));
        }

        // loop
        timelineDef
                .pushPause(DURATION)
                .push(Tween.to(paint, Argb.PAINT, DURATION).target(Argb.toArray(colors[0])));

        timelineDef
                .delay(DURATION * 2)
                .repeat(-1, 0)
                .start(tweenManager);
    }

    private void createDotTween() {

        final TimelineDef timelineDef = Timeline.createSequence();

        for (int i = 1; i < colors.length; i++) {
            timelineDef
                    .push(Tween.set(dotPaint, Argb.PAINT).target(Argb.toArray(colors[i])))
                    .push(Tween.set(dotPaint, Alpha.PAINT).target(0.F))
                    .push(Tween.to(dotPaint, Alpha.PAINT, DURATION * 2).target(1.F));
        }

        timelineDef
                .push(Tween.set(dotPaint, Argb.PAINT).target(Argb.toArray(colors[0])))
                .push(Tween.set(dotPaint, Alpha.PAINT).target(0.F))
                .push(Tween.to(dotPaint, Alpha.PAINT, DURATION * 2).target(1.F));

        timelineDef
                .delay(DURATION * 2)
                .repeat(-1, 0)
                .start(tweenManager);
    }

    private static class PacmanType implements TweenType<PacmanDrawable> {

        @Override
        public int getValuesSize() {
            return 2;
        }

        @Override
        public void getValues(@NonNull PacmanDrawable pacmanDrawable, @NonNull float[] values) {
            values[0] = pacmanDrawable.sweep;
            values[1] = pacmanDrawable.angle;
        }

        @Override
        public void setValues(@NonNull PacmanDrawable pacmanDrawable, @NonNull float[] values) {
            pacmanDrawable.sweep = values[0];
            pacmanDrawable.angle = values[1];
        }
    }
}
