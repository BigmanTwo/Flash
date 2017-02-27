package com.example.administrator.flash.common;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.flash.core.entity.FileInfo;

import java.util.List;

/**
 * Created by KingHua on 2017/2/22.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    Context context;
    List<T> list;

    public CommonAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    /***
     * 增加數據源
     * @param list
     */
    public void addList(List<T> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    /***
     * 清除数据
     */
    public void clear(){
        this.list.clear();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=convertView(position,convertView);
        return convertView;
    }

    /***
     * 重写convertView方法
     * @param position
     * @param convertView
     * @return
     */
    public abstract View convertView(int position, View convertView);
    protected  Context getContext(){
        return context;
    }
}
