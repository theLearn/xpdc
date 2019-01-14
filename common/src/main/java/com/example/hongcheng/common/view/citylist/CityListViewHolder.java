package com.example.hongcheng.common.view.citylist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.hongcheng.common.R;

public class CityListViewHolder extends RecyclerView.ViewHolder {
    public ImageView icon;
    public TextView name;
    public View itemView;

    public CityListViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        icon = itemView.findViewById(R.id.iv_city_item);
        name = itemView.findViewById(R.id.tv_city_item);
    }
}
