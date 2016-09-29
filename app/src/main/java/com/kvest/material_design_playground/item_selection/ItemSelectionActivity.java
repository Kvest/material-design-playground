package com.kvest.material_design_playground.item_selection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kvest.material_design_playground.DividerDecoration;
import com.kvest.material_design_playground.R;

/**
 * Created by kvest on 9/29/16.
 */
public class ItemSelectionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item_selection_activity);

        init();
    }

    private void init() {
        RecyclerView cheesesList = (RecyclerView)findViewById(R.id.cheeses_list);
        cheesesList.setHasFixedSize(true);
        cheesesList.setLayoutManager(new LinearLayoutManager(this));
        cheesesList.setAdapter(new CheesesAdapter(this));

        //add divider for the list
        int dividerHeight = getResources().getDimensionPixelSize(R.dimen.samples_item_divider_height);
        int dividerColor = getResources().getColor(R.color.colorPrimary);
        cheesesList.addItemDecoration(new DividerDecoration(dividerHeight, dividerColor));
    }
}
