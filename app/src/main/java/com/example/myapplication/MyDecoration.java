package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyDecoration extends RecyclerView.ItemDecoration {
    public MyDecoration() {
        super();
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
//        //实例化一个蓝色的画笔
//        Paint paint = new Paint();
//        paint.setColor(Color.BLUE);
//        //获取item的数量
//        int count = parent.getChildCount();
//        //此处绘制一个矩形，设置其位置属性
//        //由于列表是垂直分布的，所以每个要绘制的矩形左坐标和右坐标是一致的
//        int left = parent.getPaddingLeft();
//        int right = parent.getWidth() - parent.getPaddingRight();
//        //顶部坐标和底部坐标要根据item单独计算
//        for (int i = 0; i < count; i++){
//            View view = parent.getChildAt(i);
//            //要绘制的矩形的顶部恰好是item的底部，其底部则是此值 + 间距
//            float top = view.getBottom();
//            float bottom = view.getBottom() + 20;
//            //绘制矩形
//            c.drawRect(left,top,right,bottom,paint);
//    }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //设置每个item下方、左侧和右侧的间距
        outRect.bottom = 20;
        outRect.left = 20;
        outRect.right = 20;
    }
}
