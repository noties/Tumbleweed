package ru.noties.tumbleweed.sample.menu;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.noties.tumbleweed.sample.R;
import ru.noties.tumbleweed.sample.anim.BaseAnimationFragment;

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
                .setOnClickListener(v -> callbacks.onDrawableRequested());

        view.findViewById(R.id.easing)
                .setOnClickListener(v -> callbacks.onEasingRequested());
    }
}
