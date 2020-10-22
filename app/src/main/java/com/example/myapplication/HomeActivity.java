package com.example.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;

public class HomeActivity extends AppCompatActivity {
    Button but;
    private TextView texts;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;


    private List<String> lists;
    private MyRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //第一：Bmob默认初始化
        Bmob.initialize(this, "d26004a40bcb7e3f58360c2a17332282");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        LoginActivity.activityList.add(this);

        initData();

        //设置布局管理器 
        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        //设置Item增加、移除动画
        recyclerview.setItemAnimator(new DefaultItemAnimator());

        recyclerview.setPadding(8,8,8,8);

        //设置适配器 
        adapter = new MyRecyclerAdapter(this, createData());   //输入整型列表List，List由本活动内的createData()方法生成
        recyclerview.setAdapter(adapter);



        //以下为设置监听
        adapter.setOnClickListener(new MyRecyclerAdapter.OnItemClickListener() {
            @Override

            public void ItemClickListener(View view, int postion) {
                Toast.makeText(HomeActivity.this,"点击了："+postion,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void ItemLongClickListener(View view, int postion) {
                //长按删除
                lists.remove(postion);
                adapter.notifyItemRemoved(postion);
            }
        });




        texts = (TextView) findViewById(R.id.textView4);
        texts.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/方正清刻本悦宋简体.TTF"));
        texts.setEnabled(false);

        but = findViewById(R.id.button);
        //Bundle bundle = this.getIntent().getExtras();
        but.setClickable(true);
        // 因为用TextView点击，将属性改为true，点击后返回封面
        but.setOnClickListener(new onclick());


    }

    class onclick implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent2 = new Intent(); //新建Intent对象
            intent2.setClass(HomeActivity.this, LoginActivity.class);
            startActivityForResult(intent2, 0);   //返回前一页
        }
    }

    private void initData() {
        lists = new ArrayList();
        for (int i = 0; i < 100; i++) {
            lists.add("" + i);
        }
    }

    private List<Integer> createData() {
        List<Integer> data = new ArrayList<Integer>();
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                data.add(R.mipmap.ic_launcher);
            } else {
                data.add(R.drawable.setting);
            }
        }
        return data;
    }


}


