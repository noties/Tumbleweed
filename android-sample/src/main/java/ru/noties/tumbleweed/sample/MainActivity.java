package ru.noties.tumbleweed.sample;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import ru.noties.tumbleweed.BaseTween;
import ru.noties.tumbleweed.Timeline;
import ru.noties.tumbleweed.Tween;
import ru.noties.tumbleweed.TweenCallback;
import ru.noties.tumbleweed.android.types.Alpha;
import ru.noties.tumbleweed.android.types.Rotation;
import ru.noties.tumbleweed.android.types.Translation;
import ru.noties.tumbleweed.equations.Elastic;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View container = findViewById(R.id.container);

        final View view1 = findViewById(R.id.view1);
//        final View view2 = findViewById(R.id.view2);
//        final View view3 = findViewById(R.id.view3);
//        final View view4 = findViewById(R.id.view4);

        runTween(container, view1);
//        runValueAnimator(view1);
    }

    private void runTween(@NonNull View container, @NonNull View view) {
//
//        final Timeline timeline = Timeline.createSequence()
//                .push(Tween.to(view, Translation.Y, 2.F).target(400).waypoint(75).ease(Elastic.INOUT))
//                .pushPause(1.F)
//                .beginParallel()
////                    .push(Tween.call(new Callback()))
//                    .push(Tween.to(view, Rotation.XY_JOINT, 2.F).target(270).waypoint(-90))
//                    .push(Tween.to(view, Alpha.VIEW, 2.F).target(.5F))
//                .end()
////                .push(Timeline.createParallel()
////                        .push(Tween.to(view, Rotation.XY_JOINT, 2.F).target(270).waypoint(-90))
////                        .push(Tween.to(view, Alpha.VIEW, 2.F).target(.5F)))
////                .push(Tween.to(view, Rotation.XY_JOINT, 3.F).target(270).waypoint(-90))
////                .push(Tween.to(view, new XYZ(), 2.F).target(100, 100, 24).waypoint(200, 200, 36))
//                .repeatYoyo(-1, 1.F)
//                .start();

        final Timeline timeline = Timeline.createParallel()
                .push(Tween.to(view, Rotation.XY_JOINT, 2.F).target(270).waypoint(-90))
                .push(Tween.to(view, Alpha.VIEW, 2.F).target(.5F))
                .repeatYoyo(-1, 1.F)
                .start();

        container.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            long time;

            @Override
            public boolean onPreDraw() {
//                Log.i("preDraw", "" + time);
                if (time == 0L) {
                    time = SystemClock.uptimeMillis();
                    timeline.update(0);
                } else {
                    final long now = SystemClock.uptimeMillis();
                    final float delta = (float) (now - time) / 1000.F;
                    time = now;
                    timeline.update(delta);
                }
                view.postInvalidateOnAnimation();
                return true;
            }
        });
        container.postInvalidateOnAnimation();
    }

//    private void runValueAnimator(@NonNull View view) {
//        final ValueAnimator animator = ValueAnimator.ofFloat(400);
//        animator.setInterpolator(new TweenInterpolator(Elastic.INOUT));
//        animator.setDuration(2000L);
//        animator.addUpdateListener(animation -> {
//            final float fraction = animation.getAnimatedFraction();
//            final float value = fraction * 400;
//            view.setTranslationY(value);
//        });
//        animator.setRepeatMode(ValueAnimator.REVERSE);
//        animator.setRepeatCount(ValueAnimator.INFINITE);
//        animator.start();
//    }

//    private static class XYZ implements TweenType<View> {
//
//        @Override
//        public int getValuesSize() {
//            return 3;
//        }
//
//        @Override
//        public void getValues(@NonNull View view, @NonNull float[] values) {
//            values[0] = view.getX();
//            values[1] = view.getY();
//            values[2] = view.getZ();
//        }
//
//        @Override
//        public void setValues(@NonNull View view, @NonNull float[] values) {
//            view.setX(values[0]);
//            view.setY(values[1]);
//            view.setZ(values[2]);
//        }
//    }
//
//    private static class WH implements TweenType<View> {
//
//        @Override
//        public int getValuesSize() {
//            return 2;
//        }
//
//        @Override
//        public void getValues(@NonNull View view, @NonNull float[] values) {
//            values[0] = view.getWidth();
//            values[1] = view.getHeight();
//        }
//
//        @Override
//        public void setValues(@NonNull View view, @NonNull float[] values) {
//            final ViewGroup.LayoutParams params = view.getLayoutParams();
//            params.width = (int) values[0];
//            params.height = (int) values[1];
//            view.requestLayout();
//        }
//    }

//    private static class Callback implements TweenCallback {
//
//        @Override
//        public void onEvent(int type, @NonNull BaseTween source) {
//            Log.i("callback", String.format("type: %d, source: %s", type, source));
//        }
//    }
}
