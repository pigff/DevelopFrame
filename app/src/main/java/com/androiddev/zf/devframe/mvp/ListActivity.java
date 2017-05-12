package com.androiddev.zf.devframe.mvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.androiddev.zf.devframe.R;
import com.androiddev.zf.devframe.utils.FragmentUtils;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        FragmentUtils.addFragment(getSupportFragmentManager(), R.id.container_list, new ListFragment());
    }
}
