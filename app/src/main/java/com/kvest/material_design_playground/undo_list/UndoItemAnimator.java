package com.kvest.material_design_playground.undo_list;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kvest on 11/10/16.
 */
public class UndoItemAnimator extends DefaultItemAnimator {
    private Interpolator undoInterpolator;
    private final List<RecyclerView.ViewHolder> pendingUndos = new ArrayList<>();

    public UndoItemAnimator() {
        super();

        setAddDuration(250L);
        undoInterpolator = new AccelerateInterpolator(2.0f);
    }

    @Override
    public boolean animateRemove(RecyclerView.ViewHolder holder) {
        //don't animate deletion at all
        dispatchRemoveFinished(holder);
        return false;
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        //set initial translation X
        holder.itemView.setTranslationX(-holder.itemView.getMeasuredWidth());

        //schedule UNDO animation
        pendingUndos.add(holder);
        return true;
    }

    @Override
    public void runPendingAnimations() {
        super.runPendingAnimations();
        if (!pendingUndos.isEmpty()) {
            //last item in the list is the freshest
            for (int i = pendingUndos.size() - 1; i >= 0; i--) {
                final RecyclerView.ViewHolder holder = pendingUndos.remove(i);
                holder.itemView.animate()
                        .translationX(0f)
                        .setDuration(getAddDuration())
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                dispatchAddStarting(holder);
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                animation.getListeners().remove(this);
                                dispatchAddFinished(holder);
                                dispatchFinishedWhenDone();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                                clearAddAnimatedValues(holder.itemView);
                            }
                        })
                        .setInterpolator(undoInterpolator);
            }
        }
    }

    @Override
    public void endAnimation(RecyclerView.ViewHolder holder) {
        holder.itemView.animate().cancel();
        if (pendingUndos.remove(holder)) {
            dispatchAddFinished(holder);
            clearAddAnimatedValues(holder.itemView);
        }
        super.endAnimation(holder);
    }

    @Override
    public void endAnimations() {
        for (int i = pendingUndos.size() - 1; i >= 0; i--) {
            final RecyclerView.ViewHolder holder = pendingUndos.remove(i);
            clearAddAnimatedValues(holder.itemView);
            dispatchAddFinished(holder);
        }
        super.endAnimations();
    }

    @Override
    public boolean isRunning() {
        return !pendingUndos.isEmpty() || super.isRunning();
    }

    private void dispatchFinishedWhenDone() {
        if (!isRunning()) {
            dispatchAnimationsFinished();
        }
    }

    private void clearAddAnimatedValues(final View view) {
        view.setTranslationX(0f);
    }
}
