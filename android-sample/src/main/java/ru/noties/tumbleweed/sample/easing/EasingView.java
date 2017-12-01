package ru.noties.tumbleweed.sample.easing;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class EasingView extends View {

    public EasingView(Context context) {
        super(context);
    }

    public EasingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //noinspection SuspiciousNameCombination
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
