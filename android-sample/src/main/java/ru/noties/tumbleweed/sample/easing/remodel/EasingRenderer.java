package ru.noties.tumbleweed.sample.easing.remodel;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.noties.remodel.renderer.Holder;
import ru.noties.remodel.renderer.Renderer;
import ru.noties.remodel.service.Services;
import ru.noties.tumbleweed.sample.R;
import ru.noties.tumbleweed.sample.easing.EasingDrawable;

public class EasingRenderer extends Renderer<EasingItem.Easing, EasingRenderer.EasingHolder> {

    @NonNull
    @Override
    public EasingHolder createHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new EasingHolder(inflater.inflate(R.layout.render_easing_item, parent, false));
    }

    @Override
    public void render(@NonNull Services services, @NonNull EasingHolder holder, @NonNull EasingItem.Easing item) {

        holder.name.setText(item.name());

        final Drawable bg = holder.view.getBackground();

        final EasingDrawable easingDrawable;
        if (!(bg instanceof EasingDrawable)) {
            easingDrawable = new EasingDrawable(item.equation(), 0xffEF5350);
            holder.view.setBackground(easingDrawable);
        } else {
            easingDrawable = (EasingDrawable) bg;
            easingDrawable.equatation(item.equation());
        }

        easingDrawable.stop();

        holder.itemView.setOnClickListener(v -> {
            if (!easingDrawable.isRunning()) {
                easingDrawable.start();
            }
        });
    }

    static class EasingHolder extends Holder {

        final TextView name;
        final View view;

        EasingHolder(@NonNull View itemView) {
            super(itemView);

            this.name = findView(R.id.text);
            this.view = findView(R.id.view);
        }
    }
}
