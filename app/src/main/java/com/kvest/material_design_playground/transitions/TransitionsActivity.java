package com.kvest.material_design_playground.transitions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kvest.material_design_playground.R;

import petrov.kristiyan.colorpicker.ColorPicker;

/**
 * Created by kvest on 10/13/16.
 */
public class TransitionsActivity extends AppCompatActivity {
    private ViewGroup root;
    private TextView target;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transitions_activity);

        root = (ViewGroup)findViewById(R.id.root);
        target = (TextView)findViewById(R.id.target);
        findViewById(R.id.change_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectColor();
            }
        });
    }

    private void selectColor() {
        ColorPicker colorPicker = new ColorPicker(this);
        colorPicker.setRoundColorButton(true);
        colorPicker.show();
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position,int color) {
                changeColor(color);
            }

            @Override
            public void onCancel(){}
        });
    }

    private void changeColor(int color) {
        TransitionManager.beginDelayedTransition(root, new TextColorTransition());
        target.setTextColor(color);
    }
}
