package com.kvest.material_design_playground.transitions;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.Transition;
import android.support.transition.TransitionValues;
import android.transition.ChangeBounds;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by kvest on 10/14/16.
 */
public class TextColorTransition extends Transition {
    private static final String PROPNAME_TEXT_COLOR = "kvest:textColorTransition:textColor";
    private static final String[] TRANSITION_PROPERTIES = {PROPNAME_TEXT_COLOR};

    @Nullable
    @Override
    public String[] getTransitionProperties() {
        return TRANSITION_PROPERTIES;
    }

    @Override
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        if (transitionValues.view instanceof TextView) {
            transitionValues.values.put(PROPNAME_TEXT_COLOR, ((TextView) transitionValues.view).getCurrentTextColor());
        }
    }

    @Override
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        if (transitionValues.view instanceof TextView) {
            transitionValues.values.put(PROPNAME_TEXT_COLOR, ((TextView) transitionValues.view).getCurrentTextColor());
        }
    }

    @Nullable
    @Override
    public Animator createAnimator(@NonNull ViewGroup sceneRoot, @Nullable TransitionValues startValues,
                                   @Nullable TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }

        final Integer startTextColor = (Integer)startValues.values.get(PROPNAME_TEXT_COLOR);
        final Integer endTextColor = (Integer)endValues.values.get(PROPNAME_TEXT_COLOR);
        final TextView textView = (TextView)endValues.view;
        final ArgbEvaluator argbEvaluator = new ArgbEvaluator();

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1.0f).setDuration(2000);
        animator.addUpdateListener(animation -> {
            int color = (Integer)argbEvaluator.evaluate(animation.getAnimatedFraction(), startTextColor, endTextColor);
            textView.setTextColor(color);
        });

        return animator;
    }
}
