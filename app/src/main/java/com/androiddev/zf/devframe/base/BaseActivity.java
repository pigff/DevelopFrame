package com.androiddev.zf.devframe.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import com.androiddev.zf.devframe.widget.Constants;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.io.Serializable;

/**
 * Created by greedy on 2017/5/11.
 */

public class BaseActivity extends RxAppCompatActivity {

    protected static final String TAG = "BaseActivity";

    protected void openActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void openActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(Constants.PARAM, bundle);
        startActivity(intent);
    }

    protected void openActivity(Class<?> clazz, Object o) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, clazz);
        if (o instanceof Integer) {
            bundle.putInt(Constants.DATA, (Integer) o);
        } else if (o instanceof String) {
            bundle.putString(Constants.DATA, (String) o);
        } else if (o instanceof Serializable) {
            bundle.putSerializable(Constants.DATA, (Serializable) o);
        } else if (o instanceof Parcelable) {
            bundle.putParcelable(Constants.DATA, (Parcelable) o);
        }
        intent.putExtra(Constants.PARAM, bundle);
        startActivity(intent);
    }

    protected void openActivityForResult(Class<?> clazz) {
        openActivityForResult(clazz, Constants.DEFAULT_REQUEST_CODE);
    }

    protected void openActivityForResult(Class<?> clazz, int requestCode) {
        Bundle bundle = new Bundle();
        openActivityForResult(clazz, bundle, requestCode);
    }

    protected void openActivityForResult(Class<?> clazz, Bundle bundle) {
        openActivityForResult(clazz, bundle, Constants.DEFAULT_REQUEST_CODE);
    }


    protected void openActivityForResult(Class<?> clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(Constants.PARAM, bundle);
        startActivityForResult(intent, requestCode);
    }

    protected Bundle getBundleData() {
        Bundle bundle = getIntent().getBundleExtra(Constants.PARAM);
        return bundle == null ? new Bundle() : bundle;
    }

    protected void showShortToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


}
