package com.kvest.material_design_playground.undo_list;

/**
 * Created by kvest on 11/9/16.
 */
public abstract class AbstractItem {
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_DELETED_ITEM = 1;

    public final int type;

    public AbstractItem(int type) {
        this.type = type;
    }
}
