package com.kvest.material_design_playground.undo_list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.kvest.material_design_playground.R;

/**
 * Created by kvest on 11/9/16.
 */
public class UndoListActivity extends AppCompatActivity {
    private UndoListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.undo_list_activity);

        init();
    }

    private void init() {
        RecyclerView list = (RecyclerView)findViewById(R.id.list);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UndoListAdapter(this);
        list.setAdapter(adapter);
        list.setItemAnimator(new SwipeToDismissItemAnimator());
        list.addItemDecoration(new DividerItemDecoration(list.getContext(), LinearLayoutManager.VERTICAL));

        //setup "swipe to dismiss"
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        //Nothing to do
                        return false;
                    }

                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        adapter.onItemDismiss(viewHolder.getAdapterPosition());
                        viewHolder.itemView.setTranslationX(0);
                    }

                    @Override
                    public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                        if (adapter.canSwipe(viewHolder)) {
                            return super.getSwipeDirs(recyclerView, viewHolder);
                        } else {
                            return 0;
                        }
                    }
                });
        itemTouchHelper.attachToRecyclerView(list);

        //hide "undo" when user starts to scroll the list
        list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    adapter.removeDeletedItem();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {}
        });

    }
}
