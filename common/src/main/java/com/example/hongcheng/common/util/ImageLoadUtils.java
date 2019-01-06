package com.example.hongcheng.common.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.hongcheng.common.R;

public final class ImageLoadUtils {

    private ImageLoadUtils(){}

    public static void bindImageUrl(ImageView view, String url) {
        RequestOptions options = new RequestOptions()
                .error(R.drawable.img_default)
                .placeholder(R.drawable.img_default)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop();

        Glide.with(view.getContext()).applyDefaultRequestOptions(options).load(url)
                .transition(DrawableTransitionOptions.withCrossFade()).into(view);
    }

    public static void bindImageUrlForCycle(ImageView view, String url) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.img_default)
                .error(R.drawable.img_default)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .transform(new CircleCrop());

        Glide.with(view.getContext()).applyDefaultRequestOptions(options).load(url)
                .transition(DrawableTransitionOptions.withCrossFade()).into(view);
    }

    public static void bindImageUrlForRound(ImageView view, String url) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.img_default)
                .error(R.drawable.img_default)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .transform(new RoundedCorners(30));

        Glide.with(view.getContext()).applyDefaultRequestOptions(options).load(url)
                .transition(DrawableTransitionOptions.withCrossFade()).into(view);
    }
}
