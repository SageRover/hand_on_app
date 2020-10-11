package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.Code;

import java.util.LinkedList;
import java.util.List;

public class LoginActivity extends Activity {

    public static List<Activity> activityList = new LinkedList();

    public void exit() {
        for (Activity act : activityList) {
            act.finish();
        }
        System.exit(0);
    }


    Button but;
    ImageView ima;
    private String realCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginActivity.activityList.add(this);

        initView();                     // 将所有对象与各布局文件的相关组件关联的步骤 以及 注册监听接口的步骤 集合到initView()方法内——称之为初始化、

        //将验证码用图片的形式显示出来
        ima.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();  //获取当前图片验证码的对应内容用于校验

        ima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ima.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode();
                Toast.makeText(LoginActivity.this, realCode, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initView() {
        // 初始化控件
        but = findViewById(R.id.but);   //与布局文件的相关组件关联
        ima = findViewById(R.id.iv_registeractivity_showCode);
/*        mBtLoginactivityLogin = findViewById(R.id.bt_loginactivity_login);
        mTvLoginactivityRegister = findViewById(R.id.tv_loginactivity_register);
        mEtLoginactivityUsername = findViewById(R.id.et_loginactivity_username);
        mEtLoginactivityPassword = findViewById(R.id.et_loginactivity_password);
        mEtloginactivityPhonecodes = findViewById(R.id.et_loginactivity_phoneCodes);
        mIvloginactivityShowcode = findViewById(R.id.iv_loginactivity_showCode);*/

        // 设置点击事件监听器
        but.setOnClickListener(new click());     //注册监听接口
/*
        mBtLoginactivityLogin.setOnClickListener(this);
        mIvloginactivityShowcode.setOnClickListener(this);
*/

    }


    class click implements View.OnClickListener {   //定义一个类实现监听的接口
        public void onClick(View v) {
            Animation animation=new AlphaAnimation(1.0f,0.0f);
            animation.setDuration(300);
            but.startAnimation(animation);

            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
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


