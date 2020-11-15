package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;

public class PictureDisplayActivity extends Activity {
    private static final String TAG = "PictureDisplayActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_big_photoview);
        LoginActivity.activityList.add(this);
    }



}
