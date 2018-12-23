package com.example.jtolu.git_finger_lock;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.jtolu.git_finger_lock.Finger.FingerActivity;
import com.example.jtolu.git_finger_lock.showLock.ShowLockActivity;


public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //اثر انگشت
        findViewById(R.id.btn_finger_authenticate).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, FingerActivity.class));
            }
        });

        //قفل کیبورد
        findViewById(R.id.btn_blur_lock).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, ShowLockActivity.class));

            }
        });
    }

}
