package io.noties.tumbleweed;

import androidx.annotation.NonNull;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import io.noties.tumbleweed.equations.Bounce;
import io.noties.tumbleweed.paths.Linear;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TweenTest {

    @Test
    public void is_actionable() {
        // `to` and `from` tween are actionable, `set`, `call`, `mark` are not

        final TweenType type = mock(TweenType.class);

        final Map<String, TweenDef> actionable = new HashMap<String, TweenDef>() {{
            put("to", Tween.to(new Object(), type));
            put("to-with-duration", Tween.to(new Object(), type, 123F));
            put("from", Tween.from(new Object(), type));
            put("from-with-duration", Tween.from(new Object(), type, 321F));
        }};

        final Map<String, TweenDef> notActionable = new HashMap<String, TweenDef>() {{
            put("call", Tween.call(mock(TweenCallback.class)));
            put("set", Tween.set(new Object(), type));
            put("mark", Tween.mark());
        }};

        for (Map.Entry<String, TweenDef> entry : actionable.entrySet()) {
            assertTrue(entry.getKey(), entry.getValue().isActionable());
        }

        for (Map.Entry<String, TweenDef> entry : notActionable.entrySet()) {
            assertFalse(entry.getKey(), entry.getValue().isActionable());
        }
    }

    @Test
    public void target_mismatch() {
        // when TweenType and supplied targets mismatch in length

        final TweenType type = mock(TweenType.class);
        when(type.getValuesSize()).thenReturn(2);

        try {
            Tween.to(new Object(), type)
                    .target(1, 2, 3);
            fail();
        } catch (Throwable t) {
            assertTrue(t.getMessage(), t.getMessage().contains("Target size mismatch"));
        }
    }

    @Test
    public void waypoint_target_mismatch() {

        final TweenType type = mock(TweenType.class);
        when(type.getValuesSize()).thenReturn(2);

        try {
            Tween.to(new Object(), type)
                    .waypoint(1, 2, 3);
            fail();
        } catch (Throwable t) {
            assertTrue(t.getMessage(), t.getMessage().contains("Target size mismatch"));
        }
    }

    @Test
    public void already_built() {
        // after tween has been built further mutations will throw
        // 3 cases when tween is built: directly call `build`, `start` and `start(manager)`

        final Map<String, TweenDef> map = new HashMap<String, TweenDef>() {{

            final TweenDef build = Tween.to(new Object(), mock(TweenType.class));
            build.build();

            final TweenDef start = Tween.to(new Object(), mock(TweenType.class));
            start.start();

            final TweenDef startWithManager = Tween.to(new Object(), mock(TweenType.class));
            startWithManager.start(mock(TweenManager.class));

            put("build", build);
            put("start", start);
            put("startWithManager", startWithManager);
        }};

        for (Map.Entry<String, TweenDef> entry : map.entrySet()) {

            final String who = entry.getKey();
            final TweenDef tweenDef = entry.getValue();

            assertStateThrows(stateName(who, "duration"), new Runnable() {
                @Override
                public void run() {
                    tweenDef.duration(1F);
                }
            });
            assertStateThrows(stateName(who, "ease"), new Runnable() {
                @Override
                public void run() {
                    tweenDef.ease(Bounce.INOUT);
                }
            });
            assertStateThrows(stateName(who, "path"), new Runnable() {
                @Override
                public void run() {
                    tweenDef.path(Linear.instance());
                }
            });
            assertStateThrows(stateName(who, "target(Object)"), new Runnable() {
                @Override
                public void run() {
                    //noinspection unchecked
                    tweenDef.target(new Object());
                }
            });
            assertStateThrows(stateName(who, "target(float...)"), new Runnable() {
                @Override
                public void run() {
                    // we can pass any targets, state is checked before length is validated
                    tweenDef.target(1, 2, 3);
                }
            });
            assertStateThrows(stateName(who, "waypoint(Object)"), new Runnable() {
                @Override
                public void run() {
                    //noinspection unchecked
                    tweenDef.waypoint(new Object());
                }
            });
            assertStateThrows(stateName(who, "waypoint(float...)"), new Runnable() {
                @Override
                public void run() {
                    tweenDef.waypoint(1, 2, 3);
                }
            });
            assertStateThrows(stateName(who, "scale"), new Runnable() {
                @Override
                public void run() {
                    tweenDef.scale(12F);
                }
            });
            assertStateThrows(stateName(who, "action"), new Runnable() {
                @Override
                public void run() {
                    //noinspection unchecked
                    tweenDef.action(mock(TweenAction.class));
                }
            });
            assertStateThrows(stateName(who, "delay"), new Runnable() {
                @Override
                public void run() {
                    tweenDef.delay(4F);
                }
            });
            assertStateThrows(stateName(who, "repeat"), new Runnable() {
                @Override
                public void run() {
                    tweenDef.repeat(12, 2);
                }
            });
            assertStateThrows(stateName(who, "repeatYoyo"), new Runnable() {
                @Override
                public void run() {
                    tweenDef.repeatYoyo(3, 98);
                }
            });
            assertStateThrows(stateName(who, "addCallback(TweenCallback)"), new Runnable() {
                @Override
                public void run() {
                    tweenDef.addCallback(mock(TweenCallback.class));
                }
            });
            assertStateThrows(stateName(who, "addCallback(int,TweenCallback)"), new Runnable() {
                @Override
                public void run() {
                    tweenDef.addCallback(12, mock(TweenCallback.class));
                }
            });
            assertStateThrows(stateName(who, "userData"), new Runnable() {
                @Override
                public void run() {
                    tweenDef.userData(false);
                }
            });
            assertStateThrows(stateName(who, "removeWhenFinished"), new Runnable() {
                @Override
                public void run() {
                    tweenDef.removeWhenFinished(true);
                }
            });
            assertStateThrows(stateName(who, "build"), new Runnable() {
                @Override
                public void run() {
                    tweenDef.build();
                }
            });
            assertStateThrows(stateName(who, "start"), new Runnable() {
                @Override
                public void run() {
                    tweenDef.start();
                }
            });
            assertStateThrows(stateName(who, "start(TweenManger)"), new Runnable() {
                @Override
                public void run() {
                    tweenDef.start(mock(TweenManager.class));
                }
            });
        }
    }

    @NonNull
    private static String stateName(@NonNull String who, @NonNull String method) {
        return who + "#" + method;
    }

    private static void assertStateThrows(@NonNull String name, @NonNull Runnable runnable) {
        try {
            runnable.run();
            fail(name);
        } catch (Throwable t) {
            assertTrue(name, t.getMessage().contains("TweenDef has already been built"));
        }
    }
}
