package io.noties.tumbleweed.sample;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import io.noties.tumbleweed.BaseTween;
import io.noties.tumbleweed.Tween;
import io.noties.tumbleweed.TweenCallback;
import io.noties.tumbleweed.TweenCallbackAdapter;
import io.noties.tumbleweed.android.ViewTweenManager;
import io.noties.tumbleweed.android.types.Alpha;
import io.noties.tumbleweed.sample.anim.BaseAnimationFragment;
import io.noties.tumbleweed.sample.drawable.DrawableFragment;
import io.noties.tumbleweed.sample.easing.EasingFragment;
import io.noties.tumbleweed.sample.menu.MenuFragment;

public class MainActivity extends Activity implements MenuFragment.Callbacks {

    private static final String TAG_MENU = "tag.Menu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FragmentManager manager = getFragmentManager();
        if (manager.findFragmentById(R.id.container) == null) {
            manager.beginTransaction()
                    .replace(R.id.container, MenuFragment.newInstance(false), TAG_MENU)
                    .commit();
        }

        if (true) {
            final View view = findViewById(R.id.container);
//            Tween.to(view, Scale.XY, 5.0F)
//                    .target(0.5F, 0.5F)
//                    .addCallback(TweenCallback.BEGIN, ($1, $2) -> Log.e("onEvent", "BEGIN"))
//                    .addCallback(TweenCallback.START, ($1, $2) -> Log.e("onEvent", "START"))
//                    .addCallback(TweenCallback.END, ($1, $2) -> Log.e("onEvent", "END"))
//                    .addCallback(TweenCallback.COMPLETE, ($1, $2) -> Log.e("onEvent", "COMPLETE"))
//                    .addCallback(TweenCallback.BACK_BEGIN, ($1, $2) -> Log.e("onEvent", "BACK_BEGIN"))
//                    .addCallback(TweenCallback.BACK_START, ($1, $2) -> Log.e("onEvent", "BACK_START"))
//                    .addCallback(TweenCallback.BACK_END, ($1, $2) -> Log.e("onEvent", "BACK_END"))
//                    .addCallback(TweenCallback.BACK_COMPLETE, ($1, $2) -> Log.e("onEvent", "BACK_COMPLETE"))
//                    .repeatYoyo(1, 2.0F)
//                    .start(ViewTweenManager.get(view));

            view.setAlpha(0.5F);

            Tween.from(view, Alpha.VIEW, 5.0F)
                    .target(1.0F)
                    .addCallback(new TweenCallbackAdapter() {
                        @Override
                        public void onBegin(@NonNull BaseTween source) {
                            Log.e("ADAPTER", "onBegin");
                        }

                        @Override
                        public void onStart(@NonNull BaseTween source) {
                            Log.e("ADAPTER", "onStart");
                        }

                        @Override
                        public void onEnd(@NonNull BaseTween source) {
                            Log.e("ADAPTER", "onEnd");
                        }

                        @Override
                        public void onComplete(@NonNull BaseTween source) {
                            Log.e("ADAPTER", "onComplete");
                        }
                    })
                    .addCallback(TweenCallback.BEGIN, ($1, $2) -> Log.e("onEvent", "BEGIN"))
                    .addCallback(TweenCallback.START, ($1, $2) -> Log.e("onEvent", "START"))
                    .addCallback(TweenCallback.END, ($1, $2) -> Log.e("onEvent", "END"))
                    .addCallback(TweenCallback.COMPLETE, ($1, $2) -> Log.e("onEvent", "COMPLETE"))
                    .addCallback(TweenCallback.ANY_FORWARD, ($1, $2) -> Log.e("onEvent", "ANY_FORWARD, type: " + $1))
                    .repeatYoyo(3, 2.0F)
                    .start(ViewTweenManager.get(view));
        }
    }

    @Override
    public void onBackPressed() {

        final FragmentManager manager = getFragmentManager();
        final Fragment fragment = manager.findFragmentById(R.id.container);

        if (!TAG_MENU.equals(fragment.getTag())) {
            ((BaseAnimationFragment) fragment).triggerOutAction();
            manager.beginTransaction()
                    .replace(R.id.container, MenuFragment.newInstance(true), TAG_MENU)
                    .commit();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onDrawableRequested() {
        menuOut();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, DrawableFragment.newInstance())
                .commit();
    }

    @Override
    public void onEasingRequested() {
        menuOut();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, EasingFragment.newInstance())
                .commit();
    }

    private void menuOut() {
        ((BaseAnimationFragment) getFragmentManager().findFragmentById(R.id.container)).triggerOutAction();
    }
}
