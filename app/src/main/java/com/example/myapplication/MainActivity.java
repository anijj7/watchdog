package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button mCrash;
    private Button mCommon;
    private Button mOom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCrash = findViewById(R.id.crash);
        mCrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new RuntimeException(" my click exception ");
            }
        });

        mCommon = findViewById(R.id.common);
        mCommon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, R.string.app_name, Toast.LENGTH_LONG).show();
            }
        });

        mOom = findViewById(R.id.oom);
        mOom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oom();
            }
        });
    }

    private void oom(){
       throw new OutOfMemoryError(" oom error");
    }
}
