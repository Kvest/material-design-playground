package com.kvest.material_design_playground.expanding_list;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kvest on 10/9/16.
 */
public class ExpandItemAnimator extends DefaultItemAnimator {
    private final List<ExpandInfo> pendingExpands= new ArrayList<>();
    private final List<ExpandInfo> runningExpands= new ArrayList<>();

    @Override
    public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
        //TODO do i need this method(check recreation of the holder without overriding this method)
        // This allows our custom change animation on the contents of the holder instead
        // of the default behavior of replacing the viewHolder entirely
        return true;

    }

    @Override
    public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder, @NonNull RecyclerView.ViewHolder newHolder, @NonNull ItemHolderInfo preInfo, @NonNull ItemHolderInfo postInfo) {
        boolean result = super.animateChange(oldHolder, newHolder, preInfo, postInfo);

        if (!equalsItemHolderInfo(preInfo, postInfo)) {
            pendingExpands.add(new ExpandInfo(newHolder,
                                              preInfo.left, postInfo.left,
                                              preInfo.right, postInfo.right,
                                              preInfo.top, postInfo.top,
                                              preInfo.bottom, postInfo.bottom));
        }

        return result;
    }

    @Override
    public void runPendingAnimations() {
        super.runPendingAnimations();
        if (!pendingExpands.isEmpty()) {
            ArrayList<Animator> animations = new ArrayList<>(4);
            for (int i = pendingExpands.size() - 1; i >= 0; i--) {
                final ExpandInfo info = pendingExpands.remove(i);
                final View view = info.newViewHolder.itemView;

                //create sides animators
                if (info.fromTop != info.toTop) {
                    view.setTop(info.fromTop);

                    ValueAnimator animator = ValueAnimator.ofInt(info.fromTop, info.toTop);
                    animator.addUpdateListener(animation -> view.setTop((Integer) animation.getAnimatedValue()));
                    animations.add(animator);
                }
                if (info.fromRight != info.toRight) {
                    view.setRight(info.fromRight);

                    ValueAnimator animator = ValueAnimator.ofInt(info.fromRight, info.toRight);
                    animator.addUpdateListener(animation -> view.setRight((Integer) animation.getAnimatedValue()));
                    animations.add(animator);
                }
                if (info.fromBottom != info.toBottom) {
                    view.setBottom(info.fromBottom);

                    ValueAnimator animator = ValueAnimator.ofInt(info.fromBottom, info.toBottom);
                    animator.addUpdateListener(animation -> view.setBottom((Integer) animation.getAnimatedValue()));
                    animations.add(animator);
                }
                if (info.fromLeft != info.toLeft) {
                    view.setLeft(info.fromLeft);

                    ValueAnimator animator = ValueAnimator.ofInt(info.fromLeft, info.toLeft);
                    animator.addUpdateListener(animation -> view.setLeft((Integer) animation.getAnimatedValue()));
                    animations.add(animator);
                }

                AnimatorSet set = new AnimatorSet();
                set.playTogether(animations);
                set.setDuration(getMoveDuration());
                animations.clear();

                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        dispatchChangeStarting(info.newViewHolder, false);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animation.getListeners().remove(this);
                        dispatchChangeFinished(info.newViewHolder, false);
                        dispatchFinishedWhenDone();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        clearExpandAnimatedValues(info);
                    }
                });

                info.animator = set;
                runningExpands.add(info);
                set.start();

            }
        }
    }

    @Override
    public void endAnimation(RecyclerView.ViewHolder holder) {
        endChangeAnimation(pendingExpands, holder);
        endChangeAnimation(runningExpands, holder);

        super.endAnimation(holder);
    }

    @Override
    public void endAnimations() {
        for (int i = pendingExpands.size() - 1; i >= 0; i--) {
            ExpandInfo info = pendingExpands.remove(i);
            dispatchChangeFinished(info.newViewHolder, false);
        }
        for (int i = runningExpands.size() - 1; i >= 0; i--) {
            ExpandInfo info = runningExpands.remove(i);
            info.animator.cancel();
            info.animator = null;
            clearExpandAnimatedValues(info);
            dispatchChangeFinished(info.newViewHolder, false);
        }

        super.endAnimations();
    }

    private void endChangeAnimation(List<ExpandInfo> infoList, RecyclerView.ViewHolder item) {
        ExpandInfo info;
        for (int i = infoList.size() - 1; i >= 0; --i) {
            info = infoList.get(i);
            if (item == info.newViewHolder) {
                infoList.remove(i);
                if (info.animator != null) {
                    info.animator.cancel();
                    info.animator = null;
                }
                dispatchChangeFinished(info.newViewHolder, false);
            }
        }
    }

    @Override
    public boolean isRunning() {
        return !pendingExpands.isEmpty() || !runningExpands.isEmpty() || super.isRunning();
    }

    private void dispatchFinishedWhenDone() {
        if (!isRunning()) {
            dispatchAnimationsFinished();
        }
    }

    private void clearExpandAnimatedValues(final ExpandInfo info) {
        info.newViewHolder.itemView.setTop(info.toTop);
        info.newViewHolder.itemView.setRight(info.toRight);
        info.newViewHolder.itemView.setBottom(info.toBottom);
        info.newViewHolder.itemView.setLeft(info.toLeft);
    }

    private boolean equalsItemHolderInfo(@NonNull ItemHolderInfo preInfo, @NonNull ItemHolderInfo postInfo) {
        return preInfo.top == postInfo.top && preInfo.right == postInfo.right &&
               preInfo.bottom == postInfo.bottom && preInfo.left == postInfo.left;
    }

    private static class ExpandInfo {
        public final RecyclerView.ViewHolder newViewHolder;
        public Animator animator;
        public final int fromLeft;
        public final int toLeft;
        public final int fromRight;
        public final int toRight;
        public final int fromTop;
        public final int toTop;
        public final int fromBottom;
        public final int toBottom;

        public ExpandInfo(RecyclerView.ViewHolder newViewHolder, int fromLeft, int toLeft,
                          int fromRight, int toRight, int fromTop, int toTop, int fromBottom, int toBottom) {
            this.newViewHolder = newViewHolder;
            this.fromLeft = fromLeft;
            this.toLeft = toLeft;
            this.fromRight = fromRight;
            this.toRight = toRight;
            this.fromTop = fromTop;
            this.toTop = toTop;
            this.fromBottom = fromBottom;
            this.toBottom = toBottom;
        }
    }
}
