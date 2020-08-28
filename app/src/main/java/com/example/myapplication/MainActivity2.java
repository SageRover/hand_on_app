package com.example.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    Button but;
    private TextView texts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        MainActivity.activityList.add(this);

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
            intent2.setClass(MainActivity2.this, MainActivity.class);
            startActivityForResult(intent2, 0);   //返回前一页
        }
    }
}


