package io.noties.tumbleweed;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static io.noties.tumbleweed.TweenCallback.ANY;
import static io.noties.tumbleweed.TweenCallback.ANY_BACKWARD;
import static io.noties.tumbleweed.TweenCallback.ANY_FORWARD;
import static io.noties.tumbleweed.TweenCallback.BACK_BEGIN;
import static io.noties.tumbleweed.TweenCallback.BACK_COMPLETE;
import static io.noties.tumbleweed.TweenCallback.BACK_END;
import static io.noties.tumbleweed.TweenCallback.BACK_START;
import static io.noties.tumbleweed.TweenCallback.BEGIN;
import static io.noties.tumbleweed.TweenCallback.COMPLETE;
import static io.noties.tumbleweed.TweenCallback.END;
import static io.noties.tumbleweed.TweenCallback.START;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TweenCallbacksTest {

    @Test
    public void single_callbacks() {

        final TweenCallback begin = mock(TweenCallback.class, "begin");
        final TweenCallback start = mock(TweenCallback.class, "start");
        final TweenCallback end = mock(TweenCallback.class, "end");
        final TweenCallback complete = mock(TweenCallback.class, "complete");
        final TweenCallback backBegin = mock(TweenCallback.class, "backBegin");
        final TweenCallback backStart = mock(TweenCallback.class, "backStart");
        final TweenCallback backEnd = mock(TweenCallback.class, "backEnd");
        final TweenCallback backComplete = mock(TweenCallback.class, "backComplete");
        final TweenCallback anyForward = mock(TweenCallback.class, "anyForward");
        final TweenCallback anyBackward = mock(TweenCallback.class, "anyBackward");
        final TweenCallback any = mock(TweenCallback.class, "any");

        final TweenCallbacks callbacks = new TweenCallbacks.Builder()
                .add(BEGIN, begin)
                .add(START, start)
                .add(END, end)
                .add(COMPLETE, complete)
                .add(BACK_BEGIN, backBegin)
                .add(BACK_START, backStart)
                .add(BACK_END, backEnd)
                .add(BACK_COMPLETE, backComplete)
                .add(ANY_FORWARD, anyForward)
                .add(ANY_BACKWARD, anyBackward)
                .add(ANY, any)
                .build();

        // keep track of all (will exclude called to verify rest is not called)
        final Set<TweenCallback> set = new HashSet<>(Arrays.asList(
                begin, start, end, complete, backBegin, backStart, backEnd,
                backComplete, anyForward, anyBackward, any));


        final class Call {
            private void call(int event, TweenCallback... called) {

                assertTrue(called.length > 0);

                callbacks.call(event, mock(BaseTween.class));

                for (TweenCallback callback : called) {
                    verify(callback, times(1)).onEvent(eq(event), any(BaseTween.class));
                }

                final Set<TweenCallback> rest = new HashSet<>(set);
                rest.removeAll(Arrays.asList(called));

                for (TweenCallback callback : rest) {
                    verify(callback, times(0)).onEvent(eq(event), any(BaseTween.class));
                }
            }
        }
        final Call call = new Call();

        // 1
        call.call(BEGIN, begin, anyForward, any);

        // 2
        call.call(START, start, anyForward, any);

        // 4
        call.call(END, end, anyForward, any);

        // 8
        call.call(COMPLETE, complete, anyForward, any);

        // 16
        call.call(BACK_BEGIN, backBegin, anyBackward, any);

        // 32
        call.call(BACK_START, backStart, anyBackward, any);

        // 64
        call.call(BACK_END, backEnd, anyBackward, any);

        // 128
        call.call(BACK_COMPLETE, backComplete, anyBackward, any);

//        // special
//        call.call(ANY_FORWARD, anyForward, any);
//        call.call(ANY_BACKWARD, anyBackward, any);
//        call.call(ANY, any);
    }
}