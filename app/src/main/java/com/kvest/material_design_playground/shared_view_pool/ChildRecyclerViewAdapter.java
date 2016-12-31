package com.kvest.material_design_playground.shared_view_pool;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kvest.material_design_playground.R;

/**
 * Created by kvest on 12/31/16.
 */

class ChildRecyclerViewAdapter extends RecyclerView.Adapter<ChildRecyclerViewAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private int base = -1;
    private int count = 0;

    public ChildRecyclerViewAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.child_recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(base, position);
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public void updateData(int base, int count) {
        this.base = base;
        this.count = count;

        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);

            Log.d("KVEST_TAG", "Create child");
        }

        public void bind(int base, int position) {
            ((TextView) itemView).setText(base + "-" + position);
        }
    }
}
