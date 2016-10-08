package com.kvest.material_design_playground;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kvest.material_design_playground.expanding_list.ExpandableListActivity;
import com.kvest.material_design_playground.item_selection.ItemSelectionActivity;
import com.kvest.material_design_playground.vector_animation.VectorAnimationActivity;

/**
 * Created by user on 9/28/16.
 */
public class SamplesAdapter extends RecyclerView.Adapter<SamplesAdapter.ViewHolder> {
    private Item[] items = {
            new Item("Expandable list", ExpandableListActivity.class),
            new Item("Item selection", ItemSelectionActivity.class),
            new Item("Vector animation", VectorAnimationActivity.class),
    };
    private Context context;
    private final LayoutInflater layoutInflater;

    public SamplesAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView name = (TextView)layoutInflater.inflate(R.layout.samples_list_item, parent, false);

        return new ViewHolder(name);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items[position]);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    private void startActivity(Class<? extends AppCompatActivity> clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public ViewHolder(TextView itemView) {
            super(itemView);

            name = itemView;
        }

        public void bind(final Item item) {
            name.setText(item.name);
            name.setOnClickListener(view -> startActivity(item.clazz));
        }
    }

    private static class Item {
        String name;
        Class<? extends AppCompatActivity> clazz;

        public Item(String name, Class<? extends AppCompatActivity> clazz) {
            this.name = name;
            this.clazz = clazz;
        }
    }
}
