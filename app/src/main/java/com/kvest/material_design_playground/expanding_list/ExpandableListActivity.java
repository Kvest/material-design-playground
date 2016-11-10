package com.kvest.material_design_playground.expanding_list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kvest.material_design_playground.R;

/**
 * Created by kvest on 9/28/16.
 */
public class ExpandableListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expandable_list_activity);

        RecyclerView expandableList = (RecyclerView) findViewById(R.id.expandable_list);
        expandableList.setHasFixedSize(true);
        expandableList.setLayoutManager(new LinearLayoutManager(this));
        expandableList.setAdapter(new ExpandingListAdapter(this));
        expandableList.setItemAnimator(new ExpandItemAnimator());
        expandableList.addItemDecoration(new DividerItemDecoration(expandableList.getContext(), LinearLayoutManager.VERTICAL));
    }
}
