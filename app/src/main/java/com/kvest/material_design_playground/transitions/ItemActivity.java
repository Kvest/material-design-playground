package com.kvest.material_design_playground.transitions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kvest.material_design_playground.R;
import com.kvest.material_design_playground.RingView;
import com.kvest.material_design_playground.Utils;

import java.util.List;

/**
 * Created by kvest on 10/16/16.
 */
public class ItemActivity extends AppCompatActivity {
    private static final String EXTRA_NAME = "extra.name";
    private static final String EXTRA_IMAGE_ID = "extra.imageId";

    public static void start(Activity context, String name, @DrawableRes int imageId,
                             TextView nameView, RingView frame) {
        Intent intent = new Intent(context, ItemActivity.class);
        putExtras(intent, name, imageId);

        String transitionName = context.getString(R.string.transition_name);
        String transitionFrame = context.getString(R.string.transition_frame);

        Pair<View, String> p1 = Pair.create(nameView, transitionName);
        Pair<View, String> p2 = Pair.create(frame, transitionFrame);
        Bundle options = ActivityOptionsCompat
                            .makeSceneTransitionAnimation(context, p1, p2)
                            .toBundle();


        //Transition classes available only from KITKAT version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //grab properties for custom transitions
            Bundle nameExtra = new Bundle();
            TextColorTransition.addExtraProperties(nameView, nameExtra);
            intent.putExtra(transitionName, nameExtra);

            Bundle frameExtra = new Bundle();
            RingViewTransition.addExtraProperties(frame, frameExtra);
            intent.putExtra(transitionFrame, frameExtra);
        }

        context.startActivity(intent, options);
    }

    public static void start(Context context, String name, @DrawableRes int imageId) {
        Intent intent = new Intent(context, ItemActivity.class);
        putExtras(intent, name, imageId);
        context.startActivity(intent);
    }

    private static void putExtras(Intent intent, String name, @DrawableRes int imageId) {
        intent.putExtra(EXTRA_NAME, name);
        intent.putExtra(EXTRA_IMAGE_ID, imageId);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_activity);

        init();
    }

    private void init() {
        int imageRes = getIntent().getIntExtra(EXTRA_IMAGE_ID, 0);
        Bitmap bitmap = Utils.getCroppedBitmap(BitmapFactory.decodeResource(getResources(), imageRes));
        ((ImageView) findViewById(R.id.image)).setImageBitmap(bitmap);

        String name = getIntent().getStringExtra(EXTRA_NAME);
        final TextView nameView = (TextView) findViewById(R.id.name);
        nameView.setText(name);
        Palette palette = Palette.from(bitmap).generate();
        nameView.setTextColor(palette.getVibrantColor(Color.BLACK));

        RingView frame = (RingView)findViewById(R.id.frame);
        frame.setColor(palette.getVibrantColor(Color.BLACK));

        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                Intent intent = getIntent();
                for (int i = 0; i < sharedElementNames.size(); i++) {
                    String name = sharedElementNames.get(i);
                    if (intent.hasExtra(name)) {
                        sharedElements.get(i).setTag(R.id.tag_transition_extra_properties, intent.getBundleExtra(name));
                    }
                }
            }

            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);

                //clear all tags - without tags transicion will use view's custom state
                for (View view : sharedElements) {
                    view.setTag(R.id.tag_transition_extra_properties, null);
                }
            }
        });
    }
}
