package ru.noties.tumbleweed.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import ru.noties.tumbleweed.sample.easing.EasingFragment;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager()
                .beginTransaction()
                .replace(Window.ID_ANDROID_CONTENT, EasingFragment.newInstance())
                .commit();
    }
}
