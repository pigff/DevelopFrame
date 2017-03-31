package com.androiddev.zf.devframe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.androiddev.zf.devframe.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_task);
//        showEmptyView();
        Button button = (Button) findViewById(R.id.btn_01);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showErrorView();
            }
        });
//        Button button1 = (Button) findViewById(R.id.btn_02);
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showEmptyView();
//            }
//        });
    }

    @Override
    protected boolean hasBaseLayout() {
        return true;
    }
}
