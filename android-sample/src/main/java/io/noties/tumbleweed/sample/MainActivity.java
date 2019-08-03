package io.noties.tumbleweed.sample;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

import io.noties.tumbleweed.Timeline;
import io.noties.tumbleweed.Tween;
import io.noties.tumbleweed.android.ViewTweenManager;
import io.noties.tumbleweed.android.types.Alpha;
import io.noties.tumbleweed.android.types.Pivot;
import io.noties.tumbleweed.android.types.Rotation;
import io.noties.tumbleweed.android.types.Scale;
import io.noties.tumbleweed.android.types.Translation;
import io.noties.tumbleweed.android.utils.ViewUtils;
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
            final ViewTweenManager tweenManager = ViewTweenManager.get(findViewById(R.id.container));
            final View view = tweenManager.view();

            ViewUtils.whenReady(view, $ -> {
                Timeline.createParallel(1.0F)
                        .push(Tween.to(view, Alpha.VIEW).target(0.0F))
                        .push(Tween.to(view, Scale.XY).target(0.0F, 0.0F))
                        .push(Tween.to(view, Rotation.I).target(270))
                        .push(Tween.to(view, Pivot.XY).target(0, 0))
                        .push(Tween.to(view, Translation.XY).target($.getWidth(), $.getHeight()))
                        .repeatYoyo(-1, 0)
                        .start(tweenManager);
            });
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
