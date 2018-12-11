package com.example.user.infinity21.Views;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by User on 21/02/2018.
 */

public class CardView extends LinearLayout {
    ImageView image;

    public CardView(Context context) {
        super(context);

        this.setOrientation(VERTICAL);
        image = new ImageView(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        image.setLayoutParams(layoutParams);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        addView(image);
    }
}
