package com.example.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import cn.bmob.v3.Bmob;

public class HomeActivity extends AppCompatActivity {
    Button but;
    private TextView texts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //第一：Bmob默认初始化
        Bmob.initialize(this, "d26004a40bcb7e3f58360c2a17332282");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        LoginActivity.activityList.add(this);

        texts=(TextView)findViewById(R.id.textView4);
        texts.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/方正清刻本悦宋简体.TTF"));
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
}


