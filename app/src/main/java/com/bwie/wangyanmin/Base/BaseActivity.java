package com.bwie.wangyanmin.Base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/*
 *@auther:王彦敏
 *@Date: 2019/11/26
 *@Time:14:32
 *@Description:
 * */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
        initVeiw();
        initData();
    }

    protected abstract void initData();

    protected abstract void initVeiw();

    protected abstract int layoutId();
}
