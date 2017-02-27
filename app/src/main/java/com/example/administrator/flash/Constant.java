package com.example.administrator.flash;

import com.example.administrator.flash.core.entity.FileInfo;

import java.util.Comparator;
import java.util.Map;

/**
 * 常量
 * Created by Administrator on 2017/2/16.
 */
public class Constant {
/**
 * 默认的wifi  SSID
 */
    public static final String DEFAULT_SSID="XD_HOTSPOT";
//    serverSocket默认IP
    public static final String DEFAULT_SERVER_IP="192.168.43.1";
//    wifi连上时未分配默认的IP地址
    public static final String DEFAULT_UNKOWN_IP="0.0.0.0";
//最大尝试数
    public static final int DEFAULT_TRY_TIME=10;
//    文件传输监听默认端口
    public static final int DEFAULT_SERVER_POST=8080;
//    UDP通信服务默认端口
    public static final int  DEFAULT_COM_POST=8099;
//    android微型服务器默认端口
    public static final int DEFAULT_MICRO_SERVER_POST=3999;
//    wifi scan result key
    public static final String KEY_IP_POST_INFO="KEY_IP_POST_INFO";
    public static final String KEY_SCAN_RESULT="KEY_SCAN_RESULT";
//    网页传标识
    public static final String KEY_WEB_TRANSFER_FLAG="KEY_WEB_TRANSFER_FLAG";
//    文件发送方与文件传输方的通信
    public static final String MSG_FILE_RECEIVE_INIT="MSG_FILE_RECEIVE_INIT";
    public static final String MSG_FILE_RECEIVE_SUCCESS="MSG_FILE_RECEIVE_SUCCESS";
    public static final String MSG_FILE_SEND_START="MSG_FILE_SEND_START";
//    FileInfoMap默认的Comparator
    public static final Comparator<Map.Entry<String,FileInfo>> DEFAULT_COMPARATOR=new Comparator<Map.Entry<String, FileInfo>>() {
        @Override
        public int compare(Map.Entry<String, FileInfo> lhs, Map.Entry<String, FileInfo> rhs) {

            return 0;
        }
    };
    //    FileInfoMap默认的Comparator2

    public static final Comparator<FileInfo> DEFAULT_COMPARATOR2=new Comparator<FileInfo>() {
        @Override
        public int compare(FileInfo lhs, FileInfo rhs) {
            return 0;
        }
    };
//    项目地址
    public static final String GITHUB_PROJECT_SITE="";
//    assets资源名称
    public static final String NAME_FILE_TEMPLATE="file.template";
    public static final String NAME_CLASSIFY_TEMPLATE="classify.template";


}
