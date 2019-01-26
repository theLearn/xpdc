package com.example.hongcheng.common.util;

import android.graphics.*;
import android.widget.ImageView;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.hongcheng.common.R;

public final class ImageLoadUtils {

    private ImageLoadUtils() {
    }

    public static void bindImageUrl(ImageView view, String url) {
        bindImageUrl(view, url, R.drawable.img_default);
    }

    public static void bindImageUrlForCycle(ImageView view, String url) {
        bindImageUrlForCycle(view, url, R.drawable.img_default);
    }

    public static void bindImageUrlForRound(ImageView view, String url) {
        bindImageUrlForRound(view, url, R.drawable.img_default);
    }

    public static void bindImageUrl(ImageView view, String url, int resId) {
        GlideApp.with(view.getContext()).load(url).error(resId)
                .placeholder(resId)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade()).into(view);
    }

    public static void bindImageUrlForCycle(ImageView view, String url, int resId) {
        GlideApp.with(view.getContext()).load(url).placeholder(resId)
                .error(resId)
                .transform(new CircleCrop())
                .transition(DrawableTransitionOptions.withCrossFade()).into(view);
    }

    public static void bindImageUrlForRound(ImageView view, String url, int resId) {
        GlideApp.with(view.getContext()).load(url).placeholder(resId)
                .error(resId)
                .transform(new RoundedCorners(30))
                .transition(DrawableTransitionOptions.withCrossFade()).into(view);
    }

    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }
}
