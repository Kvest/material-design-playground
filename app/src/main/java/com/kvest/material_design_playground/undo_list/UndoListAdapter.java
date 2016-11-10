package com.kvest.material_design_playground.undo_list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kvest.material_design_playground.Cheeses;
import com.kvest.material_design_playground.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kvest on 9/29/16.
 */
public class UndoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AbstractItem> items;

    private final DeletedItem<CheeseItem> currentDeletedItem = new DeletedItem<>();
    private int currentDeletedItemPosition = -1;

    private final LayoutInflater inflater;

    public UndoListAdapter(Context context) {
        inflater = LayoutInflater.from(context);

        items = new ArrayList<>(Cheeses.CHEESE_STRINGS.length);
        for (int i = 0; i < Cheeses.CHEESE_STRINGS.length; i++) {
            items.add(new CheeseItem(Cheeses.CHEESE_STRINGS[i]));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case AbstractItem.TYPE_ITEM:
                View cheeseListItem = inflater.inflate(R.layout.cheese_list_item, parent, false);
                return new CheeseViewHolder(cheeseListItem);
            case AbstractItem.TYPE_DELETED_ITEM:
                View deletedListItem = inflater.inflate(R.layout.deleted_list_item, parent, false);
                return new DeletedItemViewHolder(deletedListItem);
        }

        throw new IllegalArgumentException("Unknown type " + viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case AbstractItem.TYPE_ITEM:
                ((CheeseViewHolder) holder).bind((CheeseItem) items.get(position));
        }
    }

    public void onItemDismiss(int position) {
        int deletedItemPosition = removeDeletedItem();
        if (deletedItemPosition != -1 && deletedItemPosition < position) {
            --position;
        }

        CheeseItem itemToDelete = ((CheeseItem) items.get(position));
        currentDeletedItem.setItem(itemToDelete);
        currentDeletedItemPosition = position;

        items.set(position, currentDeletedItem);
        notifyItemChanged(position);
    }

    public int removeDeletedItem() {
        int deletedItemPosition = currentDeletedItemPosition;
        if (currentDeletedItemPosition != -1) {
            items.remove(currentDeletedItemPosition);
            notifyItemRemoved(currentDeletedItemPosition);

            currentDeletedItem.setItem(null);
            currentDeletedItemPosition = -1;
        }

        return deletedItemPosition;
    }

    private void undoDeletion() {
        if (currentDeletedItemPosition != -1) {
            items.set(currentDeletedItemPosition, currentDeletedItem.item);
            notifyItemChanged(currentDeletedItemPosition);

            currentDeletedItem.setItem(null);
            currentDeletedItemPosition = -1;
        }
    }

    public boolean canSwipe(RecyclerView.ViewHolder viewHolder) {
        int position = viewHolder.getAdapterPosition();
        return (getItemViewType(position) != AbstractItem.TYPE_DELETED_ITEM);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private class CheeseViewHolder extends RecyclerView.ViewHolder {
        private TextView cheeseName;

        public CheeseViewHolder(View itemView) {
            super(itemView);

            cheeseName = (TextView)itemView.findViewById(R.id.cheese_name);
        }

        public void bind(CheeseItem cheeseItem) {
            this.cheeseName.setText(cheeseItem.cheeseName);
        }
    }

    private class DeletedItemViewHolder extends RecyclerView.ViewHolder {
        public DeletedItemViewHolder(View itemView) {
            super(itemView);

            itemView.findViewById(R.id.undo).setOnClickListener(v -> undoDeletion());
        }
    }
}
