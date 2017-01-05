package com.kvest.material_design_playground.vector_animation;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.kvest.material_design_playground.R;

/**
 * Created by kvest on 10/8/16.
 */
public class VectorAnimationActivity extends AppCompatActivity {
    private ImageView image;
    private boolean isChecked;
    private ImageView searchback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Toast.makeText(this, "AnimatedVectorDrawable are not supported", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setContentView(R.layout.vector_animation_activity);

        image = (ImageView) findViewById(R.id.image);

        findViewById(R.id.animate).setOnClickListener(v -> ((AnimatedVectorDrawable) image.getDrawable()).start());

        searchback = (ImageView) findViewById(R.id.searchback);
        searchback.setOnClickListener(v -> {
            isChecked = !isChecked;
            final int[] stateSet = {android.R.attr.state_checked * (isChecked ? 1 : -1)};
            searchback.setImageState(stateSet, true);
        });
    }
}
