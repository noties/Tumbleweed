package io.noties.tumbleweed;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

abstract class TweenCallbacks {

    abstract void call(int type, @NonNull BaseTween source);

    @SuppressWarnings("UnusedReturnValue")
    static class Builder {

        private final List<Pair> list = new ArrayList<>(3);

        @NonNull
        Builder add(@NonNull TweenCallback callback) {
            list.add(new Pair(TweenCallback.ANY, callback));
            return this;
        }

        @NonNull
        Builder add(int type, @NonNull TweenCallback callback) {
            list.add(new Pair(type, callback));
            return this;
        }

        @NonNull
        TweenCallbacks build() {
            final TweenCallbacks callbacks;
            if (list.size() == 0) {
                callbacks = NO_OP;
            } else {
                callbacks = new Impl(list);
            }
            return callbacks;
        }
    }

    private static class Pair {

        final int type;
        final TweenCallback callback;

        Pair(int type, @NonNull TweenCallback callback) {
            this.type = type;
            this.callback = callback;
        }
    }

    private static final TweenCallbacks NO_OP = new TweenCallbacks() {
        @Override
        void call(int type, @NonNull BaseTween source) {

        }
    };

    private static class Impl extends TweenCallbacks {

        private final List<Pair> list;

        private Impl(@NonNull List<Pair> list) {
            this.list = list;
        }

        @Override
        void call(int type, @NonNull BaseTween source) {
            for (Pair pair : list) {
                if ((pair.type & type) > 0) {
                    pair.callback.onEvent(type, source);
                }
            }
        }
    }
}
