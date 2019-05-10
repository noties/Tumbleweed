package io.noties.tumbleweed;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * TweenCallbacks are used to trigger actions at some specific times. They are
 * used in both Tweens and Timelines. The moment when the addCallback is
 * triggered depends on its registered triggers:
 * <p/>
 * <p>
 * <b>BEGIN</b>: right after the delay (if any)<br/>
 * <b>START</b>: at each iteration beginning<br/>
 * <b>END</b>: at each iteration ending, before the repeat delay<br/>
 * <b>COMPLETE</b>: at last END event<br/>
 * <b>BACK_BEGIN</b>: at the beginning of the first backward iteration<br/>
 * <b>BACK_START</b>: at each backward iteration beginning, after the repeat delay<br/>
 * <b>BACK_END</b>: at each backward iteration ending<br/>
 * <b>BACK_COMPLETE</b>: at last BACK_END event
 * <p/>
 * <p>
 * <pre> {@code
 * forward :      BEGIN                                   COMPLETE
 * forward :      START    END      START    END      START    END
 * |--------------[XXXXXXXXXX]------[XXXXXXXXXX]------[XXXXXXXXXX]
 * backward:      bEND  bSTART      bEND  bSTART      bEND  bSTART
 * backward:      bCOMPLETE                                 bBEGIN
 * }</pre>
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 * @see Tween
 * @see Timeline
 */
public interface TweenCallback {

    @IntDef(value = {
            BEGIN,
            START,
            END,
            COMPLETE,
            BACK_BEGIN,
            BACK_START,
            BACK_END,
            BACK_COMPLETE,
            ANY_FORWARD,
            ANY_BACKWARD,
            ANY
    }, flag = true)
    @Retention(RetentionPolicy.CLASS)
    @interface Event {
    }

    int BEGIN = 1;
    int START = 1 << 1;
    int END = 1 << 2;
    int COMPLETE = 1 << 3;
    int BACK_BEGIN = 1 << 4;
    int BACK_START = 1 << 5;
    int BACK_END = 1 << 6;
    int BACK_COMPLETE = 1 << 7;
    int ANY_FORWARD = 0x0F;
    int ANY_BACKWARD = 0xF0;
    int ANY = 0xFF;

    void onEvent(@Event int type, @NonNull BaseTween source);
}
