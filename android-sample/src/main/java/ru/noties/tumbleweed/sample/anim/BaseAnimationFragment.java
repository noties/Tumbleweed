package ru.noties.tumbleweed.sample.anim;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import ru.noties.tumbleweed.android.utils.ViewUtils;

public abstract class BaseAnimationFragment extends Fragment {

    public interface Action {
        void apply(@NonNull View view);
    }

    private Action inAction;
    private Action outAction;

    public void setInAction(@Nullable Action inAction) {
        this.inAction = inAction;
    }

    public void setOutAction(@Nullable Action outAction) {
        this.outAction = outAction;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (inAction != null) {
            ViewUtils.whenReady(view, inAction::apply);
        }
    }

    public void triggerOutAction() {
        if (outAction != null) {
            final View view = getView();
            if (view != null) {
                ViewUtils.whenReady(view, outAction::apply);
            }
        }
    }
}
