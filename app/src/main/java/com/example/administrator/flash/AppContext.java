package com.example.administrator.flash;

import android.app.Application;
import android.util.Log;

import com.example.administrator.flash.core.entity.FileInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by KingHua on 2017/2/22.
 */
public class AppContext extends Application {
    /*主要的线程*/
    public static Executor MAIN_EXECUTOR= Executors.newFixedThreadPool(5);
    /*文件传送单线程*/
    public static Executor FILE_SENDER_EXECUTOR=Executors.newSingleThreadExecutor();
    /*全局变量的上下文*/
    static AppContext appContext;
    /*文件收发方式采用map形式*/
    Map<String,FileInfo> mFileInfoMap=new HashMap<>();
    Map<String,FileInfo> mReceiverInfoMap=new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        this.appContext=this;
    }

    /***
     * 获取全局变量appContext
     * @return
     */
    public static AppContext getAppContext(){
        return appContext;
    }
    //===================================================================
    //===================================================================
    //发送方
    /***
     * 添加一个FileInfo
     * @param fileInfo
     */
    public void addFileInfo(FileInfo fileInfo){
        if (!mFileInfoMap.containsValue(fileInfo.getFilePath())){
            mFileInfoMap.put(fileInfo.getFilePath(),fileInfo);
        }
    }

    /****
     * 更新FileInfo
     * @param fileInfo
     */
    public void updateFileInfo(FileInfo fileInfo){
        mFileInfoMap.put(fileInfo.getFilePath(),fileInfo);
    }

    /***
     * 删除一个FileInfo
     * @param fileInfo
     */
    public void delFileInfo(FileInfo fileInfo){
        if (mFileInfoMap.containsValue(fileInfo.getFilePath())){
            mFileInfoMap.remove(fileInfo.getFilePath());
        }
    }

    /***
     * 是否存在指定FileInfo
     * @param fileInfo
     * @return
     */
    public boolean isExist(FileInfo fileInfo){
        if (mFileInfoMap==null){
            return false;
        }
        Log.i("FilePath------>",fileInfo.getFilePath());
        return mFileInfoMap.containsKey(fileInfo.getFilePath());
    }

    /***
     * 判断FileInfoMap是否存在
     * @return
     */
    public boolean isFileInfoMapExist(){
        if (mFileInfoMap!=null||mFileInfoMap.size()>0){
            return true;
        }
        return false;
    }

    /***
     * 获取全局的FileInfoMap
     * @return
     */
    public Map<String,FileInfo> getmFileInfoMap(){return mFileInfoMap;}

    /***
     *获取发送文件总的长度
     * @return
     */
    public  long getAllFileInfoSendSize(){
        long total=0;
        for (FileInfo fileInfo:mFileInfoMap.values()){
            total=total+fileInfo.getSize();
        }
        return total;
    }



    //===================================================================
    //===================================================================
    //接收方
    /***
     * 添加一个FileInfo
     * @param fileInfo
     */
    public void addReceiverFileInfo(FileInfo fileInfo){
        if (!mReceiverInfoMap.containsValue(fileInfo.getFilePath())){
            mReceiverInfoMap.put(fileInfo.getFilePath(),fileInfo);
        }
    }

    /****
     * 更新FileInfo
     * @param fileInfo
     */
    public void updateReceiverFileInfo(FileInfo fileInfo){
        mReceiverInfoMap.put(fileInfo.getFilePath(),fileInfo);
    }

    /***
     * 删除一个FileInfo
     * @param fileInfo
     */
    public void delReceiverFileInfo(FileInfo fileInfo){
        if (mReceiverInfoMap.containsValue(fileInfo.getFilePath())){
            mReceiverInfoMap.remove(fileInfo.getFilePath());
        }
    }
    /***
     * 判断FileInfoMap是否存在
     * @return
     */
    public boolean isReceiverFileInfoMapExist(){
        if (mReceiverInfoMap!=null&&mReceiverInfoMap.size()>0){
            return true;
        }
        return false;
    }

    /***
     * 获取全局的FileInfoMap
     * @return
     */
    public Map<String,FileInfo> getmReceiverFileInfoMap(){return mReceiverInfoMap;}

    /***
     *获取发送文件总的长度
     * @return
     */
    public  long getAllReceiverFileInfoSendSize(){
        long total=0;
        for (FileInfo fileInfo:mReceiverInfoMap.values()){
            total=total+fileInfo.getSize();
        }
        return total;
    }
}
