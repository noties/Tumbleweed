package ru.noties.tumbleweed.sample.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.support.annotation.NonNull;

import ru.noties.tumbleweed.Timeline;
import ru.noties.tumbleweed.TimelineDef;
import ru.noties.tumbleweed.Tween;
import ru.noties.tumbleweed.TweenManager;
import ru.noties.tumbleweed.TweenType;
import ru.noties.tumbleweed.android.DrawableTweenManager;
import ru.noties.tumbleweed.android.types.Argb;
import ru.noties.tumbleweed.equations.Cubic;

public class BallDrawable extends SquareDrawable implements Animatable {

    private static final float DURATION = .6F;

    private final TweenManager tweenManager = DrawableTweenManager.create(this);

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final RectF rectF = new RectF();

    private final int[] colors;

    private float y;

    private boolean startOnSideObtained;

    public BallDrawable(@NonNull int[] colors) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(colors[0]);
        this.colors = colors;
    }

    @Override
    public void start() {

        if (tweenManager.tweenCount() == 0) {
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
        return tweenManager.isStarted() && !tweenManager.isPaused();
    }

    @Override
    protected void onSideCalculated(int side) {
        rectF.set(0, 0, side, side);

        if (startOnSideObtained) {
            createTween();
            startOnSideObtained = false;
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        final float radius = rectF.width() / 2;
        canvas.drawRoundRect(rectF, radius, radius, paint);
    }


    private void createTween() {

        if (rectF.isEmpty()) {
            startOnSideObtained = true;
            return;
        }

        createBounceTween();

        createColorTween();
    }

    private void createBounceTween() {

        final Rect bounds = getBounds();

        final float centerX = rectF.width() / 2;
        final int h = bounds.height();
        final float b = bounds.bottom - ((bounds.height() - rectF.height()) / 2);

        final float side = rectF.height();

        final float distanceY = ((h - side) / 2) + (h / 7);

        Tween.to(this, new Y(), DURATION)
                .target(distanceY)
                .action($ -> {
                    final float height = y + side;
                    rectF.top = y;
                    rectF.bottom = Math.min(height, b);
                    if (height > rectF.bottom) {
                        // spread
                        final float halfSide = side / 2;
                        final float halfSpread = (height - rectF.bottom) / 2;
                        rectF.left = centerX - halfSide - halfSpread;
                        rectF.right = centerX + halfSide + halfSpread;
                    }
                })
                .repeatYoyo(-1, 0)
                .ease(Cubic.INOUT)
                .start(tweenManager);
    }

    private void createColorTween() {

        final TimelineDef timelineDef = Timeline.createSequence();

        for (int i = 1; i < colors.length; i++) {
            timelineDef
                    .pushPause(DURATION)
                    .push(Tween.to(paint, Argb.PAINT, DURATION).target(Argb.toArray(colors[i])).ease(Cubic.INOUT));
        }

        timelineDef
                .pushPause(DURATION)
                .push(Tween.to(paint, Argb.PAINT, DURATION).target(Argb.toArray(colors[0])).ease(Cubic.INOUT));

        timelineDef
                .delay(DURATION)
                .repeat(-1, 0)
                .start(tweenManager);
    }

    private static class Y implements TweenType<BallDrawable> {

        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull BallDrawable ballDrawable, @NonNull float[] values) {
            values[0] = ballDrawable.y;
        }

        @Override
        public void setValues(@NonNull BallDrawable ballDrawable, @NonNull float[] values) {
            ballDrawable.y = values[0];
        }
    }
}
