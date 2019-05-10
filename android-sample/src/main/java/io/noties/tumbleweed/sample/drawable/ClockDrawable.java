package io.noties.tumbleweed.sample.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import io.noties.tumbleweed.Tween;
import io.noties.tumbleweed.TweenManager;
import io.noties.tumbleweed.TweenType;
import io.noties.tumbleweed.android.DrawableTweenManager;
import io.noties.tumbleweed.equations.Linear;

public class ClockDrawable extends SquareDrawable implements Animatable {

    private final TweenManager tweenManager = DrawableTweenManager.create(this);

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final RectF rectF = new RectF();

    private final RectF handRectF = new RectF();

    private float rotationSmall = 300;

    private float rotationBig = 210;

    private final int borderColor;

    private final int bigColor;

    private final int smallColor;

    public ClockDrawable(int strokeWidth, @ColorInt int borderColor, @ColorInt int bigColor, @ColorInt int smallColor) {
        this.borderColor = borderColor;
        this.bigColor = bigColor;
        this.smallColor = smallColor;

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
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
        return tweenManager.isRunning();
    }

    @Override
    protected void onSideCalculated(int side) {
        rectF.set(0, 0, side, side);
        rectF.inset(paint.getStrokeWidth() / 2, paint.getStrokeWidth() / 2);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {

        if (rectF.isEmpty()) {
            return;
        }

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(borderColor);
        canvas.drawRoundRect(rectF, rectF.width() / 2, rectF.width() / 2, paint);

        final int centerX = (int) (rectF.centerX() + .5F);
        final int centerY = (int) (rectF.centerY() + .5F);

        final int stroke = (int) (paint.getStrokeWidth() / 2);

        canvas.save();
        try {
            final int bigWidth = (int) (centerX * .75F + .5F);
            handRectF.set(centerX - stroke, centerY - stroke, centerX + bigWidth, centerY + stroke);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(bigColor);
            canvas.rotate(rotationBig, rectF.centerX(), rectF.centerY());
            canvas.drawRoundRect(handRectF, handRectF.height() / 2, handRectF.height() / 2, paint);
        } finally {
            canvas.restore();
        }

        canvas.save();
        try {
            final int smallWidth = (int) (centerX * .45F + .5F);
            handRectF.set(centerX - stroke, centerY - stroke, centerX + smallWidth, centerY + stroke);
            paint.setColor(smallColor);
            canvas.rotate(rotationSmall, rectF.centerX(), rectF.centerY());
            canvas.drawRoundRect(handRectF, handRectF.height() / 2, handRectF.height() / 2, paint);
        } finally {
            canvas.restore();
        }
    }

    private void createTween() {

        final float duration = .75F;

        Tween.to(this, new RotationSmall(), duration)
                .target(rotationSmall + 360)
                .ease(Linear.INOUT)
                .repeat(-1, 0)
                .start(tweenManager);

        Tween.to(this, new RotationBig(), duration * 12)
                .target(rotationBig + 360)
                .repeat(-1, 0)
                .ease(Linear.INOUT)
                .start(tweenManager);
    }

    private static class RotationSmall implements TweenType<ClockDrawable> {

        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull ClockDrawable clockDrawable, @NonNull float[] values) {
            values[0] = clockDrawable.rotationSmall;
        }

        @Override
        public void setValues(@NonNull ClockDrawable clockDrawable, @NonNull float[] values) {
            clockDrawable.rotationSmall = values[0];
        }
    }

    private static class RotationBig implements TweenType<ClockDrawable> {

        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull ClockDrawable clockDrawable, @NonNull float[] values) {
            values[0] = clockDrawable.rotationBig;
        }

        @Override
        public void setValues(@NonNull ClockDrawable clockDrawable, @NonNull float[] values) {
            clockDrawable.rotationBig = values[0];
        }
    }
}
