package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.Code;
import com.domain.User;
import com.text.view.ClearEditText;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends Activity {

    public static List<Activity> activityList = new LinkedList();
    @BindView(R.id.et_user_name)
    ClearEditText etUserName;
    @BindView(R.id.et_user_password)
    ClearEditText etUserPassword;
    @BindView(R.id.et_security_code)
    EditText etSecurityCode;

    public void exit() {
        for (Activity act : activityList) {
            act.finish();
        }
        System.exit(0);
    }


    Button but_login;
    Button but_register;
    ImageView ima;
    private String realCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //第一：Bmob默认初始化
        Bmob.initialize(this, "d26004a40bcb7e3f58360c2a17332282");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
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
        but_login = findViewById(R.id.but_login);   //与布局文件的相关组件关联
        but_register = findViewById(R.id.but_register);   //与布局文件的相关组件关联

        ima = findViewById(R.id.iv_registeractivity_showCode);

        // 设置点击事件监听器
        but_login.setOnClickListener(new click());        //注册“登录”监听接口
        but_register.setOnClickListener(new click2());     //注册“注册”监听接口
/*
        mBtLoginactivityLogin.setOnClickListener(this);
        mIvloginactivityShowcode.setOnClickListener(this);
*/

    }


    class click implements View.OnClickListener {   //定义一个类实现监听的接口
        public void onClick(View v) {
            Animation animation = new AlphaAnimation(1.0f, 0.0f);
            animation.setDuration(300);
            but_login.startAnimation(animation);

            login();
        }
    }

    class click2 implements View.OnClickListener {   //定义一个类实现监听的接口
        public void onClick(View v) {
            Animation animation = new AlphaAnimation(1.0f, 0.0f);
            animation.setDuration(300);
            but_register.startAnimation(animation);

            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
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
            Process.killProcess(Process.myPid());

        }

    };

    private DialogInterface.OnClickListener click2 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface arg0, int arg1) {
            //当按钮click2被按下时则取消操作
            arg0.cancel();
        }
    };


/*    private void login() {
        String userAccount = etUserName.getText().toString().trim();
        String userPassword = etUserPassword.getText().toString().trim();

        if (userAccount.isEmpty()) {
            Toast.makeText(LoginActivity.this, "请输入用户账号", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPassword.isEmpty()) {
            Toast.makeText(LoginActivity.this, "请输入用户密码", Toast.LENGTH_SHORT).show();
            return;
        }

        BmobUser.loginByAccount(userAccount, userPassword, new LogInListener<User>() {
            public void done(User user, BmobException e) {
                if (e == null) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", user.getSessionToken());
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,LoginActivity.class));

                } else {
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }*/



    private void login() {
        final User user = new User();
        user.setUsername(etUserName.getText().toString().trim());
        user.setPassword(etUserPassword.getText().toString().trim());

        String SecurityCode = etSecurityCode.getText().toString().trim().toLowerCase();

        if (SecurityCode.isEmpty() || !SecurityCode.equals(realCode.toLowerCase())) {
            Toast.makeText(LoginActivity.this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        user.login(new SaveListener<User>() {
            public void done(User bmobUser, BmobException e) {
                if (e == null) {

                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    User user = BmobUser.getCurrentUser(User.class);

                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    // 创建好之后就可以通过它启动新的Activity
                    startActivity(intent);

                } else {
                    Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}


