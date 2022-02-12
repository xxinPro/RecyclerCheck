package com.xxin.recyclercheck;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerBuilder extends RecyclerView.Adapter<RecyclerBuilder.MyViewHolder> {
    private final Context context;
    private List<ItemProperty> itemProperties; //item属性集合

    public RecyclerBuilder(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.itemTitle.setText(itemProperties.get(position).getTitle());
//        是否是编辑状态，是的话显示选中按钮，反之隐藏
        if (isEditStatus){
            holder.itemSelect.setVisibility(View.VISIBLE);
//            按钮是否为选中状态
            if (itemProperties.get(position).isSelect()){
                holder.itemSelect.setImageResource(R.drawable.selectfill);
            }
            else {
                holder.itemSelect.setImageResource(R.drawable.select);
            }
        }
        else {
            holder.itemSelect.setVisibility(View.GONE);
        }
//        itemClick
        holder.itemView.setOnClickListener(view -> {
            if (onRecyclerViewItemClick != null){
                onRecyclerViewItemClick.OnItemClick(holder.getAdapterPosition(), itemProperties);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemProperties.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemTitle;
        private final ImageView itemSelect;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemSelect = itemView.findViewById(R.id.itemSelect);
        }
    }

//    获取item属性集合
    public List<ItemProperty> getItemProperties() {
        return itemProperties;
    }

    //刷新列表数据
    @SuppressLint("NotifyDataSetChanged")
    public void notifyList(List<ItemProperty> itemProperties){
        this.itemProperties = itemProperties;
        notifyDataSetChanged();
    }

    private boolean isEditStatus; //是否是编辑状态
//    设置编辑模式
    @SuppressLint("NotifyDataSetChanged")
    public void setEditMode(boolean isEditStatus){
        this.isEditStatus = isEditStatus;
        notifyDataSetChanged();//刷新列表
    }

//    响应itemClick
    public void setOnRecyclerViewItemClick(OnRecyclerViewItemClick onRecyclerViewItemClick){
        this.onRecyclerViewItemClick = onRecyclerViewItemClick;
    }

    private OnRecyclerViewItemClick onRecyclerViewItemClick; //itemClick

    interface OnRecyclerViewItemClick{
        void OnItemClick(int position, List<ItemProperty> itemProperties);
    }
}
