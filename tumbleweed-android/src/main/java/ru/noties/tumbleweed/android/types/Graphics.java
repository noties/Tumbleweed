package ru.noties.tumbleweed.android.types;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Size;

import java.util.List;

import ru.noties.tumbleweed.TweenType;

@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class Graphics<T> implements TweenType<T> {

    @NonNull
    public static Graphics<List<PointF>> points(@NonNull List<PointF> points) {
        return new Points(points.size() * 2);
    }

    @NonNull
    public static float[] toArray(@NonNull List<PointF> points) {
        return toArray(points, new float[points.size() * 2]);
    }

    @NonNull
    public static float[] toArray(@NonNull List<PointF> points, @NonNull float[] values) {
        PointF pointF;
        for (int i = 0, length = (points.size() * 2) - 1; i < length; i += 2) {
            pointF = points.get(i / 2);
            values[i] = pointF.x;
            values[i + 1] = pointF.y;
        }
        return values;
    }

    @NonNull
    public static float[] toArray(@NonNull Point point) {
        return toArray(point, new float[2]);
    }

    @NonNull
    public static float[] toArray(@NonNull Point point, @Size(2) @NonNull float[] values) {
        values[0] = point.x;
        values[1] = point.y;
        return values;
    }

    @NonNull
    public static float[] toArray(@NonNull PointF pointF) {
        return toArray(pointF, new float[2]);
    }

    @NonNull
    public static float[] toArray(@NonNull PointF pointF, @Size(2) @NonNull float[] values) {
        values[0] = pointF.x;
        values[1] = pointF.y;
        return values;
    }

    @NonNull
    public static float[] toArray(@NonNull Rect rect) {
        return toArray(rect, new float[4]);
    }

    @NonNull
    public static float[] toArray(@NonNull Rect rect, @Size(4) @NonNull float[] values) {
        values[0] = rect.left;
        values[1] = rect.top;
        values[2] = rect.right;
        values[3] = rect.bottom;
        return values;
    }

    @NonNull
    public static float[] toArray(@NonNull RectF rectF) {
        return toArray(rectF, new float[4]);
    }

    @NonNull
    public static float[] toArray(@NonNull RectF rectF, @Size(4) @NonNull float[] values) {
        values[0] = rectF.left;
        values[1] = rectF.top;
        values[2] = rectF.right;
        values[3] = rectF.bottom;
        return values;
    }

    @NonNull
    public static final Graphics<Rect> RECT = new Graphics<Rect>() {
        @Override
        public int getValuesSize() {
            return 4;
        }

        @Override
        public void getValues(@NonNull Rect rect, @NonNull float[] values) {
            toArray(rect, values);
        }

        @Override
        public void setValues(@NonNull Rect rect, @NonNull float[] values) {
            rect.set(
                    (int) (values[0] + .5F),
                    (int) (values[1] + .5F),
                    (int) (values[2] + .5F),
                    (int) (values[3] + .5F)
            );
        }

        @Override
        public String toString() {
            return "Graphics.RECT";
        }
    };

    @NonNull
    public static Graphics<RectF> RECT_F = new Graphics<RectF>() {
        @Override
        public int getValuesSize() {
            return 4;
        }

        @Override
        public void getValues(@NonNull RectF rectF, @NonNull float[] values) {
            toArray(rectF, values);
        }

        @Override
        public void setValues(@NonNull RectF rectF, @NonNull float[] values) {
            rectF.set(
                    values[0],
                    values[1],
                    values[2],
                    values[3]
            );
        }

        @Override
        public String toString() {
            return "Graphics.RECT_F";
        }
    };

    @NonNull
    public static Graphics<Point> POINT = new Graphics<Point>() {
        @Override
        public int getValuesSize() {
            return 2;
        }

        @Override
        public void getValues(@NonNull Point point, @NonNull float[] values) {
            toArray(point, values);
        }

        @Override
        public void setValues(@NonNull Point point, @NonNull float[] values) {
            point.set(
                    (int) (values[0] + .5F),
                    (int) (values[1] + .5F)
            );
        }

        @Override
        public String toString() {
            return "Graphics.POINT";
        }
    };

    @NonNull
    public static Graphics<PointF> POINT_F = new Graphics<PointF>() {
        @Override
        public int getValuesSize() {
            return 2;
        }

        @Override
        public void getValues(@NonNull PointF pointF, @NonNull float[] values) {
            toArray(pointF, values);
        }

        @Override
        public void setValues(@NonNull PointF pointF, @NonNull float[] values) {
            pointF.set(values[0], values[1]);
        }

        @Override
        public String toString() {
            return "Graphics.POINT_F";
        }
    };

    private static class Points extends Graphics<List<PointF>> {

        private final int size;

        private Points(int size) {
            this.size = size;
        }

        @Override
        public int getValuesSize() {
            return size;
        }

        @Override
        public void getValues(@NonNull List<PointF> pointFS, @NonNull float[] values) {
            toArray(pointFS, values);
        }

        @Override
        public void setValues(@NonNull List<PointF> pointFS, @NonNull float[] values) {
            for (int i = 0; i < size - 1; i += 2) {
                pointFS.get(i / 2).set(values[i], values[i + 1]);
            }
        }

        @Override
        public String toString() {
            return "Graphics.points(" + size + ")";
        }
    }
}
