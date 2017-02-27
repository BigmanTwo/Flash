package com.example.administrator.flash.ui.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.flash.AppContext;
import com.example.administrator.flash.Constant;
import com.example.administrator.flash.R;
import com.example.administrator.flash.core.entity.FileInfo;
import com.example.administrator.flash.core.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 文件发送列表
 * Created by KingHua on 2017/2/27.
 */
public class FileInfoSelectedAdapter extends BaseAdapter{
    private Context mContext;
    private Map<String,FileInfo> mDateHashMap;
    private String[] mKeys;


    List<Map.Entry<String,FileInfo>> fileInfoMapList;
    OnDataListChangedListener mOnDataListChangedListener;

    public void setOnDataListChangedListener(OnDataListChangedListener mOnDataListChangedListener) {
        this.mOnDataListChangedListener = mOnDataListChangedListener;
    }

    public FileInfoSelectedAdapter(Context mContext) {
        this.mContext = mContext;
        mDateHashMap= AppContext.getAppContext().getmFileInfoMap();
        fileInfoMapList=new ArrayList<>(mDateHashMap.entrySet());
        Collections.sort(fileInfoMapList, Constant.DEFAULT_COMPARATOR);
    }
    @Override
    public int getCount() {
        return fileInfoMapList.size();
    }

    @Override
    public Object getItem(int position) {
        return fileInfoMapList.get(position).getValue();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final FileInfo fileInfo= (FileInfo) getItem(position);
        FileSenderHolder viewHolder=null;
        if (convertView==null){
            convertView=View.inflate(mContext, R.layout.item_transfer,null);
            viewHolder=new FileSenderHolder();
            viewHolder.iv_shortcut= (ImageView) convertView.findViewById(R.id.iv_shortcut);
            viewHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.btn_operation= (Button) convertView.findViewById(R.id.btn_operation);
            viewHolder.iv_tick= (ImageView) convertView.findViewById(R.id.iv_tick);
            viewHolder.tv_progress= (TextView) convertView.findViewById(R.id.tv_progress);
            viewHolder.pd_file= (ProgressBar) convertView.findViewById(R.id.pb_file);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (FileSenderHolder) convertView.getTag();
        }
        if (fileInfo!=null){
            viewHolder.pd_file.setVisibility(View.VISIBLE);
            viewHolder.iv_tick.setVisibility(View.VISIBLE);
            viewHolder.btn_operation.setVisibility(View.VISIBLE);
            viewHolder.iv_tick.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_del));
            if (FileUtils.isApkFile(fileInfo.getFilePath()) || FileUtils.isMp4File(fileInfo.getFilePath())){
                viewHolder.iv_shortcut.setImageBitmap(fileInfo.getBitmap());
            }else if (FileUtils.isMp3File(fileInfo.getFilePath())){
                viewHolder.iv_shortcut.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_mp3));
            }else if (FileUtils.isJpgFile(fileInfo.getFilePath())){
                Glide.with(mContext)
                        .load(fileInfo.getFilePath())
                        .centerCrop()
                        .placeholder(R.mipmap.icon_jpg)
                        .crossFade()
                        .into(viewHolder.iv_shortcut);

            }
            viewHolder.tv_name.setText(fileInfo.getName());
            viewHolder.tv_progress.setText(FileUtils.getFileSize(fileInfo.getSize()));
            viewHolder.iv_tick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppContext.getAppContext().getmFileInfoMap().remove(fileInfo.getFilePath());
                    notifyDataSetChanged();
                    if (mOnDataListChangedListener!=null){
                        mOnDataListChangedListener.onDataChange();
                    }
                }
            });
        }
        return convertView;
    }
    static class FileSenderHolder{
        ImageView iv_shortcut;
        TextView tv_name;
        TextView tv_progress;
        ProgressBar pd_file;
        Button btn_operation;
        ImageView iv_tick;
    }
    /*数据改变监听*/
    public interface OnDataListChangedListener{
        void onDataChange();
    }

    @Override
    public void notifyDataSetChanged() {
        mDateHashMap=AppContext.getAppContext().getmFileInfoMap();
        fileInfoMapList=new ArrayList<>();
        Collections.sort(fileInfoMapList,Constant.DEFAULT_COMPARATOR);
        super.notifyDataSetChanged();
    }
}
