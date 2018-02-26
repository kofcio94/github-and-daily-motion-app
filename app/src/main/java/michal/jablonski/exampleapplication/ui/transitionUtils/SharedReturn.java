package michal.jablonski.exampleapplication.ui.transitionUtils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.transition.ChangeBounds;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import michal.jablonski.exampleapplication.R;

public class SharedReturn extends ChangeBounds {

    private final String PROPERTY_COLOR = "rectMorph:color";
    private final String[] TRANSITION_PROPERTIES = {
            PROPERTY_COLOR
    };
    private final int DURATION = 300;
    private final long DELAY = 0L;
    private final long DELAY_DURATION = 50L;

    public SharedReturn() {
        super();
    }

    public SharedReturn(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public String[] getTransitionProperties() {
        return TRANSITION_PROPERTIES;
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        final View view = transitionValues.view;
        if (view.getWidth() <= 0 || view.getHeight() <= 0) {
            return;
        }
        transitionValues.values.put(PROPERTY_COLOR, ContextCompat.getColor(view.getContext(), R.color.dialogBackground));
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        final View view = transitionValues.view;
        if (view.getWidth() <= 0 || view.getHeight() <= 0) {
            return;
        }
        transitionValues.values.put(PROPERTY_COLOR, ContextCompat.getColor(view.getContext(), android.R.color.transparent));
    }

    @Override
    public Animator createAnimator(final ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        Animator changeBounds = super.createAnimator(sceneRoot, startValues, endValues);
        if (startValues == null || endValues == null || changeBounds == null) {
            return null;
        }

        Integer startColor = (Integer) startValues.values.get(PROPERTY_COLOR);
        Integer endColor = (Integer) endValues.values.get(PROPERTY_COLOR);

        if (startColor == null || endColor == null) {
            return null;
        }

        AnimDrawable background = new AnimDrawable(startColor);
        endValues.view.setBackground(background);

        Animator color = ObjectAnimator.ofArgb(background, AnimDrawable.COLOR, endColor);

        if (endValues.view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) endValues.view;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View v = vg.getChildAt(i);
                v.animate().alpha(0f).translationY(v.getHeight() / 3).setStartDelay(DELAY).setDuration(DELAY_DURATION)
                        .setInterpolator(AnimationUtils.loadInterpolator(vg.getContext(), android.R.interpolator.fast_out_linear_in))
                        .start();
            }
        }

        AnimatorSet transition = new AnimatorSet();
        transition.playTogether(changeBounds, color);
        transition.setInterpolator(AnimationUtils.loadInterpolator(sceneRoot.getContext(), android.R.interpolator.fast_out_slow_in));
        transition.setDuration(DURATION);
        return transition;
    }
}
