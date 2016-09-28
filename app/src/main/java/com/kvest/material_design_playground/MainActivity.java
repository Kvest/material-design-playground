package com.kvest.material_design_playground;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by user on 9/28/16.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        init();
    }

    private void init() {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.samples_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SamplesAdapter(this));

        //add divider for list
        int dividerHeight = getResources().getDimensionPixelSize(R.dimen.samples_item_divider_height);
        int dividerColor = getResources().getColor(R.color.colorPrimary);
        recyclerView.addItemDecoration(new DividerDecoration(dividerHeight, dividerColor));
    }
}
