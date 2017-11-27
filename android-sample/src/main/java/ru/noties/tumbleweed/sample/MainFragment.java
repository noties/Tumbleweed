package ru.noties.tumbleweed.sample;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.noties.tumbleweed.Timeline;
import ru.noties.tumbleweed.Tween;
import ru.noties.tumbleweed.TweenManager;
import ru.noties.tumbleweed.android.ViewTweenManager;
import ru.noties.tumbleweed.android.types.Alpha;
import ru.noties.tumbleweed.android.types.Rotation;
import ru.noties.tumbleweed.android.types.Scale;
import ru.noties.tumbleweed.android.types.Translation;
import ru.noties.tumbleweed.android.utils.ViewUtils;
import ru.noties.tumbleweed.equations.Bounce;
import ru.noties.tumbleweed.equations.Cubic;
import ru.noties.tumbleweed.equations.Elastic;
import ru.noties.tumbleweed.equations.Quint;
import ru.noties.tumbleweed.sample.drawable.BallDrawable;
import ru.noties.tumbleweed.sample.drawable.ClockDrawable;
import ru.noties.tumbleweed.sample.drawable.MaterialDrawable;
import ru.noties.tumbleweed.sample.drawable.MicDrawable;
import ru.noties.tumbleweed.sample.drawable.NewtonDrawable;
import ru.noties.tumbleweed.sample.drawable.PacmanDrawable;
import ru.noties.tumbleweed.sample.drawable.SpinnerDrawable;
import ru.noties.tumbleweed.sample.drawable.SquareDrawable;
import ru.noties.tumbleweed.sample.drawable.SquareSpinDrawable;
import ru.noties.tumbleweed.sample.drawable.TriangleDrawable;

public class MainFragment extends Fragment {

    private static final int ROWS = 3;
    private static final int COLUMNS = 3;
    private static final int COUNT = ROWS * COLUMNS;

    public static MainFragment newInstance() {
        final Bundle bundle = new Bundle();

        final MainFragment fragment = new MainFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private ViewGroup root;

    private List<View> children;

    private TweenManager shuffleTweenManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress, parent, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        root = view.findViewById(R.id.progress_root);

        shuffleTweenManager = ViewTweenManager.create(root);

        final View shuffle = view.findViewById(R.id.app_bar_shuffle);
        shuffle.setOnClickListener(this::shuffle);
        shuffle.setOnLongClickListener(this::restore);

        final Drawable[] indicators = createIndicators();

        Drawable indicator;
        View child;

        children = new ArrayList<>(COUNT);

        for (int row = 0; row < ROWS; row++) {
            final ViewGroup group = (ViewGroup) root.getChildAt(row);
            for (int column = 0; column < COLUMNS; column++) {
                indicator = indicators[index(row, column)];
                child = group.getChildAt(column);
                children.add(child);
                if (indicator != null) {
                    child.setBackground(indicator);
                    if (indicator instanceof Animatable) {
                        final Animatable animatable = (Animatable) indicator;
                        animatable.start();
                    }
                }
            }
        }

        root.setOnTouchListener(new ToggleBackgroundAnimationTouchListener(children));

        final TextView title = view.findViewById(R.id.app_bar_title);

        Timeline.createParallel()
                .pushPause(2.F)
                .push(Tween.to(title, Rotation.I, 1.F).waypoint(-15).waypoint(15).target(0).ease(Quint.INOUT))
                .push(Tween.to(title, Alpha.VIEW, 1.F).waypoint(.5F).target(1.F))
                .repeat(-1, 2)
                .start(ViewTweenManager.create(title));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        children.clear();
    }

    private Drawable[] createIndicators() {

        final Resources r = getResources();

        final int padding = getResources().getDimensionPixelSize(R.dimen.progress_padding);

        final int red;
        final int pink;
        final int purple;
        final int deepPurple;
        final int indigo;
        final int blue;
        final int lightBlue;
        final int cyan;
        final int teal;
        final int green;
        final int lightGreen;
        final int lime;
        final int yellow;
        final int amber;
        final int orange;
        final int deepOrange;
        final int brown;
        {
            red = r.getColor(R.color.red_400);
            pink = r.getColor(R.color.pink_400);
            purple = r.getColor(R.color.purple_400);
            deepPurple = r.getColor(R.color.deep_purple_400);
            indigo = r.getColor(R.color.indigo_400);
            blue = r.getColor(R.color.blue_400);
            lightBlue = r.getColor(R.color.light_blue_400);
            cyan = r.getColor(R.color.cyan_400);
            teal = r.getColor(R.color.teal_400);
            green = r.getColor(R.color.green_400);
            lightGreen = r.getColor(R.color.light_green_400);
            lime = r.getColor(R.color.lime_400);
            yellow = r.getColor(R.color.yellow_400);
            amber = r.getColor(R.color.amber_400);
            orange = r.getColor(R.color.orange_400);
            deepOrange = r.getColor(R.color.deep_orange_400);
            brown = r.getColor(R.color.brown_400);
        }

        final SquareDrawable[] indicators = new SquareDrawable[COUNT];

        indicators[index(0, 0)] = new MaterialDrawable(new int[]{red, blue, green, orange}, padding);
        indicators[index(0, 1)] = new SpinnerDrawable(new int[]{red, pink, purple, deepPurple, indigo, blue, lightBlue, cyan});
        indicators[index(0, 2)] = new PacmanDrawable(new int[]{red, blue, green, orange});

        indicators[index(1, 0)] = new BallDrawable(new int[]{deepPurple, lightGreen, deepOrange, pink});
        indicators[index(1, 1)] = new TriangleDrawable(new int[]{red, pink, purple});
        indicators[index(1, 2)] = new MicDrawable(new int[]{yellow, amber, orange});

        indicators[index(2, 0)] = new NewtonDrawable(new int[]{red, blue, green, orange});
        indicators[index(2, 1)] = new SquareSpinDrawable(new int[]{teal, lime, brown, deepPurple});
        indicators[index(2, 2)] = new ClockDrawable(padding, red, blue, green);


        for (SquareDrawable indicator : indicators) {
            if (indicator != null) {
                indicator.setPadding(padding);
            }
        }

        return indicators;
    }

    private int index(int row, int column) {
        return ROWS * row + column;
    }

    private void shuffle(@NonNull View clicked) {

        shuffleTweenManager.killAll();

        final List<Point> positions = new ArrayList<>(COUNT);
        for (View view : children) {
            positions.add(ViewUtils.relativeTo(root, view, new Point()));
        }

        Collections.shuffle(positions);

        final Point point = new Point();

        View view;
        Point position;

        for (int i = 0; i < COUNT; i++) {

            view = children.get(i);
            position = positions.get(i);

            ViewUtils.relativeTo(root, view, point);

            Tween.to(view, Translation.XY, 1.5F)
                    .target(position.x - point.x, position.y - point.y)
                    .ease(Elastic.INOUT)
                    .removeWhenFinished(true)
                    .start(shuffleTweenManager);

            point.set(0, 0);
        }

        Timeline.createParallel()
                .push(Tween.to(clicked, Scale.XY, .5F).target(.5F, .5F).ease(Elastic.INOUT))
                .push(Tween.to(clicked, Rotation.I, .5F).target(90).ease(Cubic.INOUT))
                .repeatYoyo(1, 0)
                .start(shuffleTweenManager);
    }

    private boolean restore(View clicked) {

        shuffleTweenManager.killAll();

        for (View view : children) {
            Tween.to(view, Translation.XY, 1.5F)
                    .target(0, 0)
                    .ease(Elastic.INOUT)
                    .removeWhenFinished(true)
                    .start(shuffleTweenManager);
        }

        Timeline.createSequence()
                .push(Tween.to(clicked, Scale.XY, .25F).target(1.5F, 1.5F))
                .push(Tween.to(clicked, Scale.XY, .25F).target(1.F, 1.F).ease(Bounce.OUT))
                .start(shuffleTweenManager);

        return true;
    }

    private static class ToggleBackgroundAnimationTouchListener implements View.OnTouchListener {

        private final RectF rectF = new RectF();
        private final List<View> children;

        private float startX;
        private float startY;

        ToggleBackgroundAnimationTouchListener(@NonNull List<View> children) {
            this.children = children;
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    startX = event.getX();
                    startY = event.getY();
                    break;

                case MotionEvent.ACTION_UP:
                    onUpAction(v, event);
                    break;
            }

            return true;
        }

        private void onUpAction(View v, MotionEvent event) {

            final float x = event.getX();
            final float y = event.getY();

            for (View child : children) {

                final int w = child.getWidth();
                final int h = child.getHeight();

                final Point point = ViewUtils.relativeTo(v, child);

                rectF.left = point.x + child.getTranslationX();
                rectF.right = rectF.left + w;
                rectF.top = point.y + child.getTranslationY();
                rectF.bottom = rectF.top + h;

                if (rectF.contains(x, y)) {

                    // additional check if touch started with this view, if not -> no click
                    if (rectF.contains(startX, startY)) {

                        child.playSoundEffect(0);

                        final Drawable drawable = child.getBackground();
                        if (drawable instanceof Animatable) {
                            final Animatable animatable = (Animatable) drawable;
                            if (animatable.isRunning()) {
                                animatable.stop();
                            } else {
                                animatable.start();
                            }
                        }
                    }

                    break;
                }
            }
        }
    }
}
