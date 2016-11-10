package com.kvest.material_design_playground.vector_animation;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.kvest.material_design_playground.R;

/**
 * Created by kvest on 10/8/16.
 */
public class VectorAnimationActivity extends AppCompatActivity {
    private ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vector_animation_activity);

        image = (ImageView) findViewById(R.id.image);

        findViewById(R.id.animate).setOnClickListener(v -> ((AnimatedVectorDrawable) image.getDrawable()).start());
    }
}
