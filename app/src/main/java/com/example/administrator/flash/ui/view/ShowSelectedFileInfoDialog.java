package com.example.administrator.flash.ui.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.flash.AppContext;
import com.example.administrator.flash.R;
import com.example.administrator.flash.core.entity.FileInfo;
import com.example.administrator.flash.core.receiver.SelectedFileListChangedBroadcastReceiver;
import com.example.administrator.flash.core.utils.FileUtils;
import com.example.administrator.flash.ui.adapter.FileInfoSelectedAdapter;

import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 显示选中文件对话框
 * Created by KingHua on 2017/2/23.
 */
public class ShowSelectedFileInfoDialog {

    Context context;
    @Bind(R.id.btn_operation)
    Button btn_Operation;
    @Bind(R.id.tv_title)
    TextView tv_Title;
    @Bind(R.id.lv_result)
    ListView lv_Result;
    @Bind(R.id.rv_top)
    RelativeLayout rv_Top;

    AlertDialog mAlertDialog;
    FileInfoSelectedAdapter mFileInfoSelectedAdapter;
    public ShowSelectedFileInfoDialog(Context context) {
        this.context = context;
        View view = View.inflate(context, R.layout.view_show_selected_file_info_dialog, null);
        ButterKnife.bind(this, view);
        String title=getAllSelectedFilesDes();
        tv_Title.setText(title);
        mFileInfoSelectedAdapter=new FileInfoSelectedAdapter(context);
        mFileInfoSelectedAdapter.setOnDataListChangedListener(new FileInfoSelectedAdapter.OnDataListChangedListener() {
            @Override
            public void onDataChange() {
                if (mFileInfoSelectedAdapter.getCount()==0){
                    hide();
                }
                tv_Title.setText(getAllSelectedFilesDes());
                sendUpdateSelectedFilesBR();
            }
        });
        lv_Result.setAdapter(mFileInfoSelectedAdapter);
        mAlertDialog=new AlertDialog.Builder(context)
                .setView(view)
                .create();
    }
    @OnClick({R.id.btn_operation})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_operation: {
                clearAllSelectedFiles();
                sendUpdateSelectedFilesBR();//发送更新选中文件的广播
                break;
            }
        }
    }
    /*清除所选中文件中的文件*/
    private void clearAllSelectedFiles(){
        AppContext.getAppContext().getmFileInfoMap().clear();
        if (mFileInfoSelectedAdapter!=null){
            mFileInfoSelectedAdapter.notifyDataSetChanged();
        }
        hide();
    }
    /**
     * 获取选中对话框的Tiltle
     * @return
     */
    private String getAllSelectedFilesDes(){
        String title=null;
        long totalSize=0;
        Set<Map.Entry<String,FileInfo>>    entrySet= AppContext.getAppContext().getmFileInfoMap().entrySet();
        for (Map.Entry<String,FileInfo> entryFile:entrySet){
            FileInfo info=entryFile.getValue();
            totalSize=totalSize+info.getSize();
        }
        title=context.getResources().getString(R.string.str_selected_file_info_detail)
                .replace("{count}",String.valueOf(entrySet.size()))
                .replace("{size}", FileUtils.getFileSize(totalSize));
        return title;
    }
    /*发送更新选中文件列表的广播*/
    private void sendUpdateSelectedFilesBR(){
            this.context.sendBroadcast(new Intent(SelectedFileListChangedBroadcastReceiver.ACTION_CHOOSE_FILE_CHANGED));
    }
    /*隐藏dialog*/
    public void hide(){
        if (mAlertDialog!=null){
            mAlertDialog.hide();
        }
    }
    /*显示dialog*/
    public void show(){
        if (mAlertDialog!=null){
            notifyDataSetChanged();
            tv_Title.setText(getAllSelectedFilesDes());
            mAlertDialog.show();
        }
    }
    public void notifyDataSetChanged(){
        if (mFileInfoSelectedAdapter!=null){
            mFileInfoSelectedAdapter.notifyDataSetChanged();
        }
    }
}
