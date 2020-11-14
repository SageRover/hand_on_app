package com.example.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.example.myapplication.recyclerview.MyAdapter;
import com.example.myapplication.recyclerview.MyRecyclerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;

public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.button)
    Button but;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private TextView texts;
    private ArrayList<String> lists;
    private MyRecyclerAdapter adapter;
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        LoginActivity.activityList.add(this);

        //Bmob默认初始化
        Bmob.initialize(this, "d26004a40bcb7e3f58360c2a17332282");
        //Recyclerview初始化及其实现
        initRecyclerview();
        //底部导航栏
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.setting, "Setting"))
                .addItem(new BottomNavigationItem(R.drawable.user, "User"))
                .addItem(new BottomNavigationItem(R.drawable.jiangshi, "Books"))
                .addItem(new BottomNavigationItem(R.drawable.international, "International"))
                .setFirstSelectedPosition(0)
                .initialise();


        texts = (TextView) findViewById(R.id.textView4);
        texts.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/方正清刻本悦宋简体.TTF"));
        texts.setEnabled(false);

        //Bundle bundle = this.getIntent().getExtras();
        but.setClickable(true);
        // 因为用TextView点击，将属性改为true，点击后返回封面
        but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent2 = new Intent(); //新建Intent对象
                intent2.setClass(HomeActivity.this, LoginActivity.class);
                startActivityForResult(intent2, 0);   //返回前一页
            }
        });
    }

    private void initRecyclerview() {
        //设置布局管理器 
        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //设置Item增加、移除动画
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        recyclerview.addItemDecoration(new MyDecoration());

        MyAdapter adapter = new MyAdapter(R.layout.item_recyclerview, createImageIDData());
        recyclerview.setAdapter(adapter);
//      // 设置新的数据方法
//      adapter.setNewData(createImageIDData());

        //条目点击事件
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            Log.d(TAG, "onItemClick: ");
            Toast.makeText(HomeActivity.this, "点击了第" + (position + 1) + "条条目", Toast.LENGTH_SHORT).show();
        });
        //条目长按事件
        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "onItemLongClick: ");
                adapter.remove(position);
                Toast.makeText(HomeActivity.this, "长按了第" + (position + 1) + "条条目", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @NotNull
    private List<Integer> createImageIDData() {

        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                data.add(R.drawable.a2);
            } else {
                data.add(R.drawable.a1);
            }
        }
        return data;
    }

//    private void initRecyclerview() {
//        //？数据list初始化，生成一个包含100个数据的ArrayList-用于后续删除操作
//        initData();
//        //设置布局管理器 
//        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        //设置Item增加、移除动画
//        recyclerview.setItemAnimator(new DefaultItemAnimator());
////        //设置内边距
////        recyclerview.setPadding(8, 8, 8, 8);
//        //添加分割线
//        recyclerview.addItemDecoration(new MyDecoration());
//
//        //设置适配器 
//        adapter = new MyRecyclerAdapter(this, createData());   //输入整型列表List到适配器作为构造方法参数 ，List由本活动内的createData()方法生成
//        recyclerview.setAdapter(adapter);
//
//
//        //以下为设置监听 adapter，类似于设置按钮点击事件
//        adapter.setOnClickListener(new MyRecyclerAdapter.OnItemClickListener() {
//            @Override
//            public void ItemClickListener(View view, int position) {
//                Toast.makeText(HomeActivity.this, "点击了：" + position, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void ItemLongClickListener(View view, int position) {
//                //长按删除
//                lists.remove(position);
//                adapter.notifyItemRemoved(position);
//            }
//        });
//    }

//    /**
//     * 这个方法应该和长按删除有关
//     */
//    private void initData() {
//        lists = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            lists.add("" + i);  //数字前面加个双引号直接将数值化为String，这是利用java的toString机制来做的转换，任何类型在和String相加的时候，都会先转换成String。
//        }
//    }

//    @NotNull        //返回值data不能为空
//    //1到100交替存储图像资源
//    private List<Integer> createData() {
//        List<Integer> data = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            if (i % 2 == 0) {
//                data.add(R.drawable.a1);
//            } else {
//                data.add(R.drawable.a2);
//            }
//        }
//        return data;
//    }
}


