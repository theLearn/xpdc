package com.xp.dc.xpdc.widget.choosecar;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xp.dc.xpdc.R;
import com.xp.dc.xpdc.bean.CarInfo;

import java.util.List;

class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private Context context;
    private List<CarInfo> carInfos;

    private int parentWidth;

    public CarAdapter(Context context, List<CarInfo> carInfos) {
        this.context = context;
        this.carInfos = carInfos;
    }

    private boolean mode;

    public void setMode(boolean mode) {
        this.mode = mode;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_car, viewGroup, false);
        parentWidth = viewGroup.getWidth();
        return new CarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder carViewHolder, final int i) {

        final CarInfo carInfo = carInfos.get(i);
        carViewHolder.iv_text_kcon.setImageResource(carInfo.getCarIcon());
        carViewHolder.tv_name.setText(carInfo.getCarName());
        carViewHolder.tv_price.setText(carInfo.getPrice());
        if (carInfo.isChecked()) {
            carViewHolder.ll_item.setBackgroundResource(R.drawable.ic_choosed_bg);
        } else {
            carViewHolder.ll_item.setBackgroundResource(R.mipmap.ic_unchoose);
        }
        carViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carInfo.setChecked(!carInfo.isChecked());
                notifyDataSetChanged();
            }
        });
//        carViewHolder.itemView.getLayoutParams().width = parentWidth / 3 - 15;
    }

    @Override
    public int getItemCount() {
        return carInfos.size();
    }

    class CarViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_text_kcon;
        private TextView tv_name;
        private TextView tv_price;
        private LinearLayout ll_item;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_item = itemView.findViewById(R.id.ll_item);
            iv_text_kcon = itemView.findViewById(R.id.iv_text_icon);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_price = itemView.findViewById(R.id.tv_price);
        }
    }

}
