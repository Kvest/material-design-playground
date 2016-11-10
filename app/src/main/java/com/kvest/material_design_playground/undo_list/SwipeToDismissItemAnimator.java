package com.kvest.material_design_playground.undo_list;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;

/**
 * Created by kvest on 11/10/16.
 */
public class SwipeToDismissItemAnimator extends DefaultItemAnimator {
    @Override
    public boolean animateRemove(RecyclerView.ViewHolder holder) {
        //don't animate deletion
        dispatchRemoveFinished(holder);
        return false;
    }
}
