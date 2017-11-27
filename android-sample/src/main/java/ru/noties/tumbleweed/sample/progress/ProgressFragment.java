package ru.noties.tumbleweed.sample.progress;

import android.animation.Animator;
import android.app.Fragment;
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

import ru.noties.debug.Debug;
import ru.noties.tumbleweed.Timeline;
import ru.noties.tumbleweed.Tween;
import ru.noties.tumbleweed.TweenManager;
import ru.noties.tumbleweed.android.ViewTweenManager;
import ru.noties.tumbleweed.android.types.Alpha;
import ru.noties.tumbleweed.android.types.Rotation;
import ru.noties.tumbleweed.android.types.Scale;
import ru.noties.tumbleweed.android.types.Translation;
import ru.noties.tumbleweed.android.utils.ViewUtils;
import ru.noties.tumbleweed.equations.Cubic;
import ru.noties.tumbleweed.equations.Elastic;
import ru.noties.tumbleweed.equations.Quint;
import ru.noties.tumbleweed.sample.R;

public class ProgressFragment extends Fragment {

    private static final int ROWS = 3;
    private static final int COLUMNS = 3;

    public static ProgressFragment newInstance() {
        final Bundle bundle = new Bundle();

        final ProgressFragment fragment = new ProgressFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private ViewGroup root;

    private List<View> children;

    private TweenManager shuffleTweenManager;

    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        Debug.i();
        return super.onCreateAnimator(transit, enter, nextAnim);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress, parent, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        root = view.findViewById(R.id.progress_root);

        shuffleTweenManager = ViewTweenManager.create(root);

        view.findViewById(R.id.app_bar_shuffle)
                .setOnClickListener(this::shuffle);

        final Drawable[] indicators = createIndicators();

        Drawable indicator;
        View child;

        children = new ArrayList<>(9);

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

        root.setOnTouchListener(new View.OnTouchListener() {

            private final RectF rectF = new RectF();

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {

                    final float x = event.getX();
                    final float y = event.getY();

                    for (View child : children) {

                        final int w = child.getWidth();
                        final int h = child.getHeight();

                        final Point point = ViewUtils.relativeTo(root, child);

                        rectF.left = point.x + child.getTranslationX();
                        rectF.right = rectF.left + w;
                        rectF.top = point.y + child.getTranslationY();
                        rectF.bottom = rectF.top + h;

                        if (rectF.contains(x, y)) {

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
                            break;
                        }
                    }

                    return true;
                }

                return true;
            }
        });

        final TextView title = view.findViewById(R.id.app_bar_title);
        final TweenManager manager = ViewTweenManager.create(title);

        Timeline.createParallel()
                .pushPause(2.F)
                .push(Tween.to(title, Rotation.I, 1.F).waypoint(-15).waypoint(15).target(0).ease(Quint.INOUT))
                .push(Tween.to(title, Alpha.VIEW, 1.F).waypoint(.5F).target(1.F))
                .repeat(-1, 2)
                .start(manager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        children.clear();
    }

    private Drawable[] createIndicators() {

        final int padding = getResources().getDimensionPixelSize(R.dimen.progress_padding);

        final SquareDrawable[] indicators = new SquareDrawable[ROWS * COLUMNS];

        indicators[index(0, 0)] = new MaterialProgressDrawable(new int[]{0xffEF5350, 0xff42A5F5, 0xff66BB6A, 0xffFFA726}, 20);
        indicators[index(0, 1)] = new SpinnerProgressDrawable(new int[]{0xffEF5350, 0xffEC407A, 0xffAB47BC, 0xff7E57C2, 0xff5C6BC0, 0xff42A5F5, 0xff29B6F6, 0xff26C6DA});
        indicators[index(0, 2)] = new PacmanProgressDrawable(new int[]{0xffEF5350, 0xff42A5F5, 0xff66BB6A, 0xffFFA726});

        indicators[index(1, 0)] = new BallDrawable(new int[]{0xff7E57C2, 0xff9CCC65, 0xffFF7043, 0xffEC407A});
        indicators[index(1, 1)] = new TriangleDrawable(new int[]{0xffEF5350, 0xffEC407A, 0xffAB47BC});
        indicators[index(1, 2)] = new MicDrawable(new int[]{0xffEF5350, 0xffEC407A, 0xffAB47BC});

        indicators[index(2, 0)] = new MaterialProgressDrawable(new int[]{0xffEF5350, 0xff42A5F5, 0xff66BB6A, 0xffFFA726}, 20);
        indicators[index(2, 1)] = new SpinnerProgressDrawable(new int[]{0xffEF5350, 0xffEC407A, 0xffAB47BC, 0xff7E57C2, 0xff5C6BC0, 0xff42A5F5, 0xff29B6F6, 0xff26C6DA});
        indicators[index(2, 2)] = new PacmanProgressDrawable(new int[]{0xffEF5350, 0xff42A5F5, 0xff66BB6A, 0xffFFA726});


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

        final List<Point> positions = new ArrayList<>(9);
        for (View view : children) {
            positions.add(ViewUtils.relativeTo(root, view, new Point()));
        }

        Collections.shuffle(positions);

        final Point point = new Point();

        View view;
        Point position;

        for (int i = 0; i < 9; i++) {

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
}
