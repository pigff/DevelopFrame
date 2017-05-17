package com.androiddev.zf.devframe.mvp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androiddev.zf.devframe.R;
import com.androiddev.zf.devframe.data.api.Joke;
import com.androiddev.zf.devframe.base.BaseToolbarActivity;
import com.androiddev.zf.devframe.mvp.presenter.IMainView;
import com.androiddev.zf.devframe.mvp.presenter.imp.MainPresenter;

public class MainActivity extends BaseToolbarActivity<MainPresenter> implements IMainView {

    private TextView mTvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getPresenter().attachView(this);
        getPresenter().getData();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("哈哈这是标题");
        mTvContent = (TextView) findViewById(R.id.tv_content);
        Button button = (Button) findViewById(R.id.btn_jump);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ListActivity.class);
            }
        });
    }

    @Override
    protected boolean canGoBack() {
        return true;
    }

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    protected int provideContentView() {
        return R.layout.act_task;
    }

    @Override
    public void showData(Joke joke) {
        mTvContent.setText(joke != null ? joke.toString() : "");
    }
}
