package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Sage
 * @time 2020/10/22 22:31
 * @description MyRecyclerAdapter
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    private Context context;

    private List<Integer> data;
    private OnItemClickListener mListener;

    //构造方法
    public MyRecyclerAdapter(Context context, List<Integer> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        holder.imageView.setImageResource(this.data.get(position));

/*        final ImageData data = this.data.get(position);
        //获取item宽度，计算图片等比例缩放后的高度，为imageView设置参数
        ViewGroup.LayoutParams layoutParams = holder.imageView.getLayoutParams();
        float itemWidth = (ScreenUtils.getScreenWidth(context) - 16*3) / 2;
        layoutParams.width = (int) itemWidth;
        float scale = (itemWidth+0f)/data.getWidth();
        layoutParams.height= (int) (data.getHeight()*scale);
        holder.imageView.setLayoutParams(layoutParams);*/

/*        //获取item宽度，计算图片等比例缩放后的高度，为 imagevlew设置参数
        LinearLayout.LayoutParams layoutparams=(LinearLayout.LayoutParams)holder.imageView.getLayoutParams();
        float itemwidth =(ScreenUtils.getScreenWidth(holder.itemView.getContext()-8*3)/2);
        layoutparams.width =(int) itemwidth;
        float scale = (itemwidth+0f)/book width;
        layoutparams. height=(int)(book. height"scale);
        holder. imageview. setlayoutparams(layoutparams);*/

        if (position % 2 == 0) {
            holder.imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
        } else {
            holder.imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
        }

        //以下为设置监听
        if (mListener != null) {//如果设置了监听那么它就不为空，然后回调相应的方法
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();//得到当前点击item的位置pos
                    mListener.ItemClickListener(holder.itemView, pos);//把事件交给我们实现的接口那里处理
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();//得到当前点击item的位置pos
                    mListener.ItemLongClickListener(holder.itemView, pos);//把事件交给我们实现的接口那里处理
                    return true;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public interface OnItemClickListener {
        void ItemClickListener(View view, int postion);
        void ItemLongClickListener(View view, int postion);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView1);
        }
    }

}