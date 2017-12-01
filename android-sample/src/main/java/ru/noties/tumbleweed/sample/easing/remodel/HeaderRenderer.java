package ru.noties.tumbleweed.sample.easing.remodel;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.noties.remodel.renderer.Holder;
import ru.noties.remodel.renderer.Renderer;
import ru.noties.remodel.service.Services;
import ru.noties.tumbleweed.sample.R;

public class HeaderRenderer extends Renderer<EasingItem.Header, HeaderRenderer.HeaderHolder> {

    @NonNull
    @Override
    public HeaderHolder createHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new HeaderHolder(inflater.inflate(R.layout.render_easing_header, parent, false));
    }

    @Override
    public void render(@NonNull Services services, @NonNull HeaderHolder holder, @NonNull EasingItem.Header item) {
        holder.text.setText(item.text());
    }

    static class HeaderHolder extends Holder {

        final TextView text;

        HeaderHolder(@NonNull View itemView) {
            super(itemView);

            this.text = findView(R.id.text);
        }
    }
}
