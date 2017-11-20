package ru.noties.tumbleweed.sample;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import ru.noties.debug.AndroidLogDebugOutput;
import ru.noties.debug.Debug;
import ru.noties.tumbleweed.Timeline;
import ru.noties.tumbleweed.Tween;
import ru.noties.tumbleweed.android.types.Color;
import ru.noties.tumbleweed.android.types.Elevation;
import ru.noties.tumbleweed.android.types.Rotation;
import ru.noties.tumbleweed.android.types.Scale;
import ru.noties.tumbleweed.android.types.Translation;
import ru.noties.tumbleweed.android.utils.ViewUtils;
import ru.noties.tumbleweed.equations.Bounce;
import ru.noties.tumbleweed.equations.Elastic;

public class MainActivity extends Activity {

    static {
        Debug.init(new AndroidLogDebugOutput(true));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View container = findViewById(R.id.container);

        final View view1 = findViewById(R.id.view1);
        final View view2 = findViewById(R.id.view2);
//        final View view3 = findViewById(R.id.view3);
//        final View view4 = findViewById(R.id.view4);

        runTween(container, view1);
    }

    private void runTween(@NonNull View container, @NonNull View view) {

        if (view.getWidth() == 0) {
            ViewUtils.whenReady(view, view1 -> runTween(container, view));
            return;
        }

        final Timeline timeline = Timeline.createSequence()
                .push(Tween.to(view, Translation.Y, 2.F).target(400).ease(Elastic.INOUT))
                .push(Timeline.createParallel()
                        .push(Tween.to(view, Rotation.I, 2.F).target(270).waypoint(-90))
//                        .push(Tween.to(view, Alpha.VIEW, 2.F).target(.3F).ease(Bounce.INOUT))
                        .push(Tween.to(view, Elevation.I, 2.F).target(64))
                        .push(Tween.to(view, Scale.XY, 2.F).target(1.5F, 1.5F))
                        .push(Tween.to(view, Scale.PIVOT_XY, 2.F).target(.2F, .2F).scale(view.getWidth(), view.getHeight()))
                        .push(Tween.to(view, new BackgroundColor(), 2.F).target(Color.argb(0x8000ffF0))))
                .repeatYoyo(-1, 1.F)
                .start();

        final View v = ((ViewGroup) container).getChildAt(1);
        final Timeline t = Timeline.createSequence()
                .push(Tween.to(v, Translation.Y, 2.F)
                        .target(container.getHeight() - v.getHeight())
                        .ease(Bounce.OUT))
                .push(Tween.to(v, Translation.Y, 2.F)
                        .target(0)
                        .ease(Bounce.IN))
                .repeat(-1, 2.F)
                .start();

        container.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            long time;

            @Override
            public boolean onPreDraw() {
                if (time == 0L) {
                    time = SystemClock.uptimeMillis();
                    timeline.update(0);
                    t.update(0);
                } else {
                    final long now = SystemClock.uptimeMillis();
                    final float delta = (float) (now - time) / 1000.F;
                    time = now;
                    timeline.update(delta);
                    t.update(delta);
                }
                view.postInvalidateOnAnimation();
                return true;
            }
        });
        container.postInvalidateOnAnimation();
    }

    private static class BackgroundColor extends Color.Argb<View> {

        @Override
        protected int getColor(@NonNull View view) {
            final int color;
            final Drawable drawable = view.getBackground();
            if (drawable instanceof ColorDrawable) {
                color = ((ColorDrawable) drawable).getColor();
            } else {
                color = 0;
            }
            return color;
        }

        @Override
        protected void setColor(@NonNull View view, int color) {
            view.setBackgroundColor(color);
        }
    }
}
