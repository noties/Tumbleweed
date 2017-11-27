package ru.noties.tumbleweed.sample.progress;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.support.annotation.NonNull;
import android.support.annotation.Size;

import ru.noties.tumbleweed.Timeline;
import ru.noties.tumbleweed.Tween;
import ru.noties.tumbleweed.TweenManager;
import ru.noties.tumbleweed.TweenType;
import ru.noties.tumbleweed.android.DrawableTweenManager;
import ru.noties.tumbleweed.equations.Cubic;

public class MicDrawable extends SquareDrawable implements Animatable {

    private final TweenManager tweenManager = DrawableTweenManager.create(this);

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final Rect rect = new Rect();

    private final int[] colors;

    private int side;

    private int line0;
    private int line1;
    private int line2;

    private boolean waitingForSide;

    public MicDrawable(@Size(3) @NonNull int[] colors) {
        this.colors = colors;
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xFFff0000);
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
        this.side = side;

        if (waitingForSide) {
            createTween();
            waitingForSide = false;
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {

        if (side == 0) {
            return;
        }

        final int avaialable = side / 3;
        final int width = side / 4;

        final int left = (avaialable - width) / 2;

        // line0
        {
            final int top = (side - line0) / 2;
            rect.set(left, top, left + width, top + line0);
            paint.setColor(colors[0]);
            canvas.drawRect(rect, paint);
        }

        {
            final int top = (side - line1) / 2;
            rect.set(avaialable + left, top, avaialable + left + width, top + line1);
            paint.setColor(colors[1]);
            canvas.drawRect(rect, paint);
        }

        {
            final int top = (side - line2) / 2;
            rect.set((avaialable * 2) + left, top, (avaialable * 2) + left + width, top + line2);
            paint.setColor(colors[2]);
            canvas.drawRect(rect, paint);
        }
    }

    private void createTween() {

        if (side == 0) {
            waitingForSide = true;
            return;
        }

        line0 = line1 = line2 = side / 2;

        final float duration = .25F;

        Timeline.createParallel()
                .push(Tween.to(this, new Line0(), duration * 4).waypoint(side).target(line0).ease(Cubic.INOUT))
                .push(Tween.to(this, new Line1(), duration * 4).waypoint(side).target(line1).delay(duration).ease(Cubic.INOUT))
                .push(Tween.to(this, new Line2(), duration * 4).waypoint(side).target(line2).delay(duration * 2).ease(Cubic.INOUT))
                .repeat(-1, 0)
                .start(tweenManager);

//        Tween.to(this, new Line0(), duration * 2)
//                .waypoint(side)
//                .target(line0)
//                .ease(Cubic.INOUT)
//                .repeat(-1, 2)
//                .start(tweenManager);
//
//        Tween.to(this, new Line1(), duration * 2)
//                .waypoint(side)
//                .target(line1)
//                .delay(duration)
//                .repeat(-1, 3)
//                .start(tweenManager);
//
//        Tween.to(this, new Line2(), duration * 2)
//                .waypoint(side)
//                .target(line2)
//                .delay(duration * 2)
//                .repeat(-1, 4)
//                .start(tweenManager);
    }

    private static class Line0 implements TweenType<MicDrawable> {

        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull MicDrawable micDrawable, @NonNull float[] values) {
            values[0] = micDrawable.line0;
        }

        @Override
        public void setValues(@NonNull MicDrawable micDrawable, @NonNull float[] values) {
            micDrawable.line0 = (int) (values[0] + .5F);
        }
    }

    private static class Line1 implements TweenType<MicDrawable> {

        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull MicDrawable micDrawable, @NonNull float[] values) {
            values[0] = micDrawable.line1;
        }

        @Override
        public void setValues(@NonNull MicDrawable micDrawable, @NonNull float[] values) {
            micDrawable.line1 = (int) (values[0] + .5F);
        }
    }

    private static class Line2 implements TweenType<MicDrawable> {

        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull MicDrawable micDrawable, @NonNull float[] values) {
            values[0] = micDrawable.line2;
        }

        @Override
        public void setValues(@NonNull MicDrawable micDrawable, @NonNull float[] values) {
            micDrawable.line2 = (int) (values[0] + .5F);
        }
    }
}
