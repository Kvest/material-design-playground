package com.kvest.material_design_playground.expanding_list;

import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by kvest on 10/9/16.
 */
public class ExpandItemAnimator extends DefaultItemAnimator {
    @Override
    public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
        //TODO do i need this method(check recreation of the holder without overriding this method)
        // This allows our custom change animation on the contents of the holder instead
        // of the default behavior of replacing the viewHolder entirely
        return true;

    }

    @Override
    public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder, @NonNull RecyclerView.ViewHolder newHolder, @NonNull ItemHolderInfo preInfo, @NonNull ItemHolderInfo postInfo) {
        final int fromLeft = preInfo.left;
        final int fromTop = preInfo.top;
        final int toLeft = postInfo.left;
        final int toTop = postInfo.top;

        Log.d("KVEST_TAG", String.format("animateChange: [%d]->[%d]", preInfo.bottom, postInfo.bottom));
        Log.d("KVEST_TAG", "animateChange: " + (newHolder == oldHolder));
        final View v = newHolder.itemView;
        ValueAnimator va = ValueAnimator.ofInt(preInfo.bottom, postInfo.bottom);
        va.addUpdateListener(animation -> {
            v.setBottom((Integer) animation.getAnimatedValue());
            Log.d("KVEST_TAG", "animateChange: " + animation.getAnimatedValue());
        });
        va.start();

        return super.animateChange(oldHolder, newHolder, preInfo, postInfo);
    }
}
