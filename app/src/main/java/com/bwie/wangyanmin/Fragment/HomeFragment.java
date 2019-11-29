package com.bwie.wangyanmin.Fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bwie.wangyanmin.Base.BaseFragment;
import com.bwie.wangyanmin.Bean;
import com.bwie.wangyanmin.MyAdapter;
import com.bwie.wangyanmin.NetUtils;
import com.bwie.wangyanmin.R;
import com.bwie.wangyanmin.SeconnedActivity;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    private PullToRefreshListView pullToRefreshListView;
    private int page=1;
    private RelativeLayout relativeLayout;
    private List<Bean.ListdataBean> list=new ArrayList<>();
    @Override
    protected int layoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initVeiw(View inflate) {
        //获取控件
        relativeLayout = inflate.findViewById(R.id.rl);
        pullToRefreshListView = inflate.findViewById(R.id.pull);
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String arr="https://www.thepaper.cn/newsDetail_forward_4923534";
                Intent intent = new Intent(getActivity(), SeconnedActivity.class);
                intent.putExtra("key",arr);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void initData() {
        //初始化设置数据
        pullToRefreshListView.setMode(PullToRefreshListView.Mode.BOTH);

        //设置监听

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page=1;
                list.clear();
                Data();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;

                Data();
            }
        });
        Data();
    }

    private void Data() {
        if (NetUtils.net(getActivity())){
            //判断网络，有网显示，无网显示图片
            relativeLayout.setVisibility(View.GONE);
            pullToRefreshListView.setVisibility(View.VISIBLE);

            String httpUrl="";
            if (page==1){
                httpUrl="http://blog.zhaoliang5156.cn/api/pengpainews/pengpai1.json";
            }else {
                httpUrl="http://blog.zhaoliang5156.cn/api/pengpainews/pengpai2.json";
            }
            //获取数据
            NetUtils.getInstance().getData(httpUrl, new NetUtils.MyCallBack() {
                @Override
                public void onGson(String json) {
                    Log.e("xxx",json);
                    //Gson解析
                    Gson gson = new Gson();
                    Bean bean = gson.fromJson(json, Bean.class);
                    List<Bean.ListdataBean> listdata = bean.getListdata();
                    list.addAll(listdata);
                    pullToRefreshListView.setAdapter(new MyAdapter(list));
                    pullToRefreshListView.onRefreshComplete();

                }
            });


        }else {
            //判断网络，有网显示，无网显示图片
            relativeLayout.setVisibility(View.VISIBLE);
            pullToRefreshListView.setVisibility(View.GONE);
        }

    }

}
