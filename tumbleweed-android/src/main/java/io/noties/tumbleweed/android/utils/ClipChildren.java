package io.noties.tumbleweed.android.utils;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

// hm... weird thing to do (but no surprising)
// I wonder if there is any way to obtain `clipChildren` value..
public abstract class ClipChildren {

    public interface State {
        void restore(@NonNull View view);
    }

    // returns null if not supported
    @Nullable
    public static State disable(@NonNull View view) {

        final State state;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {

            final SparseBooleanArray array = new SparseBooleanArray();

            ViewGroup group = initial(view);

            while (group != null) {
                array.put(group.hashCode(), group.getClipChildren());
                group.setClipChildren(false);
                group = next(group);
            }
            state = new StateImpl(array);
        } else {
            state = null;
        }

        return state;
    }

    private static class StateImpl implements State {

        private final SparseBooleanArray sparseBooleanArray;

        private StateImpl(@NonNull SparseBooleanArray sparseBooleanArray) {
            this.sparseBooleanArray = sparseBooleanArray;
        }

        @Override
        public void restore(@NonNull View view) {

            if (sparseBooleanArray.size() == 0) {
                return;
            }

            ViewGroup group = initial(view);
            int hash;
            while (group != null) {
                hash = group.hashCode();
                if (sparseBooleanArray.indexOfKey(hash) > -1) {
                    group.setClipChildren(sparseBooleanArray.get(hash));
                }
                group = next(group);
            }
        }
    }

    @Nullable
    private static ViewGroup initial(@NonNull View view) {
        return view instanceof ViewGroup
                ? (ViewGroup) view
                : (view.getParent() instanceof ViewGroup ? (ViewGroup) view.getParent() : null);
    }

    @Nullable
    private static ViewGroup next(@NonNull ViewGroup group) {
        return group.getParent() instanceof ViewGroup
                ? (ViewGroup) group.getParent()
                : null;
    }
}
