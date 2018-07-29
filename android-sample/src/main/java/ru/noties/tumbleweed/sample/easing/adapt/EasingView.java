package ru.noties.tumbleweed.sample.easing.adapt;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.noties.adapt.Holder;
import ru.noties.adapt.ItemView;
import ru.noties.tumbleweed.sample.R;
import ru.noties.tumbleweed.sample.easing.EasingDrawable;

public class EasingView extends ItemView<EasingItem.Easing, EasingView.EasingHolder> {

    @NonNull
    @Override
    public EasingHolder createHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new EasingHolder(inflater.inflate(R.layout.view_easing_item, parent, false));
    }

    @Override
    public void bindHolder(@NonNull EasingHolder holder, @NonNull EasingItem.Easing item) {

        holder.name.setText(item.name());

        final Drawable bg = holder.view.getBackground();

        final EasingDrawable easingDrawable;
        if (!(bg instanceof EasingDrawable)) {
            easingDrawable = new EasingDrawable(item.equation(), 0xffEF5350);
            holder.view.setBackground(easingDrawable);
        } else {
            easingDrawable = (EasingDrawable) bg;
            easingDrawable.equation(item.equation());
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

        EasingHolder(@NonNull View view) {
            super(view);

            this.name = requireView(R.id.text);
            this.view = requireView(R.id.view);
        }
    }
}
