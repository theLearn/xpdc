package com.example.hongcheng.common.view.citylist;

import android.support.annotation.NonNull;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.hongcheng.common.R;
import com.example.hongcheng.common.base.BaseListAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityListAdapter extends BaseListAdapter<Pair<String, List<CityItem>>, CityListViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_HEAD = 1;

    private int size = 0;
    private Map<Integer, Pair<Integer, Integer>> sourceMap = new HashMap<>();

    public void setSource(List<Pair<String, List<CityItem>>> data) {
        super.setData(data);

        if (data.isEmpty()) {
            return;
        }

        size = 0;
        for (int i = 0; i < data.size(); i++) {
            sourceMap.put(size, Pair.create(i, -1));
            size++;
            Pair<String, List<CityItem>> item = data.get(i);
            for (int j = 0; j < item.second.size(); j++) {
                sourceMap.put(size, Pair.create(i, j));
                size++;
            }
        }
    }

    @Override
    public int getItemCount() {

        return size;
    }

    @Override
    public int getItemViewType(int position) {
        Pair<Integer, Integer> pair = sourceMap.get(position);

        if (pair != null && pair.second < 0) {
            return TYPE_HEAD;
        } else {
            return TYPE_ITEM;
        }
    }

    @NonNull
    @Override
    public CityListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new CityListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_city_cell, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CityListViewHolder holder, final int i) {
        int type = getItemViewType(i);
        holder.icon.setVisibility(View.GONE);
        if (TYPE_HEAD == type) {
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.default_background));
            holder.name.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.text_ab));
            String str = getData().get(sourceMap.get(i).first).first;
            if("0".equals(str)) {
                holder.name.setText("热门城市");
                holder.icon.setVisibility(View.VISIBLE);
            } else {
                holder.name.setText(str);
            }
        } else {
            CityItem model = getData().get(sourceMap.get(i).first).second.get(sourceMap.get(i).second);
            holder.itemView.setBackgroundResource(R.color.white);
            holder.name.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.text_26));
            holder.name.setText(model.getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getOnItemClickListener() != null) {
                        getOnItemClickListener().onItemClick(i);
                    }
                }
            });
        }
    }
}
