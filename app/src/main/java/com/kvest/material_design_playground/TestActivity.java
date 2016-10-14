package com.kvest.material_design_playground;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionValues;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by kvest on 10/5/16.
 */
public class TestActivity extends AppCompatActivity {

    private FrameLayout root;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);

        root = (FrameLayout) findViewById(R.id.root);

        findViewById(R.id.show).setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(root, new CustomTransition(findViewById(R.id.green)));
        });
    }

    private static boolean f;

    private class CustomTransition extends Transition {


        public CustomTransition(View tv) {
            addTarget(tv);
            setDuration(4000);

        }

        @Override
        public void captureStartValues(TransitionValues transitionValues) {
            Log.d("KVEST_TAG", "captureStartValues: " + transitionValues.view.getClass().getName());
            transitionValues.values.put("value",System.currentTimeMillis());
        }

        @Override
        public void captureEndValues(TransitionValues transitionValues) {
            Log.d("KVEST_TAG", "captureEndValues: " + transitionValues.view.getClass().getName());
            transitionValues.values.put("value",System.currentTimeMillis());
        }

        @Override
        public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
            Log.d("KVEST_TAG", "createAnimator: " + startValues.view.getClass().getName());
            Animator result;
            if (f) {
                result = ObjectAnimator.ofFloat(startValues.view, startValues.view.ROTATION, 0, 360).setDuration(1000);
            } else {
                result = ObjectAnimator.ofFloat(startValues.view, startValues.view.SCALE_X,1.0f, 0.5f, 1.0f).setDuration(1000);
            }
            result.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    Log.d("KVEST_TAG", "onAnimationStart: ");
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.d("KVEST_TAG", "onAnimationEnd: ");
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    Log.d("KVEST_TAG", "onAnimationCancel: ");
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    Log.d("KVEST_TAG", "onAnimationRepeat: ");
                }
            });

            f = !f;

            return result;

            //return endValues.view.animate().setDuration(4 * 1000).rotation(360).;
        }


    }
}
