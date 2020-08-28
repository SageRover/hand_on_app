package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends Activity {

    public static List<Activity> activityList = new LinkedList();

    public void exit() {
        for (Activity act : activityList) {
            act.finish();
        }
        System.exit(0);
    }


    Button but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.activityList.add(this);

        but = findViewById(R.id.but);   //与布局文件的相关组建关联
        but.setOnClickListener(new click());     //注册监听接口
    }

    class click implements View.OnClickListener {   //定义一个类实现监听的接口
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            // 创建好之后就可以通过它启动新的Activity
            startActivity(intent);
        }
    }


    public void showdialog(View view) {
        //定义一个新的对话框对象
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
        //设置对话框提示内容
        alertdialogbuilder.setMessage("确定要退出程序吗？");
        //定义对话框2个按钮标题及接受事件的函数
        alertdialogbuilder.setPositiveButton("确定", click1);
        alertdialogbuilder.setNegativeButton("取消", click2);
        //创建并显示对话框
        AlertDialog alertdialog1 = alertdialogbuilder.create();
        alertdialog1.show();

    }

    private DialogInterface.OnClickListener click1 = new DialogInterface.OnClickListener() {
        //使用该标记是为了增强程序在编译时候的检查，如果该方法并不是一个覆盖父类的方法，在编译时编译器就会报告错误。
        @Override

        public void onClick(DialogInterface arg0, int arg1) {
            //当按钮click1被按下时执行结束进程
            exit();
            android.os.Process.killProcess(android.os.Process.myPid());

        }

    };

    private DialogInterface.OnClickListener click2 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface arg0, int arg1) {
            //当按钮click2被按下时则取消操作
            arg0.cancel();
        }
    };

}


