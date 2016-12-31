package com.kvest.material_design_playground.shared_view_pool;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kvest.material_design_playground.R;

/**
 * Created by kvest on 12/31/16.
 */

class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private RecyclerView.RecycledViewPool pool;

    public MainRecyclerViewAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.main_recycler_view_item, parent, false);

        ViewHolder holder = new ViewHolder(view);
        pool = holder.setRecycledViewPool(pool);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(position, 10 + (position % 27));
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView;
        private ChildRecyclerViewAdapter adapter;

        public ViewHolder(View itemView) {
            super(itemView);

            Log.d("KVEST_TAG", "----------------------Create main----------------------");

            recyclerView = (RecyclerView)itemView;
            recyclerView.setHasFixedSize(true);

            LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            layoutManager.setRecycleChildrenOnDetach(true); //need to set this flag to allow the shared RecycledViewPool works correctly
            recyclerView.setLayoutManager(layoutManager);

            adapter = new ChildRecyclerViewAdapter(recyclerView.getContext());
            recyclerView.setAdapter(adapter);
        }

        public RecyclerView.RecycledViewPool setRecycledViewPool(RecyclerView.RecycledViewPool pool) {
            if (pool != null) {
                recyclerView.setRecycledViewPool(pool);
                return pool;
            }

            return recyclerView.getRecycledViewPool();
        }

        public void bind(int base, int count) {
            adapter.updateData(base, count);
        }
    }
}
