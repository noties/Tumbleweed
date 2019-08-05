package io.noties.tumbleweed.sample.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

// a simple drawable that will make square bounds square
public abstract class SquareDrawable extends Drawable {


    protected abstract void onSideCalculated(int side);

    protected abstract void onDraw(@NonNull Canvas canvas);


    private float left;

    private float top;

    private int padding;

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);

        final int w = bounds.width();
        final int h = bounds.height();

        final int side = Math.min(w, h) - (padding * 2);

        this.left = ((float) w - side) / 2;
        this.top = ((float) h - side) / 2;

        onSideCalculated(side);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        final int save = canvas.save();
        try {
            // we can be square already
            if (left != 0
                    || top != 0) {
                canvas.translate(left, top);
            }
            onDraw(canvas);
        } finally {
            canvas.restoreToCount(save);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        // no op
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        // no op
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

    @SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
    public SquareDrawable setPadding(int padding) {
        this.padding = padding;
        return this;
    }
}
