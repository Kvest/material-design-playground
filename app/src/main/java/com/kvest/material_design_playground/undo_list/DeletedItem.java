package com.kvest.material_design_playground.undo_list;

/**
 * Created by kvest on 11/9/16.
 */
public class DeletedItem<T> extends AbstractItem {
    public T item;

    public DeletedItem() {
        super(TYPE_DELETED_ITEM);
    }

    public void setItem(T item) {
        this.item = item;
    }
}
