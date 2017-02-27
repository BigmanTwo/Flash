package com.example.administrator.flash.common;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.flash.R;
import com.example.administrator.flash.utils.StatusBarUtils;

/**
 * Created by KingHua on 2017/2/21.
 */
public class BaseActivity extends AppCompatActivity {
    Context context;
    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        context=this;
        StatusBarUtils.setStatuBarAndBottomBarTranslucent(this);
        super.onCreate(savedInstanceState);
    }
    public Context getContext(){
        return context;
    }

    /*展示对话框*/
    protected void showProgressDialog(){
        if (mProgressDialog==null){
            mProgressDialog=new ProgressDialog(context);
        }
        mProgressDialog.setMessage(getResources().getString(R.string.tip_loading));
        mProgressDialog.show();
    }/*
    隐藏对话框
    */
    protected void hideProgressDialog(){
        if (mProgressDialog!=null&&mProgressDialog.isShowing()){
            mProgressDialog.hide();
            mProgressDialog=null;
        }
    }
}
