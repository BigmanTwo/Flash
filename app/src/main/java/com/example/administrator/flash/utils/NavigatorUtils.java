package com.example.administrator.flash.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.administrator.flash.Constant;
import com.example.administrator.flash.core.utils.FileUtils;
import com.example.administrator.flash.ui.ChooseFileActivity;
import com.example.administrator.flash.ui.ChooseReceiveActivity;
import com.example.administrator.flash.ui.FileReceiverActivity;
import com.example.administrator.flash.ui.FileSenderActivity;
import com.example.administrator.flash.ui.ReceiverWaitingActivity;
import com.example.administrator.flash.ui.WebTransferActivity;

import java.io.File;

/**UI导航的工具类
 * Created by Administrator on 2017/2/16.
 */
public class NavigatorUtils {
    /**
     * 跳转到文件选择UI
     * @param context
     * @param isWebTransfer 是否使用网页传
     */
    public static void toChooseFileUI(Context context,boolean isWebTransfer){
            if (context==null){
                throw new RuntimeException("Context not be null!");
            }
        Intent  intent=new Intent (context,ChooseFileActivity.class);
        intent.putExtra(Constant.KEY_WEB_TRANSFER_FLAG,isWebTransfer);
        context.startActivity(intent);
    }

    /**
     * 跳转选择文件夹
     * @param context
     */
    public static void toChooseFileUI(Context context){
        toChooseFileUI(context,false);
    }

    /**跳转到选择文件夹接受者UI
     * @param context
     */
    public static void toChooseReceiveUI(Context context){
        if (context==null){
            throw new RuntimeException("Context  not be null!");
        }
        Intent intent=new Intent(context,ChooseReceiveActivity.class);
        context.startActivity(intent);
    }

    /**跳转到选择接受者界面
     * @param context
     */
    public static void toReceiveWaitingUI(Context context){
        if (context==null){
            throw  new RuntimeException("Context not be null!");
        }
        Intent intent=new Intent(context, ReceiverWaitingActivity.class);
        context.startActivity(intent);
    }

    /**跳转到文件发送列表UI
     * @param context
     */
    public static void toFileSenderListUI(Context context){
        if (context==null){
            throw new RuntimeException("Context not be null!");
        }
        Intent intent=new Intent(context, FileSenderActivity.class);
        context.startActivity(intent);
    }

    /**跳转到文件接受列表
     * @param context
     * @param bundle
     */
    public static void toFileReceiverListUI(Context context, Bundle bundle){
        if (context==null){
            throw new RuntimeException("Context not be null!");
        }
        Intent intent=new Intent(context, FileReceiverActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }

    /**打开指定的app 文件储存文件夹
     ** @param context
     */
    public static void toSystemFileChooser(Context context){
        if (context==null){
            throw new RuntimeException("Context not be null!");
        }
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
//        还差一个地址
        File file=new File(FileUtils.getRootDirPath());
        Uri uri=Uri.fromFile(file);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri,"*/*");
        context.startActivity(intent);
    }

    /**条转到网页传UI
     * @param context
     */
    public static void toWebTransferUI(Context context){
        if (context==null){
            throw new RuntimeException("Context not be null!");
        }
        Intent intent=new Intent(context, WebTransferActivity.class);
        context.startActivity(intent);
    }
}
