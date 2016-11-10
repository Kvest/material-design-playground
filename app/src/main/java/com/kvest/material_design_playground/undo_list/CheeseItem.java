package com.kvest.material_design_playground.undo_list;

/**
 * Created by kvest on 11/9/16.
 */
public class CheeseItem extends AbstractItem {
    public final String cheeseName;

    public CheeseItem(String cheeseName) {
        super(TYPE_ITEM);

        this.cheeseName = cheeseName;
    }
}
