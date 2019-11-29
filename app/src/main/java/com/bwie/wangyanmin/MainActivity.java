package com.bwie.wangyanmin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.bwie.wangyanmin.Base.BaseActivity;
import com.bwie.wangyanmin.Fragment.HomeFragment;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    //Fragment集合
    private List<Fragment> list = new ArrayList<>();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<String> add = new ArrayList<>();
    private ImageView imageView;


    @Override
    protected void initData() {
        list.clear();
        add.clear();
        HomeFragment homeFragment = new HomeFragment();
        list.add(homeFragment);
        HomeFragment homeFragment1 = new HomeFragment();
        list.add(homeFragment1);
        HomeFragment homeFragment2 = new HomeFragment();
        list.add(homeFragment2);
        HomeFragment homeFragment3 = new HomeFragment();
        list.add(homeFragment3);
        add.add("要问");
        add.add("推荐");
        add.add("视频");
        add.add("财经");

        //设置监听
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return add.get(position);
            }
        });
        //设置联动
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void initVeiw() {
        //找控件
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.vp);
        imageView = findViewById(R.id.iv_one);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode==-1) {
            Uri data1 = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data1);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
