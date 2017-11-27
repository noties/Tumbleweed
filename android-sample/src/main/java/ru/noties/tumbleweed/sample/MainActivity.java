package ru.noties.tumbleweed.sample;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IdRes;

import ru.noties.debug.AndroidLogDebugOutput;
import ru.noties.debug.Debug;

public class MainActivity extends Activity {

    static {
        Debug.init(new AndroidLogDebugOutput(true));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findFragment(R.id.container) == null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commit();
        }
    }

    private <F extends Fragment> F findFragment(@IdRes int id) {
        //noinspection unchecked
        return (F) getFragmentManager().findFragmentById(id);
    }
}
