package ru.noties.tumbleweed.sample.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import ru.noties.tumbleweed.Tween;
import ru.noties.tumbleweed.TweenManager;
import ru.noties.tumbleweed.TweenType;
import ru.noties.tumbleweed.android.DrawableTweenManager;
import ru.noties.tumbleweed.equations.Linear;

public class SpinnerDrawable extends SquareDrawable implements Animatable {

    private static final int MIN_ALPHA = 75;
    private static final int MAX_ALPHA = 255;

    private final TweenManager tweenManager;

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final RectF rectF = new RectF();

    private final int[] colors;

    private final int parts;

    private final float sweep;

    private final int[] alphas;

    private int shift;

    @SuppressWarnings("WeakerAccess")
    public SpinnerDrawable(@ColorInt int[] colors) {
        this.tweenManager = DrawableTweenManager.create(this);
        this.paint.setStyle(Paint.Style.FILL);
        this.colors = colors;
        this.parts = colors.length;
        this.sweep = 360.F / parts;

        this.alphas = new int[parts];
        {
            final int step = (MAX_ALPHA - MIN_ALPHA) / parts;
            for (int i = 0; i < parts; i++) {
                alphas[i] = MIN_ALPHA + (i * step);
            }
        }
    }

    @Override
    protected void onSideCalculated(int side) {
        rectF.set(0, 0, side, side);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {

        float angle = 360 - sweep;

        for (int i = 0; i < parts; i++) {
            paint.setColor(colors[i]);
            paint.setAlpha((int) (alphas[(i + shift) % parts] + .5F));
            canvas.drawArc(rectF, angle, sweep, true, paint);
            angle -= sweep;
        }
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
        Tween.to(this, new Type(), .75F)
                .target(parts)
                .ease(Linear.INOUT)
                .repeat(-1, 0)
                .start(tweenManager);

    }

    private static class Type implements TweenType<SpinnerDrawable> {

        @Override
        public int getValuesSize() {
            return 1;
        }

        @Override
        public void getValues(@NonNull SpinnerDrawable spinnerDrawable, @NonNull float[] values) {
            values[0] = spinnerDrawable.shift;
        }

        @Override
        public void setValues(@NonNull SpinnerDrawable spinnerDrawable, @NonNull float[] values) {
            spinnerDrawable.shift = (int) (values[0] + .5F);
        }
    }
}
