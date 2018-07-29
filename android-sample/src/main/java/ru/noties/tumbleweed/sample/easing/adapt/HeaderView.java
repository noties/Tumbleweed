package ru.noties.tumbleweed.sample.easing.adapt;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.noties.adapt.DynamicHolder;
import ru.noties.adapt.ItemView;
import ru.noties.tumbleweed.sample.R;

public class HeaderView extends ItemView<EasingItem.Header, DynamicHolder> {

    @NonNull
    @Override
    public DynamicHolder createHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new DynamicHolder(inflater.inflate(R.layout.view_header_item, parent, false));
    }

    @Override
    public void bindHolder(@NonNull DynamicHolder holder, @NonNull EasingItem.Header item) {
        holder.<TextView>on(R.id.text, textView -> textView.setText(item.text()));
    }
}
