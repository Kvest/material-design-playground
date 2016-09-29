package com.kvest.material_design_playground.expanding_list;

import android.support.annotation.DrawableRes;

/**
 * Created by kvest on 9/28/16.
 */
public class ExpandableListItem {
    final String name;
    final String description;
    final int imgResource;

    public ExpandableListItem(String name, String description, @DrawableRes int imgResource) {
        this.name = name;
        this.description = description;
        this.imgResource = imgResource;
    }
}
