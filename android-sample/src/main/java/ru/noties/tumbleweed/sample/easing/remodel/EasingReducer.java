package ru.noties.tumbleweed.sample.easing.remodel;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.noties.remodel.Reducer;
import ru.noties.remodel.result.ResultList;
import ru.noties.remodel.result.SyncResultList;
import ru.noties.tumbleweed.TweenEquation;
import ru.noties.tumbleweed.equations.Back;
import ru.noties.tumbleweed.equations.Bounce;
import ru.noties.tumbleweed.equations.Circ;
import ru.noties.tumbleweed.equations.Cubic;
import ru.noties.tumbleweed.equations.Elastic;
import ru.noties.tumbleweed.equations.Expo;
import ru.noties.tumbleweed.equations.Quad;
import ru.noties.tumbleweed.equations.Quart;
import ru.noties.tumbleweed.equations.Quint;
import ru.noties.tumbleweed.equations.Sine;

public class EasingReducer implements Reducer<EasingModel, EasingItem> {
    @NonNull
    @Override
    public ResultList<EasingItem> reduce(@NonNull EasingModel model) {

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

        return SyncResultList.create(list);
    }

    private static void add(@NonNull String name, @NonNull List<EasingItem> list, @NonNull Enum<? extends TweenEquation>[] equations) {
        list.add(new EasingItem.Header(name));
        for (Enum<? extends TweenEquation> equation : equations) {
            list.add(new EasingItem.Easing(equation));
        }
    }
}
