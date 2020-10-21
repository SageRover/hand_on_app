package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.domain.User;
import com.text.view.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {



    @BindView(R.id.et_user_name)
    ClearEditText etUserName;
    @BindView(R.id.et_user_phone)
    ClearEditText etUserPhone;
    @BindView(R.id.et_user_passward)
    ClearEditText etUserPassward;
    @BindView(R.id.et_user_passwardagain)
    ClearEditText etUserPasswardagain;
    @BindView(R.id.but_registered)
    Button butRegistered;

    protected void onCreate(Bundle savedInstanceState) {
        //第一：Bmob默认初始化
        Bmob.initialize(this, "d26004a40bcb7e3f58360c2a17332282");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        LoginActivity.activityList.add(this);

        initView();                     // 将所有对象与各布局文件的相关组件关联的步骤 以及 注册监听接口的步骤 集合到initView()方法内——称之为初始化

        butRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });


    }

    private void initView() {

        // 初始化控件
    }

    private void signUp() {

        String userName = etUserName.getText().toString().trim();
        String userPhone = etUserPhone.getText().toString().trim();
        String userPassward = etUserPassward.getText().toString().trim();
        String userPasswardAgain = etUserPasswardagain.getText().toString().trim();

        if (userName.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userPhone.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "请输入电话", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userPassward.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!userPassward.equals(userPasswardAgain)) {
            Toast.makeText(RegisterActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        final User user = new User();
        user.setUsername(userName);
        user.setPassword(userPassward);
        user.setMobilePhoneNumber(userPhone);
        user.setAge(18);
        user.setGender(0);

        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @OnClick(R.id.but_registered)
    public void onViewClicked() {
    }
}




