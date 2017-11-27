package ru.noties.tumbleweed.sample.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.drawable.Animatable;
import android.support.annotation.NonNull;
import android.support.annotation.Size;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.noties.tumbleweed.Timeline;
import ru.noties.tumbleweed.Tween;
import ru.noties.tumbleweed.TweenDef;
import ru.noties.tumbleweed.TweenManager;
import ru.noties.tumbleweed.android.DrawableTweenManager;
import ru.noties.tumbleweed.android.types.Argb;
import ru.noties.tumbleweed.android.types.Graphics;
import ru.noties.tumbleweed.equations.Cubic;

public class SquareSpinDrawable extends SquareDrawable implements Animatable {

    private final TweenManager tweenManager = DrawableTweenManager.create(this);

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final Path path = new Path();

    private final List<PointF> points = new ArrayList<>(4);

    private final int[] colors;

    private int side;

    private boolean waitingForSide;

    public SquareSpinDrawable(@Size(4) @NonNull int[] colors) {
        this.colors = colors;
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(colors[0]);
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
            waitingForSide = false;
            createTween();
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {

        if (path.isEmpty()) {
            return;
        }

        canvas.drawPath(path, paint);
    }

    private void createTween() {

        if (side == 0) {
            waitingForSide = true;
            return;
        }

        final float tilt = side / 8;
        final float half = side / 2;

        final PointF point0 = new PointF(tilt, tilt);
        final PointF point1 = new PointF(side - tilt, tilt);
        final PointF point2 = new PointF(tilt, side - tilt);
        final PointF point3 = new PointF(side - tilt, side - tilt);

        final List<PointF> start = Arrays.asList(point0, point1, point2, point3);
        points.addAll(start);

        // create points here
        final List<PointF> waypoint0 = Arrays.asList(
                new PointF(0, half),
                new PointF(side, half),
                new PointF(tilt * 2, half),
                new PointF(side - (tilt * 2), half)
        );
        final List<PointF> target0 = Arrays.asList(point2, point3, point0, point1);

        final List<PointF> waypoint1 = Arrays.asList(
                point(half, side),
                point(half, side - (tilt * 2)),
                point(half, 0),
                point(half, tilt * 2)
        );
        final List<PointF> target1 = Arrays.asList(point3, point2, point1, point0);

        final List<PointF> waypoint2 = Arrays.asList(
                point(side, half),
                point(0, half),
                point(side - (tilt * 2), half),
                point(tilt * 2, half)
        );
        final List<PointF> target2 = Arrays.asList(point1, point0, point3, point2);

        final List<PointF> waypoint3 = Arrays.asList(
                point(half, 0),
                point(half, tilt * 2),
                point(half, side),
                point(half, side - (tilt * 2))
        );
        final List<PointF> target3 = Arrays.asList(point0, point1, point2, point3);

        final float duration = .75F;

        Timeline.createSequence()
                .push(Timeline.createParallel()
                        .push(tween(waypoint0, target0, duration))
                        .push(Tween.set(paint, Argb.PAINT).target(Argb.toArray(colors[1])).delay(duration / 2)))
                .push(Timeline.createParallel()
                        .push(tween(waypoint1, target1, duration))
                        .push(Tween.set(paint, Argb.PAINT).target(Argb.toArray(colors[2])).delay(duration / 2)))
                .push(Timeline.createParallel()
                        .push(tween(waypoint2, target2, duration))
                        .push(Tween.set(paint, Argb.PAINT).target(Argb.toArray(colors[3])).delay(duration / 2)))
                .push(Timeline.createParallel()
                        .push(tween(waypoint3, target3, duration))
                        .push(Tween.set(paint, Argb.PAINT).target(Argb.toArray(colors[0])).delay(duration / 2)))
                .repeat(-1, 0)
                .start(tweenManager);
    }

    private TweenDef<List<PointF>> tween(@NonNull List<PointF> waypoint, @NonNull List<PointF> target, float duration) {
        return Tween.to(points, Graphics.points(points), duration)
                .waypoint(waypoint)
                .target(target)
                .action($ -> redraw())
                .ease(Cubic.INOUT);
    }

    private void redraw() {

        path.reset();

        if (points.size() == 0) {
            return;
        }

        PointF pointF = points.get(0);
        path.moveTo(pointF.x, pointF.y);

        pointF = points.get(2);
        path.lineTo(pointF.x, pointF.y);

        pointF = points.get(3);
        path.lineTo(pointF.x, pointF.y);

        pointF = points.get(1);
        path.lineTo(pointF.x, pointF.y);

        path.close();

        invalidateSelf();
    }

    private static PointF point(float x, float y) {
        return new PointF(x, y);
    }
}
