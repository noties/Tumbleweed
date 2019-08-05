package io.noties.tumbleweed.sample.easing;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.noties.adapt.Adapt;
import io.noties.adapt.Item;
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

        final Adapt adapt = Adapt.create();

        adapt.setItems(items());

        recyclerView.setAdapter(adapt);

        initializeRecyclerView(recyclerView, adapt);
    }

    private void initializeRecyclerView(@NonNull RecyclerView recyclerView, @NonNull Adapt adapt) {

        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            final int item = Item.generatedViewType(EasingItem.class);

            @Override
            public int getSpanSize(int position) {
                return item == adapt.getItemViewType(position)
                        ? 1
                        : layoutManager.getSpanCount();
            }
        });

        final int spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.setPadding(spacing, spacing, spacing, spacing);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.set(spacing, spacing, spacing, spacing);
            }
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @NonNull
    private static List<Item> items() {

        final List<Item> list = new ArrayList<>();

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

    private static void add(@NonNull String name, @NonNull List<Item> list, @NonNull Enum<? extends TweenEquation>[] equations) {
        list.add(new HeaderItem(name));
        for (Enum<? extends TweenEquation> equation : equations) {
            list.add(new EasingItem(equation));
        }
    }
}
