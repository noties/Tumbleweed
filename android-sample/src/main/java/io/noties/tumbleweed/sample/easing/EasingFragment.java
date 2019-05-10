package io.noties.tumbleweed.sample.easing;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.noties.adapt.Adapt;
import io.noties.tumbleweed.TweenEquation;
import io.noties.tumbleweed.equations.Back;
import io.noties.tumbleweed.equations.Bounce;
import io.noties.tumbleweed.equations.Circ;
import io.noties.tumbleweed.equations.Cubic;
import io.noties.tumbleweed.equations.Elastic;
import io.noties.tumbleweed.equations.Expo;
import io.noties.tumbleweed.equations.Quad;
import io.noties.tumbleweed.equations.Quart;
import io.noties.tumbleweed.equations.Quint;
import io.noties.tumbleweed.equations.Sine;
import io.noties.tumbleweed.sample.R;
import io.noties.tumbleweed.sample.anim.BaseAnimationFragment;
import io.noties.tumbleweed.sample.anim.ChildAnimationAction;
import io.noties.tumbleweed.sample.easing.adapt.EasingItem;
import io.noties.tumbleweed.sample.easing.adapt.EasingView;
import io.noties.tumbleweed.sample.easing.adapt.HeaderView;

public class EasingFragment extends BaseAnimationFragment {

    public static EasingFragment newInstance() {
        final Bundle bundle = new Bundle();

        final EasingFragment fragment = new EasingFragment();
        fragment.setArguments(bundle);
        fragment.setInAction(new ChildAnimationAction(false));
        fragment.setOutAction(new ChildAnimationAction(true));

        return fragment;
    }

    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        // yeah, without it won't work
        return ValueAnimator.ofFloat(.0F, 1.F).setDuration(ChildAnimationAction.DURATION);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_easing, parent, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        final Adapt<EasingItem> adapt = Adapt.builder(EasingItem.class)
                .include(EasingItem.Header.class, new HeaderView())
                .include(EasingItem.Easing.class, new EasingView())
                .build();

        adapt.setItems(items());

        recyclerView.setAdapter(adapt.recyclerViewAdapter());

        initializeRecyclerView(recyclerView, adapt);
    }

    private void initializeRecyclerView(@NonNull RecyclerView recyclerView, @NonNull Adapt<EasingItem> adapterInfo) {

        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            final int item = adapterInfo.assignedViewType(EasingItem.Easing.class);

            @Override
            public int getSpanSize(int position) {
                return item == adapterInfo.itemViewType(position)
                        ? 1
                        : layoutManager.getSpanCount();
            }
        });

        final int spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.setPadding(spacing, spacing, spacing, spacing);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(spacing, spacing, spacing, spacing);
            }
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @NonNull
    private static List<EasingItem> items() {

        final List<EasingItem> list = new ArrayList<>();

        add("Sine", list, Sine.values());
        add("Quad", list, Quad.values());
        add("Cubic", list, Cubic.values());
        add("Quart", list, Quart.values());
        add("Quint", list, Quint.values());
        add("Expo", list, Expo.values());
        add("Circ", list, Circ.values());
        add("Back", list, Back.values());
        add("Elastic", list, Elastic.values());
        add("Bounce", list, Bounce.values());

        return Collections.unmodifiableList(list);
    }

    private static void add(@NonNull String name, @NonNull List<EasingItem> list, @NonNull Enum<? extends TweenEquation>[] equations) {
        list.add(new EasingItem.Header(name));
        for (Enum<? extends TweenEquation> equation : equations) {
            list.add(new EasingItem.Easing(equation));
        }
    }
}
