package ru.noties.tumbleweed.sample;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import ru.noties.debug.AndroidLogDebugOutput;
import ru.noties.debug.Debug;
import ru.noties.tumbleweed.BaseTween;
import ru.noties.tumbleweed.Timeline;
import ru.noties.tumbleweed.Tween;
import ru.noties.tumbleweed.TweenCallback;
import ru.noties.tumbleweed.android.ViewTweenManager;
import ru.noties.tumbleweed.android.types.Argb;
import ru.noties.tumbleweed.android.types.Elevation;
import ru.noties.tumbleweed.android.types.Rotation;
import ru.noties.tumbleweed.android.types.Scale;
import ru.noties.tumbleweed.android.types.Translation;
import ru.noties.tumbleweed.android.utils.ViewUtils;
import ru.noties.tumbleweed.equations.Bounce;
import ru.noties.tumbleweed.equations.Elastic;
import ru.noties.tumbleweed.sample.progress.ProgressFragment;

public class MainActivity extends Activity {

    static {
        Debug.init(new AndroidLogDebugOutput(true));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        final View container = findViewById(R.id.container);
//
//        final View view1 = findViewById(R.id.view1);
//        final View view2 = findViewById(R.id.view2);
////        final View view3 = findViewById(R.id.view3);
////        final View view4 = findViewById(R.id.view4);
//
//        runTween(container, view1);

        if (findFragment(R.id.container) == null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, ProgressFragment.newInstance())
                    .commit();
        }
    }

    private void runTween(@NonNull View container, @NonNull View view) {

        if (view.getWidth() == 0) {
            ViewUtils.whenReady(view, view1 -> runTween(container, view));
            return;
        }

        final ViewTweenManager tweenManager = ViewTweenManager.create(container);

//        final CrazyDrawable drawable = new CrazyDrawable();
//        container.setBackground(drawable);

        final int w = container.getWidth();
        final int h = container.getHeight();
//
//        final MaterialProgressDrawable materialProgressDrawable = new MaterialProgressDrawable();
//        container.setBackground(materialProgressDrawable);
//
//        materialProgressDrawable.start();

//        final BottomDrawable drawable = new BottomDrawable();
//        container.setBackground(drawable);
//
//        Timeline.createParallel()
//                .push(drawable.tween().ease(Elastic.INOUT))
//                .push(Tween.to(drawable.paint(), Argb.PAINT, 3.F).target(Argb.toArray(0xFF3F51B5)))
//                .repeatYoyo(-1, .0F)
//                .start(tweenManager);

//        final Drawable d = new ColorDrawable(0x40808080);
//        d.setBounds(0, 0, w, h);
//        ((FrameLayout) container).setForeground(d);
//
//
//        final int side = Math.min(w, h) / 2;
//        final int l = (w - side) / 2;
//        final int t = (h - side) / 2;
//        final int r = l + side;
//        final int b = t + side;
//
//        Tween.to(d.copyBounds(), Graphics.RECT, 3.F)
//                .target(l, t, r, b)
//                .ease(Elastic.INOUT)
//                .action(drawable::setBounds)
//                .repeatYoyo(-1, 0)
//                .start(tweenManager);

//        drawable.tween()
//                .repeatYoyo(-1, .0F)
//                .start(tweenManager);

//        final PaintDrawable2 paintDrawable2 = new PaintDrawable2(0x4000ff0f);
//        paintDrawable2.setCornerRadius(128);
//        {
//            final int side = (int) (Math.min(w * .2F, h * .2F) + .5F);
//            final int left = (w - side) / 2;
//            final int top = (h - side) / 2;
//            paintDrawable2.setBounds(left, top, left + side, top + side);
//        }
//        ((FrameLayout) container).setForeground(paintDrawable2);

//        Timeline.createParallel()
//                .push(Tween.to(paintDrawable2, new PaintDrawableType(), .75F).target(0).ease(Back.INOUT))
//                .push(Tween.to(paintDrawable2.getBounds(), Geometry.RECT, .75F).target(Geometry.toArray(new Rect(0, 0, w, h))).ease(Back.INOUT))
//                .repeatYoyo(-1, 2.F)
//                .start(tweenManager);
//
//        Timeline.createSequence()
//                .push(drawable.y(w, h, 2.F).target(.75F))
//                .pushPause(5.F)
//                .push(drawable.x(w, h, 2.F).target(.25F))
//                .push(drawable.x(w, h, 2.F).target(.75F))
//                .push(drawable.x(w, h, 2.F).target(.5F))
//                .push(drawable.y(w, h, 2.F).target(1.F))
//                .pushPause(15.F)
//                .repeat(-1, .0F)
//                .start(tweenManager);

        Timeline.createSequence()
                .push(Tween.to(view, Translation.Y, 2.F).target(400).ease(Elastic.INOUT))
                .push(Timeline.createParallel()
                        .callback(TweenCallback.BEGIN | TweenCallback.END, new Callback())
                        .push(Tween.to(view, Rotation.I, 2.F).target(270).waypoint(-90))
//                        .push(Tween.to(view, Alpha.VIEW, 2.F).target(.3F).ease(Bounce.INOUT))
                        .push(Tween.to(view, Elevation.I, 2.F).target(64))
                        .push(Tween.to(view, Scale.XY, 2.F).target(1.5F, 1.5F))
                        .push(Tween.to(view, Translation.X, 2.F).target(100))
                        .push(Tween.to(view, Scale.PIVOT_XY, 2.F).target(.2F, .2F).scale(view.getWidth(), view.getHeight()))
                        .push(Tween.to(view, Argb.BACKGROUND, 2.F).target(Argb.toArray(0x8000ffF0))))
                .repeatYoyo(-1, 1.F)
                .start(tweenManager);

        final View v = ((ViewGroup) container).getChildAt(1);
        Timeline.createSequence()
                .push(Tween.to(v, Translation.Y, 2.F)
                        .target(container.getHeight() - v.getHeight())
                        .ease(Bounce.OUT))
                .push(Tween.to(v, Translation.Y, 2.F)
                        .target(0)
                        .ease(Bounce.IN))
                .repeat(-1, 2.F)
                .start(tweenManager);
    }

    private static class Callback implements TweenCallback {

        @Override
        public void onEvent(int type, @NonNull BaseTween source) {
            Debug.i("type: %s, source: %s", type, source);
        }
    }

    private <F extends Fragment> F findFragment(@IdRes int id) {
        //noinspection unchecked
        return (F) getFragmentManager().findFragmentById(id);
    }
}
