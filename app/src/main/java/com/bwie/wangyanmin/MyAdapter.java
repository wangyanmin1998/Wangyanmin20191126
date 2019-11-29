package com.bwie.wangyanmin;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.wangyanmin.Base.BaseActivity;

import java.util.List;

/*
 *@auther:王彦敏
 *@Date: 2019/11/26
 *@Time:15:02
 *@Description:
 * */
public class MyAdapter extends BaseAdapter {
    List<Bean.ListdataBean> list;
    public MyAdapter(List<Bean.ListdataBean> list) {
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        Bean.ListdataBean listdataBean = list.get(position);
        int itemType = listdataBean.getItemType();
        if (itemType==1){
            return 0;
        }else {
            return 1;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemViewType = getItemViewType(position);
        ViewHolder holder=null;
        if (convertView==null){
            if (itemViewType==0){
                convertView=View.inflate(parent.getContext(),R.layout.item,null);
                //创建对象
                holder=new ViewHolder();
                holder.tv_name=convertView.findViewById(R.id.tv_name);
                holder.iv=convertView.findViewById(R.id.iv);
                convertView.setTag(holder);

            }else {
                convertView=View.inflate(parent.getContext(),R.layout.item1,null);
                //创建对象
                holder=new ViewHolder();
                holder.tv_name=convertView.findViewById(R.id.tv_name);
                holder.iv=convertView.findViewById(R.id.iv);
                convertView.setTag(holder);

            }



        }else {
            holder= (ViewHolder) convertView.getTag();

        }
        Bean.ListdataBean listdataBean = list.get(position);
        String title = listdataBean.getTitle();
        holder.tv_name.setText(title);

        NetUtils.getInstance().getPhoto(listdataBean.getImageurl(),holder.iv);


        return convertView;
    }



    private class ViewHolder{
        private TextView tv_name;
        private ImageView iv;
    }

}
