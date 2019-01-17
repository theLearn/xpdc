package com.xp.dc.xpdc.widget.choosecar;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.xp.dc.xpdc.R;
import com.xp.dc.xpdc.bean.CarClassfyInfo;
import com.xp.dc.xpdc.bean.CarInfo;

import java.util.List;

class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.ChooseViewHolder> {

    private Context context;
    private List<CarClassfyInfo> carClassfyInfos;
    //    private  LinearLayoutManager linearLayoutManager;
    private boolean mode;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ChooseAdapter(Context context, List<CarClassfyInfo> carClassfyInfos) {
        this.context = context;
        this.carClassfyInfos = carClassfyInfos;
    }

    public void setMode(boolean mode) {
        this.mode = mode;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChooseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_choose_type, viewGroup, false);
        return new ChooseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseViewHolder chooseViewHolder, final int i) {
        final CarClassfyInfo carClassfyInfo = carClassfyInfos.get(i);
        chooseViewHolder.iv_icon.setImageResource(carClassfyInfo.getIcon());
        chooseViewHolder.tv_type.setText(carClassfyInfo.getType());
        chooseViewHolder.tv_type.setGravity(mode ? Gravity.LEFT : Gravity.CENTER);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        chooseViewHolder.rv_car.setLayoutManager(linearLayoutManager);
        final CarAdapter carAdapter = new CarAdapter(context, carClassfyInfo.getCarInfo());
        carAdapter.setMode(mode);
        chooseViewHolder.rv_car.setAdapter(carAdapter);
        chooseViewHolder.rv_car.setVisibility(mode ? View.VISIBLE : View.GONE);
        chooseViewHolder.rv_car.addItemDecoration(new SpaceItemDecoration(10));
        chooseViewHolder.iv_icon.setVisibility(!mode ? View.VISIBLE : View.GONE);
        chooseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carClassfyInfo.setChecked(true);
                notifyDataSetChanged();
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(i);
            }
        });
        carAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                CarInfo carInfo = carClassfyInfo.getCarInfo().get(position);
                for (CarClassfyInfo classfyInfo : carClassfyInfos) {
                    for (CarInfo info : classfyInfo.getCarInfo()) {
                        if (carInfo.getId() != info.getId())
                            info.setChecked(false);
                    }
                }
                notifyDataSetChanged();
            }
        });

        if (!mode) {
            if (carClassfyInfos.size() < 3) {
                chooseViewHolder.itemView.getLayoutParams().width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth() / carClassfyInfos.size();
            } else {
                chooseViewHolder.itemView.getLayoutParams().width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth() / 3;
            }
        } else {
            chooseViewHolder.itemView.getLayoutParams().width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
        }
    }


    @Override
    public int getItemCount() {
        return carClassfyInfos.size();
    }

    class ChooseViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_icon;
        private TextView tv_type;
        private RecyclerView rv_car;

        private ChooseViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_type = itemView.findViewById(R.id.tv_type);
            rv_car = itemView.findViewById(R.id.rv_car);
        }
    }

    interface OnItemClickListener {
        void onItemClick(int position);
    }
}
