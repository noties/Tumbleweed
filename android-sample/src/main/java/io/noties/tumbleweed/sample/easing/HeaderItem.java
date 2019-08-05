package io.noties.tumbleweed.sample.easing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import io.noties.adapt.Item;
import io.noties.tumbleweed.sample.R;

public class HeaderItem extends Item<HeaderItem.Holder> {

    private final String name;

    HeaderItem(@NonNull String name) {
        super(name.hashCode());
        this.name = name;
    }

    @NonNull
    @Override
    public Holder createHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new Holder(inflater.inflate(R.layout.view_header_item, parent, false));
    }

    @Override
    public void render(@NonNull Holder holder) {
        holder.text.setText(name);
    }

    static class Holder extends Item.Holder {

        final TextView text;

        Holder(@NonNull View itemView) {
            super(itemView);

            this.text = requireView(R.id.text);
        }
    }
}
