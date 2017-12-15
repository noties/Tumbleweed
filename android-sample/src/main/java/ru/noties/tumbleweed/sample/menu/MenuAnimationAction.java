package ru.noties.tumbleweed.sample.menu;

import android.support.annotation.NonNull;
import android.view.View;

import ru.noties.tumbleweed.Tween;
import ru.noties.tumbleweed.android.ViewTweenManager;
import ru.noties.tumbleweed.android.types.Alpha;
import ru.noties.tumbleweed.equations.Cubic;
import ru.noties.tumbleweed.sample.anim.BaseAnimationFragment;

public class MenuAnimationAction implements BaseAnimationFragment.Action {

    public static final long DURATION = 1000L;

    private final boolean exit;

    public MenuAnimationAction(boolean exit) {
        this.exit = exit;
    }

    @Override
    public void apply(@NonNull View view) {

        final float alpha = exit
                ? .0F
                : 1.F;

        view.setAlpha(1.F - alpha);

        Tween.to(view, Alpha.VIEW, DURATION / 1000.F)
                .target(alpha)
                .ease(exit ? Cubic.OUT : Cubic.IN)
                .start(ViewTweenManager.create(view));
    }
}
