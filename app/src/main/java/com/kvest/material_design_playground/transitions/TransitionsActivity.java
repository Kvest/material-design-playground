package com.kvest.material_design_playground.transitions;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import petrov.kristiyan.colorpicker.ColorPicker;

import com.kvest.material_design_playground.R;
import com.kvest.material_design_playground.RingView;

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
        findViewById(R.id.change_color).setOnClickListener(v -> selectColor());

        initItems();
    }

    private void initItems() {
        final View chameleon = findViewById(R.id.chameleon);
        ((TextView) chameleon.findViewById(R.id.name)).setText("Chameleon");
        chameleon.setOnClickListener(v -> showItemActivity("Chameleon", R.drawable.chameleon, chameleon));

        final View rock = findViewById(R.id.rock);
        ((TextView) rock.findViewById(R.id.name)).setText("Rock");
        rock.setOnClickListener(v -> showItemActivity("Rock", R.drawable.rock, rock));

        final View flower = findViewById(R.id.flower);
        ((TextView) flower.findViewById(R.id.name)).setText("Flower");
        flower.setOnClickListener(v -> showItemActivity("Flower", R.drawable.flower, flower));
    }

    private void showItemActivity(String name, @DrawableRes int imageId, View itemRoot) {
        ItemActivity.start(this, name, imageId, (TextView)itemRoot.findViewById(R.id.name),
                                                (RingView) itemRoot.findViewById(R.id.marker));
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(root, new TextColorTransition());
        }

        target.setTextColor(color);
    }
}
