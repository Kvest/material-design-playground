package com.kvest.material_design_playground.expanding_list;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kvest on 10/2/16.
 */
public class ExpandListTransition extends Transition {
    private static final String CHILDREN_BOUNDS = "kvest:expandListTransition:childrenBounds";

    public ExpandListTransition() {
        super();
    }

    public ExpandListTransition(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public String[] getTransitionProperties() {
        //TODO
        return null;
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }

        Map<View, int[]> oldChildrenBounds = (Map<View, int[]>)startValues.values.get(CHILDREN_BOUNDS);
        Map<View, int[]> newChildrenBounds = (Map<View, int[]>)endValues.values.get(CHILDREN_BOUNDS);

        Log.d("KVEST_TAG", "old=" + oldChildrenBounds.size() + ", new=" + newChildrenBounds.size());

//        if (start.equals(end)) {
//            Log.d("KVEST_TAG", "createAnimator: " + endValues != null ? endValues.view.getClass().getName() : "null");
//        }
        return super.createAnimator(sceneRoot, startValues, endValues);
    }

    private void captureValues(TransitionValues transitionValues) {
        if (!(transitionValues.view instanceof RecyclerView)) {
            return;
        }

        RecyclerView view = (RecyclerView)transitionValues.view;

        if (view.isLaidOut() || view.getWidth() != 0 || view.getHeight() != 0) {
            int childCount = view.getChildCount();
            Map<View, int[]> childrenBounds = new HashMap<>(childCount);
            for (int i = 0; i < childCount; i++) {
                View v = view.getChildAt(i);

                //TODO
                //v.setHasTransientState(true);

                childrenBounds.put(v, new int[]{v.getLeft(), v.getTop(), v.getRight(), v.getBottom()});
            }

            transitionValues.values.put(CHILDREN_BOUNDS, childrenBounds);
        }
    }
}
