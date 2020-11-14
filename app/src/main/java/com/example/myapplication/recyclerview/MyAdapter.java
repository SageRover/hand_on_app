package com.example.myapplication.recyclerview;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.myapplication.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MyAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {

    /**
     * 构造方法，此示例中，在实例化Adapter时就传入了一个List。
     * 如果后期设置数据，不需要传入初始List，直接调用 super(layoutResId); 即可
     */
    public MyAdapter(int layoutResId, List<Integer> list) {
        super(layoutResId, list);

    }

    /**
     * 在此方法中设置item数据
     */
    @Override
    protected void convert(@NotNull BaseViewHolder helper, @NotNull Integer item) {
        //Glide加载应用资源图片
        Glide.with(getContext())
                .load(item)
                .into((ImageView) helper.getView(R.id.imageView1));  //是将资源加载到holder上的imageView1中，而不是直接加载到imageView1中
//        helper.setImageResource(R.id.imageView1,item);             //BRVAH自带设置图片

    }

//    @Override
//    protected void convert(@NotNull BaseViewHolder baseViewHolder, Integer integer) {
//
//    }
}

