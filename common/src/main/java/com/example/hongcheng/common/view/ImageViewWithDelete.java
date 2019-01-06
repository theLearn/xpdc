package com.example.hongcheng.common.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.hongcheng.common.R;
import com.example.hongcheng.common.util.ImageLoadUtils;
import com.example.hongcheng.common.util.ScreenUtils;
import com.example.hongcheng.common.util.StringUtils;

public class ImageViewWithDelete extends FrameLayoutForSquare  {

    private ImageView imageView;
    private ImageView deleteView;

    public ImageViewWithDelete(@NonNull Context context) {
        this(context, null);
    }

    public ImageViewWithDelete(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageViewWithDelete(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    public void initialize(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr){
        imageView = new ImageView(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(layoutParams);
        layoutParams.setMargins(0, ScreenUtils.dp2px(context, 8),ScreenUtils.dp2px(context, 8),0);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        this.addView(imageView);

        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.addView(relativeLayout);

        deleteView = new ImageView(context);
        RelativeLayout.LayoutParams deleteLayoutParam = new RelativeLayout.LayoutParams(ScreenUtils.dp2px(context, 16), ScreenUtils.dp2px(context, 16));
        deleteLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        deleteLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        deleteView.setLayoutParams(deleteLayoutParam);
        relativeLayout.addView(deleteView);
        deleteView.setImageResource(R.drawable.delete_red);

        setImageModule(null);
    }

    public void setImageModule(final String url){
        post(new Runnable() {
            @Override
            public void run() {
                if(StringUtils.isEmpty(url)){
                    imageView.setImageResource(R.drawable.icon_camera_gray);
                    deleteView.setVisibility(GONE);

                }else{
                    ImageLoadUtils.bindImageUrlForRound(imageView, url);
                    deleteView.setVisibility(VISIBLE);
                }
            }
        });
    }

    public ImageView getImageView () {
        return imageView;
    }

    public ImageView getDeleteView() {
        return deleteView;
    }

    public interface OnImgDeleteListener {
        void onImgDelete(int position);
    }
}
