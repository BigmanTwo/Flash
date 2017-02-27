package com.example.administrator.flash.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.flash.R;

/**
 * Created by Administrator on 2017/2/16.
 */
public class MyRecylerViewAdapter extends RecyclerView.Adapter<MyRecylerViewAdapter.MyViewHolder> {
    private Context context;
    private String[] stringArray;
    public MyRecylerViewAdapter(Context context, String[] strs) {
        this.context=context;
        this.stringArray=strs;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(context, R.layout.item_transfer,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return stringArray.length;
    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
