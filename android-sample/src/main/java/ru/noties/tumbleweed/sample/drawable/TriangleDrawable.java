package ru.noties.tumbleweed.sample.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.support.annotation.NonNull;
import android.support.annotation.Size;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.noties.tumbleweed.Timeline;
import ru.noties.tumbleweed.Tween;
import ru.noties.tumbleweed.TweenEquation;
import ru.noties.tumbleweed.TweenManager;
import ru.noties.tumbleweed.android.DrawableTweenManager;
import ru.noties.tumbleweed.android.types.Graphics;
import ru.noties.tumbleweed.equations.Quint;

public class TriangleDrawable extends SquareDrawable implements Animatable {

    private final TweenManager tweenManager = DrawableTweenManager.create(this);

    private final List<PointF> points = new ArrayList<>(3);

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final RectF rectF = new RectF();

    private final int[] colors;

    private int side;

    private boolean waitingForSide;

    public TriangleDrawable(@Size(3) @NonNull int[] colors) {
        this.colors = colors;
        paint.setStyle(Paint.Style.FILL);
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
        this.rectF.set(0, 0, side / 4, side / 4);

        if (waitingForSide) {
            createTween();
            waitingForSide = false;
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {

        if (points.size() == 0) {
            return;
        }

        for (int i = 0; i < 3; i++) {
            final PointF pointF = points.get(i);
            final int save = canvas.save();
            try {
                paint.setColor(colors[i]);
                final float half = rectF.width() / 2;
                canvas.translate(pointF.x, pointF.y);
                canvas.drawRoundRect(rectF, half, half, paint);
            } finally {
                canvas.restoreToCount(save);
            }
        }
    }

    private void createTween() {

        if (side == 0) {
            waitingForSide = true;
            return;
        }

        final float full = rectF.width();

        final PointF point0 = new PointF(side * .5F - (full / 2), 0);
        final PointF point1 = new PointF(side - full, side - full);
        final PointF point2 = new PointF(0, side - full);

        points.add(point0);
        points.add(point1);
        points.add(point2);

        final List<PointF> waypoint0 = Arrays.asList(point1, point2, point0);
        final List<PointF> waypoint1 = Arrays.asList(point2, point0, point1);

        final TweenEquation equation = Quint.INOUT;

        Timeline.createSequence()
                .push(Tween.to(points, Graphics.points(points), .5F).target(waypoint0).ease(equation))
                .push(Tween.to(points, Graphics.points(points), .5F).target(waypoint1).ease(equation))
                .push(Tween.to(points, Graphics.points(points), .5F).target(points).ease(equation))
                .repeat(-1, 0)
                .start(tweenManager);
    }
}
