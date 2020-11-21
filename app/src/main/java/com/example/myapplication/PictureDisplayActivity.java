package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PictureDisplayActivity extends Activity {
    private static final String TAG = "PictureDisplayActivity";
    @BindView(R.id.PhotoView)
    com.github.chrisbanes.photoview.PhotoView PhotoView;
    @BindView(R.id.picture_display_text)
    TextView pictureDisplayText;

    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_big_photoview);
        ButterKnife.bind(this);
        LoginActivity.activityList.add(this);
        Intent intent = getIntent();
        ArrayList<Integer> enlargeImage = intent.getIntegerArrayListExtra("enlargeImage");
        int position = intent.getIntExtra("position", 1);
        assert enlargeImage != null;
        PhotoView.setImageResource(enlargeImage.get(position));
        Log.d(TAG, "onCreate: "+position);
        pictureDisplayText.setText(position+1+"");

    }


}
