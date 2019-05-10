package io.noties.tumbleweed.sample.menu;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionValues;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.noties.tumbleweed.Timeline;
import io.noties.tumbleweed.Tween;
import io.noties.tumbleweed.android.AnimatorTweenManager;
import io.noties.tumbleweed.android.types.Rotation;
import io.noties.tumbleweed.android.types.Scale;
import io.noties.tumbleweed.sample.R;
import io.noties.tumbleweed.sample.anim.BaseAnimationFragment;

import static java.lang.Math.random;

public class MenuFragment extends BaseAnimationFragment {

    public static MenuFragment newInstance(boolean in) {
        final Bundle bundle = new Bundle();

        final MenuFragment fragment = new MenuFragment();
        fragment.setArguments(bundle);
        if (in) {
            fragment.setInAction(new MenuAnimationAction(false));
        }
        fragment.setOutAction(new MenuAnimationAction(true));
        return fragment;
    }

    public interface Callbacks {

        void onDrawableRequested();

        void onEasingRequested();
    }

    private Callbacks callbacks;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.callbacks = (Callbacks) activity;
    }

    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        // yeah, without it won't work
        return ValueAnimator.ofFloat(.0F, 1.F).setDuration(MenuAnimationAction.DURATION);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, parent, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.drawable)
                .setOnClickListener(v -> {
                    if (false) {
                        // usage of AnimatorTweenManager in transitions
                        testTransition(v);
                    } else {
                        callbacks.onDrawableRequested();
                    }
                });

        view.findViewById(R.id.easing)
                .setOnClickListener(v -> callbacks.onEasingRequested());
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private void testTransition(@NonNull View view) {

        TransitionManager.beginDelayedTransition((ViewGroup) view.getParent(), new Transition() {

            private final String KEY = "my-key:background:color";

            @Override
            public void captureStartValues(TransitionValues transitionValues) {
                captureValues(transitionValues);
            }

            @Override
            public void captureEndValues(TransitionValues transitionValues) {
                captureValues(transitionValues);
            }

            private void captureValues(@NonNull TransitionValues values) {
                final Drawable drawable = values.view.getBackground();
                if (drawable instanceof ColorDrawable) {
                    final int color = ((ColorDrawable) drawable).getColor();
                    values.values.put(KEY, color);
                }
            }

            @Override
            public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {

                if (startValues == null
                        || endValues == null) {
                    return null;
                }

                final Integer from = (Integer) startValues.values.get(KEY);
                final Integer to = (Integer) endValues.values.get(KEY);

                if (from == null
                        || to == null) {
                    return null;
                }

                final View v = endValues.view;

                final AnimatorTweenManager tweenManager = AnimatorTweenManager.create();
                Timeline.createSequence()
                        .push(Tween.to(v, Rotation.I, .25F).target(360))
                        .push(Tween.to(v, Scale.XY, .25F).target(.5F, .5F))
                        .push(Tween.to(v, Scale.XY, .25F).target(1, 1))
                        .start(tweenManager);

                return tweenManager.animator();
            }
        });

        view.setRotation(0);
        view.setBackgroundColor(Color.rgb(
                (int) (random() * 255 + .5F),
                (int) (random() * 255 + .5F),
                (int) (random() * 255 + .5F)
        ));
    }
}
