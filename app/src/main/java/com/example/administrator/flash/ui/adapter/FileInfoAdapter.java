package com.example.administrator.flash.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.flash.AppContext;
import com.example.administrator.flash.R;
import com.example.administrator.flash.common.CommonAdapter;
import com.example.administrator.flash.core.entity.FileInfo;

import java.util.List;

/**
 * Created by KingHua on 2017/2/22.
 */
public class FileInfoAdapter extends CommonAdapter<FileInfo> {
    /*文件类型标识*/
    private int type=FileInfo.TYPE_APK;
    public FileInfoAdapter(Context context, List<FileInfo> list) {
        super(context, list);
    }
    public FileInfoAdapter(Context context, List<FileInfo> list,int type) {
        super(context, list);
        this.type=type;
    }

    @Override
    public View convertView(int position, View convertView) {
        FileInfo fileInfo=getList().get(position);
        Log.i("FilePath------>",fileInfo.getFilePath());
        Log.i("fileInfo==null---->",(fileInfo==null || fileInfo.getSize()<=0)+"");
        if (type==FileInfo.TYPE_APK){
            ApkViewHolder viewHolder=null;
            if (convertView==null){
                convertView=View.inflate(getContext(), R.layout.item_apk,null);
                viewHolder=new ApkViewHolder();
                viewHolder.iv_shortcut= (ImageView) convertView.findViewById(R.id.iv_shortcuta);
                viewHolder.iv_ok_tick= (ImageView) convertView.findViewById(R.id.iv_ok_ticka);
                viewHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_namea);
                viewHolder.tv_size= (TextView) convertView.findViewById(R.id.tv_size);
                convertView.setTag(viewHolder);
            }else {
                viewHolder= (ApkViewHolder) convertView.getTag();
            }
            if (getList()!=null && getList().get(position)!=null){
                viewHolder.iv_shortcut.setImageBitmap(fileInfo.getBitmap());
                viewHolder.tv_name.setText(fileInfo.getName()==null?"":fileInfo.getName());
                viewHolder.tv_size.setText(fileInfo.getSizeDesc()==null?"":fileInfo.getSizeDesc());
                if (AppContext.getAppContext().isExist(fileInfo)){
                    viewHolder.iv_ok_tick.setVisibility(View.VISIBLE);
                }else {
                    viewHolder.iv_ok_tick.setVisibility(View.GONE);
                }
            }

        }else if (type==FileInfo.TYPE_JPG){
            JpgViewHolder viewHolder=null;
            if (convertView==null){
                convertView=View.inflate(getContext(),R.layout.item_jpg,null);
                viewHolder=new JpgViewHolder();
                viewHolder.iv_shortcut= (ImageView) convertView.findViewById(R.id.iv_shortcuts);
                viewHolder.iv_ok_tick= (ImageView) convertView.findViewById(R.id.iv_ok_ticks);
                convertView.setTag(viewHolder);
            }else {
                viewHolder= (JpgViewHolder) convertView.getTag();
            }

            if (getList()!=null && getList().get(position)!=null){
//                viewHolder.iv_shortcut.setImageBitmap(fileInfo.getBitmap());

                Glide   .with(getContext())
                        .load(fileInfo.getFilePath())
                        .centerCrop()
                        .placeholder(R.mipmap.icon_jpg)
                        .crossFade()
                        .into(viewHolder.iv_shortcut);

                if (AppContext.getAppContext().isExist(fileInfo)){
                    viewHolder.iv_ok_tick.setVisibility(View.VISIBLE);
                }else {
                    viewHolder.iv_ok_tick.setVisibility(View.GONE);
                }
            }

        }else if (type==FileInfo.TYPE_MP3){
            Mp3ViewHolder viewHolder=null;
            if (convertView==null){
                convertView=View.inflate(getContext(),R.layout.item_mp3,null);
                viewHolder=new Mp3ViewHolder();
                viewHolder.iv_shortcut= (ImageView) convertView.findViewById(R.id.iv_shortcut3);
                viewHolder.iv_ok_tick= (ImageView) convertView.findViewById(R.id.iv_ok_tick3);
                viewHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_name3);
                viewHolder.tv_size= (TextView) convertView.findViewById(R.id.tv_size3);
                convertView.setTag(viewHolder);
            }else {
                viewHolder= (Mp3ViewHolder) convertView.getTag();
            }
            if (getList()!=null && getList().get(position)!=null){
                viewHolder.iv_shortcut.setImageBitmap(fileInfo.getBitmap());
                viewHolder.tv_name.setText(fileInfo.getName()==null?"":fileInfo.getName());
                viewHolder.tv_size.setText(fileInfo.getSizeDesc()==null?"":fileInfo.getSizeDesc());
                if (AppContext.getAppContext().isExist(fileInfo)){
                    viewHolder.iv_ok_tick.setVisibility(View.VISIBLE);
                }else {
                    viewHolder.iv_ok_tick.setVisibility(View.GONE);
                }
            }
        }else if (type==FileInfo.TYPE_MP4){
            Mp4ViewHolder viewHolder=null;
            if (convertView==null){
                convertView=View.inflate(getContext(),R.layout.item_mp4,null);
                viewHolder=new Mp4ViewHolder();
                viewHolder.iv_shortcut= (ImageView) convertView.findViewById(R.id.iv_shortcut4);
                viewHolder.iv_ok_tick= (ImageView) convertView.findViewById(R.id.iv_ok_tick4);
                viewHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_name4);
                viewHolder.tv_size= (TextView) convertView.findViewById(R.id.tv_size4);
                convertView.setTag(viewHolder);
            }else {
                viewHolder= (Mp4ViewHolder) convertView.getTag();
            }
            if (getList()!=null && getList().get(position)!=null){
                viewHolder.iv_shortcut.setImageBitmap(fileInfo.getBitmap());
                viewHolder.tv_name.setText(fileInfo.getName()==null?"":fileInfo.getName());
                viewHolder.tv_size.setText(fileInfo.getSizeDesc()==null?"":fileInfo.getSizeDesc());
                if (AppContext.getAppContext().isExist(fileInfo)){
                    viewHolder.iv_ok_tick.setVisibility(View.VISIBLE);
                }else {
                    viewHolder.iv_ok_tick.setVisibility(View.GONE);
                }
            }
        }

        return convertView;
    }
    static class ApkViewHolder{
        ImageView iv_shortcut;
        ImageView iv_ok_tick;
        TextView tv_name;
        TextView tv_size;
    }
    static class JpgViewHolder{
        ImageView iv_shortcut;
        ImageView iv_ok_tick;
    }
    static class Mp3ViewHolder{
        ImageView iv_shortcut;
        ImageView iv_ok_tick;
        TextView tv_name;
        TextView tv_size;
    }
    static class Mp4ViewHolder{
        ImageView iv_shortcut;
        ImageView iv_ok_tick;
        TextView tv_name;
        TextView tv_size;
    }
}
