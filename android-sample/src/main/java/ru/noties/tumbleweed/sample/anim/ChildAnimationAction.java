package ru.noties.tumbleweed.sample.anim;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import ru.noties.tumbleweed.Tween;
import ru.noties.tumbleweed.TweenAction;
import ru.noties.tumbleweed.TweenCallback;
import ru.noties.tumbleweed.android.ViewTweenManager;
import ru.noties.tumbleweed.android.types.Translation;
import ru.noties.tumbleweed.android.utils.ClipChildren;
import ru.noties.tumbleweed.equations.Back;
import ru.noties.tumbleweed.sample.R;

public class ChildAnimationAction implements BaseAnimationFragment.Action {

    public static final long DURATION = 1000L;

    private final boolean exit;

    public ChildAnimationAction(boolean exit) {
        this.exit = exit;
    }

    @Override
    public void apply(@NonNull View view) {

        final int height = view.getHeight();
        if (!exit) {
            view.setTranslationY(-height);
        }

        final View container = view.findViewById(R.id.app_bar_container);
        final ClipChildren.State clipState = ClipChildren.disable(container);

        Tween.to(view, Translation.Y, (float) DURATION / 1000.F)
                .target(exit ? -height : 0)
                .ease(exit ? Back.IN : Back.OUT)
                .action(new AppBarAction(container, clipState))
                .addCallback(TweenCallback.COMPLETE, (type, source) -> {
                    if (clipState != null) {
                        clipState.restore(container);
                    }
                })
                .start(ViewTweenManager.create(view));
    }

    // applied only if we could disable clipping, otherwise visual effect would be not so good
    private static class AppBarAction implements TweenAction<View> {

        private final View appBar;
        private final ClipChildren.State clipState;

        AppBarAction(@NonNull View appBar, @Nullable ClipChildren.State clipState) {
            this.appBar = appBar;
            this.clipState = clipState;
        }

        @Override
        public void apply(@NonNull View view) {

            if (clipState != null) {

                final float translationY = view.getTranslationY();

                final int appBarHeight = appBar.getHeight();

                final float scale;
                final float y;
                if (translationY > 0) {
                    // we have overlap, let's scale appBar a bit for nicer visual effect
                    scale = (translationY + appBarHeight) / appBarHeight;
                    y = -translationY / 2;
                } else {
                    scale = 1.F;
                    y = .0F;
                }
                appBar.setScaleX(scale);
                appBar.setScaleY(scale);
                appBar.setTranslationY(y);
            }
        }
    }
}
