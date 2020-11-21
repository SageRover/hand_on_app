package com.example.myapplication.recyclerview;

import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.Calendar;

/**
 * ListView / RecyclerView item 防止重复点击处理方案
 */

public abstract class OnNoDoubleClickListener implements OnItemClickListener {
    // 0.9秒内防止多次点击
    public static final int MIN_CLICK_DELAY_TIME = 900;
    private long lastClickTime = 0;

    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(adapter, view, position);
        }
    }

    // 用户需要进一步实现
    protected abstract void onNoDoubleClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position);

}