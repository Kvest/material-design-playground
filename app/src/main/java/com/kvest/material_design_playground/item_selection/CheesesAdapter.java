package com.kvest.material_design_playground.item_selection;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kvest.material_design_playground.Cheeses;
import com.kvest.material_design_playground.R;

/**
 * Created by kvest on 9/29/16.
 */
public class CheesesAdapter extends RecyclerView.Adapter<CheesesAdapter.ViewHolder> {

    private final LayoutInflater inflater;

    public CheesesAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public CheesesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cheese_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CheesesAdapter.ViewHolder holder, int position) {
        holder.bind(Cheeses.CHEESE_STRINGS[position]);
    }

    @Override
    public int getItemCount() {
        return Cheeses.CHEESE_STRINGS.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView cheeseName;

        public ViewHolder(View itemView) {
            super(itemView);

            cheeseName = (TextView)itemView.findViewById(R.id.cheese_name);
        }

        public void bind(String cheeseName) {
            this.cheeseName.setText(cheeseName);
        }
    }
}
