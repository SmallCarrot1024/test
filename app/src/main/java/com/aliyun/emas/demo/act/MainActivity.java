package com.aliyun.emas.demo.act;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.aliyun.emas.demo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView brand = findViewById(R.id.tv_brand);
        brand.setText(Build.BRAND+"/"+Build.MODEL+"/"+Build.VERSION.RELEASE);

    }
}
