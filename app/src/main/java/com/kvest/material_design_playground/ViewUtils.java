package com.kvest.material_design_playground;

import android.util.Property;
import android.view.View;

public final class ViewUtils {
    private ViewUtils() {}

    public static final Property<View, Integer> VIEW_TOP =
            new Property<View, Integer>(Integer.class, "view_top") {
                @Override
                public Integer get(View object) {
                    return object.getTop();
                }

                @Override
                public void set(View object, Integer value) {
                    object.setTop(value);
                }
            };
    public static final Property<View, Integer> VIEW_BOTTOM =
            new Property<View, Integer>(Integer.class, "view_bottom") {
                @Override
                public Integer get(View object) {
                    return object.getBottom();
                }

                @Override
                public void set(View object, Integer value) {
                    object.setBottom(value);
                }
            };
    public static final Property<View, Integer> VIEW_LEFT =
            new Property<View, Integer>(Integer.class, "view_left") {
                @Override
                public Integer get(View object) {
                    return object.getLeft();
                }

                @Override
                public void set(View object, Integer value) {
                    object.setLeft(value);
                }
            };
    public static final Property<View, Integer> VIEW_RIGHT =
            new Property<View, Integer>(Integer.class, "view_right") {
                @Override
                public Integer get(View object) {
                    return object.getRight();
                }

                @Override
                public void set(View object, Integer value) {
                    object.setRight(value);
                }
            };
}
