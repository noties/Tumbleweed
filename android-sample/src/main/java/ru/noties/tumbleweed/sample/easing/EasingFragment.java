package ru.noties.tumbleweed.sample.easing;

import android.app.Fragment;
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

import ru.noties.remodel.Remodel;
import ru.noties.remodel.adapter.AdapterInfo;
import ru.noties.tumbleweed.sample.R;
import ru.noties.tumbleweed.sample.easing.remodel.EasingItem;
import ru.noties.tumbleweed.sample.easing.remodel.EasingModel;
import ru.noties.tumbleweed.sample.easing.remodel.EasingReducer;
import ru.noties.tumbleweed.sample.easing.remodel.EasingRenderer;
import ru.noties.tumbleweed.sample.easing.remodel.HeaderRenderer;

public class EasingFragment extends Fragment {

    public static EasingFragment newInstance() {
        final Bundle bundle = new Bundle();

        final EasingFragment fragment = new EasingFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_easing, parent, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        final Remodel<EasingModel, EasingItem> remodel = Remodel.builder(createModel(), EasingItem.class)
                .addRenderer(EasingItem.Header.class, new HeaderRenderer())
                .addRenderer(EasingItem.Easing.class, new EasingRenderer())
                .reducer(new EasingReducer())
                .recyclerView(recyclerView)
                .build();

        initializeRecyclerView(recyclerView, remodel.adapterInfo());
    }

    private void initializeRecyclerView(@NonNull RecyclerView recyclerView, @NonNull AdapterInfo<EasingItem> adapterInfo) {

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

    private static EasingModel createModel() {
        return new EasingModel();
    }
}
