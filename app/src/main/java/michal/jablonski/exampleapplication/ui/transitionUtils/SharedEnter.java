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

public class SharedEnter extends ChangeBounds {

    private final String PROPERTY_COLOR = "circleMorph:color";
    private final String[] TRANSITION_PROPERTIES = {
            PROPERTY_COLOR
    };
    private final int DURATION = 300;
    private final int DURATION_AND_DELAY = 150;
    private final float OFFSET = 1.8f;

    public SharedEnter() {
        super();
    }

    public SharedEnter(Context context, AttributeSet attrs) {
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
        transitionValues.values.put(PROPERTY_COLOR, ContextCompat.getColor(view.getContext(), android.R.color.transparent));
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        final View view = transitionValues.view;
        if (view.getWidth() <= 0 || view.getHeight() <= 0) {
            return;
        }
        transitionValues.values.put(PROPERTY_COLOR, ContextCompat.getColor(view.getContext(), R.color.dialogBackground));
    }

    @Override
    public Animator createAnimator(final ViewGroup sceneRoot, TransitionValues startValues, final TransitionValues endValues) {
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

        // ease in the dialog's child views (slide up & fade in)
        if (endValues.view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) endValues.view;
            float offset = vg.getHeight() / 3;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View v = vg.getChildAt(i);
                v.setTranslationY(offset);
                v.setAlpha(0f);
                v.animate().alpha(1f).translationY(0f).setDuration(DURATION_AND_DELAY).setStartDelay(DURATION_AND_DELAY)
                        .setInterpolator(AnimationUtils.loadInterpolator(vg.getContext(), android.R.interpolator.fast_out_slow_in))
                        .start();
                offset *= OFFSET;
            }
        }

        AnimatorSet transition = new AnimatorSet();
        transition.playTogether(changeBounds, color);
        transition.setDuration(DURATION);
        transition.setInterpolator(AnimationUtils.loadInterpolator(sceneRoot.getContext(), android.R.interpolator.fast_out_slow_in));
        return transition;
    }

}
