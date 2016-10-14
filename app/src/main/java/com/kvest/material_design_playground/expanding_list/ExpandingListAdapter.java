package com.kvest.material_design_playground.expanding_list;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kvest.material_design_playground.R;
import com.kvest.material_design_playground.Utils;

import java.util.List;

/**
 * Created by kvest on 9/28/16.
 */
public class ExpandingListAdapter extends RecyclerView.Adapter<ExpandingListAdapter.ViewHolder> {
    private static final String LONG_LOREM = "Mauris dapibus convallis massa, vitae ultrices est ultricies ut. Nam porttitor et metus ac bibendum. Nam at justo vitae felis lacinia ultrices laoreet ut arcu. Nam ac purus et turpis convallis mollis. Integer lorem eros, hendrerit imperdiet interdum vitae, sagittis eget ipsum. Donec dignissim tortor at felis fringilla, sed dignissim diam vulputate. Nam sit amet facilisis massa. Suspendisse posuere quam quis augue dapibus venenatis.";

    private static String PAYLOAD_EXTEND = "PAYLOAD_EXTEND";

    private ExpandableListItem[] items = {
            new ExpandableListItem("Chameleon", LONG_LOREM, R.drawable.chameleon),
            new ExpandableListItem("Rock", LONG_LOREM, R.drawable.rock),
            new ExpandableListItem("Flower", LONG_LOREM, R.drawable.flower),
            new ExpandableListItem("Chameleon", LONG_LOREM, R.drawable.chameleon),
            new ExpandableListItem("Rock", LONG_LOREM, R.drawable.rock),
            new ExpandableListItem("Flower", LONG_LOREM, R.drawable.flower),
            new ExpandableListItem("Chameleon", LONG_LOREM, R.drawable.chameleon),
            new ExpandableListItem("Rock", LONG_LOREM, R.drawable.rock),
            new ExpandableListItem("Flower", LONG_LOREM, R.drawable.flower),
            new ExpandableListItem("Chameleon", LONG_LOREM, R.drawable.chameleon),
            new ExpandableListItem("Rock", LONG_LOREM, R.drawable.rock),
            new ExpandableListItem("Flower", LONG_LOREM, R.drawable.flower)
    };
    private final LayoutInflater inflater;

    public ExpandingListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.expanding_list_item, parent, false);

        return new ViewHolder(parent, view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items[position]);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            holder.bind(items[position]);
        } else {
            holder.bind(items[position], payloads);
        }
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;
        private TextView description;

        public ViewHolder(ViewGroup parent, View itemView) {
            super(itemView);
            Log.d("KVEST_TAG", "Create ViewHolder: ");
            image = (ImageView)itemView.findViewById(R.id.image);
            name = (TextView)itemView.findViewById(R.id.name);
            description = (TextView)itemView.findViewById(R.id.description);

            itemView.setOnClickListener(v -> {
                int adapterPos = getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    ExpandableListItem item = items[adapterPos];

                    item.isExpanded = !item.isExpanded;
                    notifyItemChanged(adapterPos, PAYLOAD_EXTEND);
                }
            });
        }

        public void bind(ExpandableListItem item) {
            Bitmap bitmap = Utils.getCroppedBitmap(BitmapFactory.decodeResource(image.getContext().getResources(), item.imgResource));

            image.setImageBitmap(bitmap);
            name.setText(item.name);
            description.setText(item.description);
            description.getLayoutParams().height = item.isExpanded ? ViewGroup.LayoutParams.WRAP_CONTENT : 0;
        }

        public void bind(ExpandableListItem item, List<Object> payloads) {
            for (Object payload : payloads) {
                if (PAYLOAD_EXTEND.equals(payload)) {
                    description.getLayoutParams().height = item.isExpanded ? ViewGroup.LayoutParams.WRAP_CONTENT : 0;
                    description.requestLayout();
                } else {
                    throw new IllegalArgumentException("Unknown payload " + payload);
                }
            }
        }

/*        private void toggleVisibility(final View view) {
            view.getLayoutParams().height = view.getLayoutParams().height == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : 0;
            view.requestLayout();

//            final int start = view.getHeight();
//
//            view.getLayoutParams().height = view.getLayoutParams().height == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : 0;
//            view.requestLayout();


//            final ViewTreeObserver observer = view.getViewTreeObserver();
//            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//                @Override
//                public boolean onPreDraw() {
//                    observer.removeOnPreDrawListener(this);
//
//                    ValueAnimator animator = ValueAnimator.ofInt(start, view.getHeight());
//                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator animation) {
//                            view.getLayoutParams().height = (Integer) animation.getAnimatedValue();
//                            view.requestLayout();
//                        }
//                    });
//                    animator.start();
//
//                    return false;
//                }
//            });

            //view.setVisibility(view.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        }*/
    }
}
