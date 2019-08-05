package io.noties.tumbleweed.sample.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;

import androidx.annotation.NonNull;
import androidx.annotation.Size;

import io.noties.tumbleweed.Timeline;
import io.noties.tumbleweed.Tween;
import io.noties.tumbleweed.TweenEquation;
import io.noties.tumbleweed.TweenManager;
import io.noties.tumbleweed.TweenType;
import io.noties.tumbleweed.android.DrawableTweenManager;
import io.noties.tumbleweed.equations.Quart;

public class NewtonDrawable extends SquareDrawable implements Animatable {

    private final TweenManager tweenManager = DrawableTweenManager.create(this);

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final RectF rectF = new RectF();

    private final int[] colors;

    private int side;

    private boolean waitingForSide;

    private int top;

    private float left;
    private float right;

    public NewtonDrawable(@Size(4) @NonNull int[] colors) {
        this.colors = colors;
    }

    @Override
    protected void onSideCalculated(int side) {
        this.side = side;
        if (waitingForSide) {
            waitingForSide = false;
            createTween();
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {

        if (waitingForSide) {
            return;
        }

        final float width = rectF.width();
        final float radius = width / 2;

        canvas.translate(0, top);

        final int save = canvas.save();
        try {

            paint.setColor(colors[0]);

            if (left > 0) {
                canvas.translate(left, 0);
            }
            canvas.drawRoundRect(rectF, radius, radius, paint);

        } finally {
            canvas.restoreToCount(save);
        }

        // 2 static balls
        paint.setColor(colors[1]);
        canvas.translate(width * 2, 0);
        canvas.drawRoundRect(rectF, radius, radius, paint);

        paint.setColor(colors[2]);
        canvas.translate(width, 0);
        canvas.drawRoundRect(rectF, radius, radius, paint);

        paint.setColor(colors[3]);
        canvas.translate(width, 0);

        if (right > 0) {
            canvas.translate(right, 0);
        }
        canvas.drawRoundRect(rectF, radius, radius, paint);
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

    private void createTween() {

        if (side == 0) {
            waitingForSide = true;
            return;
        }

        final int diameter = side / 6;
        rectF.set(0, 0, diameter, diameter);

        top = (side - diameter) / 2;

        final float duration = .35F;
        final TweenEquation equationIn = Quart.IN;
        final TweenEquation equationOut = Quart.OUT;

        Timeline.createParallel()
                .push(Tween.to(this, new Left(), duration).target(diameter).ease(equationIn))
                .push(Tween.to(this, new Right(), duration).target(diameter).ease(equationOut).delay(duration))
                .push(Tween.to(this, new Right(), duration).target(0).ease(equationIn).delay(duration * 2))
                .push(Tween.to(this, new Left(), duration).target(0).ease(equationOut).delay(duration * 3))
                .repeat(-1, 0)
                .start(tweenManager);
    }

    private static class Left implements TweenType<NewtonDrawable> {

        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull NewtonDrawable newtonDrawable, @NonNull float[] values) {
            values[0] = newtonDrawable.left;
        }

        @Override
        public void setValues(@NonNull NewtonDrawable newtonDrawable, @NonNull float[] values) {
            newtonDrawable.left = values[0];
        }
    }

    private static class Right implements TweenType<NewtonDrawable> {

        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull NewtonDrawable newtonDrawable, @NonNull float[] values) {
            values[0] = newtonDrawable.right;
        }

        @Override
        public void setValues(@NonNull NewtonDrawable newtonDrawable, @NonNull float[] values) {
            newtonDrawable.right = values[0];
        }
    }
}
