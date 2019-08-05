package io.noties.tumbleweed.sample.easing;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import io.noties.adapt.Item;
import io.noties.tumbleweed.TweenEquation;
import io.noties.tumbleweed.sample.R;

public class EasingItem extends Item<EasingItem.Holder> {

    private final String name;
    private final TweenEquation equation;

    EasingItem(@NonNull Enum<? extends TweenEquation> equation) {
        super(createId(equation));
        this.name = equation.name();
        this.equation = (TweenEquation) equation;
    }

    @NonNull
    @Override
    public Holder createHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new Holder(inflater.inflate(R.layout.view_easing_item, parent, false));
    }

    @Override
    public void render(@NonNull Holder holder) {

        holder.name.setText(name);

        final Drawable bg = holder.view.getBackground();

        final EasingDrawable easingDrawable;
        if (!(bg instanceof EasingDrawable)) {
            easingDrawable = new EasingDrawable(equation, 0xffEF5350);
            holder.view.setBackground(easingDrawable);
        } else {
            easingDrawable = (EasingDrawable) bg;
            easingDrawable.equation(equation);
        }

        easingDrawable.stop();

        holder.itemView.setOnClickListener(v -> {
            if (!easingDrawable.isRunning()) {
                easingDrawable.start();
            }
        });
    }

    static class Holder extends Item.Holder {

        final TextView name;
        final View view;

        Holder(@NonNull View itemView) {
            super(itemView);

            this.name = requireView(R.id.text);
            this.view = requireView(R.id.view);
        }
    }

    private static long createId(@NonNull Enum<? extends TweenEquation> equation) {
        // I have no idea why, but hashCodes are the same for the same ordinals (for different classes)
        return (equation.getClass().getName() + "." + equation.name()).hashCode();
    }
}
