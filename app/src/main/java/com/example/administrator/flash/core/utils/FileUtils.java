package com.example.administrator.flash.core.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.administrator.flash.R;
import com.example.administrator.flash.core.entity.FileInfo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KingHua on 2017/2/17.
 */
public class FileUtils {
    private static final String TAG=FileUtils.class.getSimpleName();
//    自定义文件类型
    private static final int TYPE_APK=1;
    private static final int  TYPE_JPEG=2;
    private static final int TYPE_MP3=3;
    private static final int TYPE_MP4=4;


    /**
     * 默认的根目录
     */
    public static final String DEFAULT_ROOT_PATH="/mnt/download/flash/";
    /**
     * 默认的缩略图目录
     */
    public static final String DEFAULT_SCREENSHOT_PATH="/mnt/kc_screenshot/";
//    小数的格式化
    public static final DecimalFormat FORMAT=new DecimalFormat("####.##");
    public static final DecimalFormat FORMAT_ONE=new DecimalFormat("####.#");

    /**
     * 储存卡获取指定文件
     * @param context
     * @param extendsion
     * @return
     */
    public static List<FileInfo> getSpecificTypeFile(Context context,String[] extension){
            List<FileInfo> fileInfoList=new ArrayList<>();
//        内存卡文件的uri
        //内存卡文件的Uri
        Uri fileUri= MediaStore.Files.getContentUri("external");
        //筛选列，这里只筛选了：文件路径和含后缀的文件名
        String[] projection=new String[]{
                MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.TITLE
        };

        //构造筛选条件语句
        String selection="";
        for(int i=0;i<extension.length;i++)
        {
            if(i!=0)
            {
                selection=selection+" OR ";
            }
            selection=selection+ MediaStore.Files.FileColumns.DATA+" LIKE '%"+extension[i]+"'";
        }
        Log.i(TAG, "selection===>>> " + selection);
        //按时间降序条件
        String sortOrder = MediaStore.Files.FileColumns.DATE_MODIFIED;
        Log.i(TAG, "sortOrder ===>>> " + sortOrder);
        Cursor cursor = context.getContentResolver().query(fileUri, projection, selection, null, sortOrder);
        Log.i(TAG, "cursor ===>>> " + (cursor!=null));
        if (cursor!=null){
            while (cursor.moveToNext()) {
                try {
                    String data = cursor.getString(0);
                    Log.i(TAG, "data ===>>> " + data);
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setFilePath(data);
                    long size = 0;
                    File file = new File(data);
                    size = file.length();
                    fileInfo.setSize(size);
                    fileInfoList.add(fileInfo);
                }catch (Exception e){
                    Log.i(TAG, "Exception ===>>> " + e.getMessage());
                }
            }
        }
        cursor.close();
        Log.i(TAG, "getSize ===>>> " + fileInfoList.size());
        return fileInfoList;
    }
    public static List<FileInfo> getSpecificTypeFiles(Context context, String[] extension){
        List<FileInfo> fileInfoList = new ArrayList<FileInfo>();

        //内存卡文件的Uri
        Uri fileUri= MediaStore.Files.getContentUri("external");
        //筛选列，这里只筛选了：文件路径和含后缀的文件名
        String[] projection=new String[]{
                MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.TITLE
        };

        //构造筛选条件语句
        String selection="";
        for(int i=0;i<extension.length;i++)
        {
            if(i!=0)
            {
                selection=selection+" OR ";
            }
            selection=selection+ MediaStore.Files.FileColumns.DATA+" LIKE '%"+extension[i]+"'";
        }
        Log.i(TAG, "selection===>>> " + selection);
        //按时间降序条件
        String sortOrder = MediaStore.Files.FileColumns.DATE_MODIFIED;
        Log.i(TAG, "sortOrder ===>>> " + sortOrder);
        Cursor cursor = context.getContentResolver().query(fileUri, projection, selection, null, sortOrder);
        if(cursor != null){
            while (cursor.moveToNext()){
                try{
                    String data = cursor.getString(0);
                    Log.i(TAG, "data ===>>> " + data);
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setFilePath(data);

                    long size = 0;
                    try{
                        File file = new File(data);
                        size = file.length();
                        fileInfo.setSize(size);
                    }catch(Exception e){

                    }
                    fileInfoList.add(fileInfo);
                }catch (Exception e){
                    Log.i("FileUtils", "------>>>" + e.getMessage());
                }

            }
        }
        Log.i(TAG, "getSize ===>>> " + fileInfoList.size());
        return fileInfoList;
    }
    /**获取指定文件
     * @param context
     * @param flieName
     * @return
     */
    public static FileInfo getFileInfo(Context context,String flieName){
        List<FileInfo> fileList=getSpecificTypeFile(context,new String[]{flieName});
        if (fileList==null && fileList.size()==0){
            return null;
        }
        return fileList.get(0);
    }

    /**转化完整信息的FileInfo
     * @param context
     * @param infos
     * @param type
     * @return
     */
    public static List<FileInfo> getDetailFileInfos(Context context,List<FileInfo> infos,int type){
        if (infos==null || infos.size()<=0){
            return infos;
        }
        for (FileInfo info:infos) {
            if (info!=null){
                 info.setName(getFileName(info.getFilePath()));
                info.setSizeDesc(getFileSize(info.getSize()));
                if (type==FileInfo.TYPE_APK){
                    info.setBitmap(FileUtils.drawableToBitmap(FileUtils.getApkThumbnail(context,info.getFilePath())));
                }else if (type==FileInfo.TYPE_JPG){

                }else if (type==FileInfo.TYPE_MP3){

                }else if (type==FileInfo.TYPE_MP4){
                    info.setBitmap(FileUtils.getScreenshotBitmap(context,info.getFilePath(),FileInfo.TYPE_MP4));

                }
                info.setFileType(type);
            }

        }
        return infos;
    }

    /**根据传入的byte数量装换成byte，Kbyte，Mbyte，Gbyte单位的字符串
     * @param size
     * @return
     */
    public static String getFileSize(long size) {
        if (size<0){
            return "0B";
        }
        double value=0;
        if (size/1024<1){
            return size+"B";
        }else if (size/(1024*1024)<1){
            value=size/1024f;
            return FORMAT.format(value)+"KB";
        }else  if (size/(1024*1024*1024)<1){
            value=((size*100)/(1024*1024))/100f;
            return FORMAT.format(value)+"MB";
        }else {
            value=((size*1001)/(1024*1024*1024))/100f;
            return FORMAT.format(value)+"GB";
        }


    }

    /**根据文件路径获取文件名称
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        if (filePath==null || filePath.equals("")){
            return "";
        }
        return filePath.substring(filePath.lastIndexOf("/")+1);
    }

    /**Drawable转Bitmap
     * @param drawable
     * @return
     */
    public  static Bitmap drawableToBitmap(Drawable drawable){
        if (drawable==null){
            return null;
        }
//        取drawable的长宽
        int w=drawable.getIntrinsicWidth();
        int h=drawable.getIntrinsicHeight();
        //取drawable的颜色
        Bitmap.Config config=drawable.getOpacity()!= PixelFormat.OPAQUE?Bitmap.Config.ARGB_8888:
                Bitmap.Config.RGB_565;
        //建立对应的bitmap
        Bitmap bitmap=Bitmap.createBitmap(w,h,config);
        //建立对应的bitmap画布
        Canvas canvas=new Canvas(bitmap);

        drawable.setBounds(0,0,w,h);
        //把drawable的内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /**获取APK文件的logo图标
     * @param context
     * @param apk_path
     * @return
     */
    public static Drawable getApkThumbnail(Context context,String apk_path){
    if (context==null){
        return null;
    }
        PackageManager packageManager=context.getPackageManager();
        PackageInfo packageInfo=packageManager.getPackageArchiveInfo(apk_path,PackageManager.GET_ACTIVITIES);
        ApplicationInfo appInfo=packageInfo.applicationInfo;
        appInfo.sourceDir=apk_path;
        appInfo.publicSourceDir=apk_path;
        if (appInfo!=null){
            Drawable apk_icon=appInfo.loadIcon(packageManager);
            return apk_icon;
        }
        return null;
    }

    /**得到缩略图
     * @param context
     * @param apk_path
     * @param type
     * @return
     */
    public static Bitmap getScreenshotBitmap(Context context,String apk_path,int type){
        Bitmap bitmap=null;
        switch (type){
            case  TYPE_APK:
                Drawable drawable=getApkThumbnail(context,apk_path);
                if (drawable!=null){
                    bitmap=drawableToBitmap(drawable);
                }else {
                    bitmap= BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
                }
                break;
            case TYPE_JPEG:
                try {
                    bitmap=BitmapFactory.decodeStream(new FileInputStream(new File(apk_path)));

                } catch (FileNotFoundException e) {
                    bitmap=BitmapFactory.decodeResource(context.getResources(),R.mipmap.icon_jpg);
                }
                bitmap=ScreenShotUtils.extractThumnail(bitmap,100,100);
                break;
            case TYPE_MP3:
                bitmap=BitmapFactory.decodeResource(context.getResources(),R.mipmap.icon_mp3);
                bitmap=ScreenShotUtils.extractThumnail(bitmap,100,100);
                break;
            case TYPE_MP4:
                try {
                    bitmap=ScreenShotUtils.createVideoThumbnail(apk_path);
                }catch (Exception e){
                    bitmap=BitmapFactory.decodeResource(context.getResources(),R.mipmap.icon_mp4);
                }
                bitmap=ScreenShotUtils.extractThumnail(bitmap,100,100);
                break;


        }
        return bitmap;
    }

    /*** 获取文件的根目录
     * @return
     */
    public static String getRootDirPath(){
        String path=DEFAULT_ROOT_PATH;
        if (Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)){
            path=Environment.getExternalStorageState()+"/flash/";
        }
        return path;
    }

    /**
     * 获取文件缩略图文件
     * @return
     */
    public static String getScreenshotDirPath(){
        String path=DEFAULT_SCREENSHOT_PATH;
        if (Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)){
            path=Environment.getExternalStorageState()+"/kc_screenshot/";
        }
        return path;
    }

    /**获取指定文件夹
     * @param type
     * @return
     */
    public static String getSpecifyDirPath(int type){
        String dirPath=getRootDirPath();
        switch (type){
            case FileInfo.TYPE_APK:
                dirPath=dirPath+"apk/";
                break;
            case FileInfo.TYPE_JPG:
                dirPath=dirPath+"jpg/";
                break;
            case FileInfo.TYPE_MP3:
                dirPath=dirPath+"mp3/";
                break;
            case FileInfo.TYPE_MP4:
                dirPath=dirPath+"mp4/";
                break;
            default:
                dirPath=dirPath+"other/";
                break;
        }
        return dirPath;
    }

    /**生成本地路径文件
     * @param url
     * @return
     */
    public static File createLocalFile(String url){
        String fileName=getFileName(url);
        String dir=getRootDirPath();
        if (fileName.lastIndexOf(FileInfo.EXTEND_APK)>0){
            dir=getSpecifyDirPath(FileInfo.TYPE_APK);
        }else if (fileName.lastIndexOf(FileInfo.EXTEND_JPG)>0){
            dir=getSpecifyDirPath(FileInfo.TYPE_JPG);
        }else if (fileName.lastIndexOf(FileInfo.EXTEND_MP3)>0){
            dir=getSpecifyDirPath(FileInfo.TYPE_MP3);
        }else if (fileName.lastIndexOf(FileInfo.EXTEND_MP4)>0){
            dir=getSpecifyDirPath(FileInfo.TYPE_MP4);
        }else {
            dir=getSpecifyDirPath(-1);
        }
        File dirFile=new File(dir);
        if (!dirFile.exists()){
            dirFile.mkdirs();
        }
        File file=new File(dirFile,fileName);
        return file;
    }

    /**转换成流量数组
     * result[0]为数值
     * result[1]为单位
     * @param size
     * @return
     */
    public static String[] getFileSizeArrayStr(long size){
        String[] result=new String[2];
        if (size<0){
            result[0]="0";
            result[1]="B";
            return result;
        }
        double value=0f;
        if (size/1024<1){
            result[0]=FORMAT.format(size);
            result[1]="B";
        }else if (size/(1024*1024)<1){
            value=size/1024f;
            result[0]=FORMAT.format(value);
            result[1]="KB";
        }else if (size/(1024*1024*1024)<1){
            value=((size*100)/(1024*1024))/100f;
            result[0]=FORMAT.format(value);
            result[1]="MB";
        }else {
            value=(size*1001/(1024*1024*1024))/100f;
            result[0]=FORMAT.format(value);
            result[1]="GB";
        }
        return result;
    }

    /**转换为时间数组
     * result[0]数值
     * result[1]单位
     * @param second
     * @return
     */
    public static String[] getTimeArrayStr(long second){
        String[] result=new String[2];
        if (second<0){
            result[0]="0";
            result[1]="秒";
            return result;
        }
        double value=0f;
        if (second/(60*1000f)<1){
            result[0]=FORMAT.format(second);
            result[1]="秒";
        }else if (second/(60*60*1000f)<1){
            value=second/(60*1000f);
            result[0]=FORMAT.format(value);
            result[1]="分";
        }else {
            value=second/(60*60*1000f);
            result[0]=FORMAT.format(value);
            result[1]="时";
        }
        return result;
    }

    /**判断是不是apk文件
     * @param filePath
     * @return
     */
    public static boolean isApkFile(String filePath){
        if (filePath==null||filePath.equals("")){
            return false;
        }
        if (filePath.lastIndexOf(FileInfo.EXTEND_APK)>0){
            return true;
        }
        return false;
    }

    /**判断是否是jpg文件
     * @param filePath
     * @return
     */
    public static boolean isJpgFile(String filePath){
        if (filePath==null||filePath.equals("")){
            return false;
        }
        if (filePath.lastIndexOf(FileInfo.EXTEND_JPG)>0 ||filePath.lastIndexOf(FileInfo.EXTEND_JPEG)>0){
            return true;
        }
        return false;
    }

    /**判断是否是png文件
     * @param filePath
     * @return
     */
    public static boolean isPngFile(String filePath){
        if (filePath==null||filePath.equals("")){
            return false;
        }
        if (filePath.lastIndexOf(FileInfo.EXTEND_PNG)>0){
            return true;
        }
        return false;
    }

    /**判断是否是MP3文件
     * @param filePath
     * @return
     */
    public static boolean isMp3File(String filePath){
        if (filePath==null||filePath.equals("")){
            return false;
        }
        if (filePath.lastIndexOf(FileInfo.EXTEND_MP3)>0){
            return true;
        }
        return false;
    }

    /**判断是否是MP4文件
     * @param filePath
     * @return
     */
    public static boolean isMp4File(String filePath){
        if (filePath==null||filePath.equals("")){
            return false;
        }
        if (filePath.lastIndexOf(FileInfo.EXTEND_MP4)>0){
            return true;
        }
        return false;
    }

    /**获取专辑封面
     * @param filePath
     * @return
     */
    public static Bitmap createAlbumArt(final String filePath){
        Bitmap bitmap=null;
        //获取多媒体文件元
        MediaMetadataRetriever retriever=new MediaMetadataRetriever();
        try {
        retriever.setDataSource(filePath);//设置数据
        byte[] embedPic=retriever.getEmbeddedPicture();//得到字节型数据
        bitmap=BitmapFactory.decodeByteArray(embedPic,0,embedPic.length);//转换成图片
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return bitmap;
    }

    /**Bitmap转ByteArray
     * @param bitmap
     * @return
     */
    public static byte[] bitmapToByteArray(Bitmap bitmap){
        if (bitmap==null){
            return null;
        }
        ByteArrayOutputStream arrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,arrayOutputStream);
        return arrayOutputStream.toByteArray();
    }

    /*** Bitmap写入到SDCard
     * @param bitmap
     * @param recPath
     * @return
     */
    public static boolean bitmapToSDCard(Bitmap bitmap,String recPath){
        if (bitmap==null){
            return false;
        }
        File file=new File(recPath);
        try {
            FileOutputStream outputStream=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
            outputStream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**Bitmap装换成指定的千字节
     * @param srcBitmap
     * @param maxBitmap 比如想要32k的，就传入32
     * @return
     */
    public static Bitmap compressBitmap(Bitmap srcBitmap,int maxBitmap){
        ByteArrayOutputStream outputStream=null;
        outputStream=new ByteArrayOutputStream();
        srcBitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        int option=98;
        while (outputStream.toByteArray().length/1024>maxBitmap && option>0){
            outputStream.reset();
            srcBitmap.compress(Bitmap.CompressFormat.PNG,option,outputStream);
            option-=2;
        }
        ByteArrayInputStream inputStream=new ByteArrayInputStream(outputStream.toByteArray());
        Bitmap bitmap=BitmapFactory.decodeStream(inputStream,null,null);//把字节生成图片
        return bitmap;
    }

    /**压缩图片到指定文件中去----图片大小没变文件大小变了（图片的位深度改变）
     * @param srcBitmap
     * @param maxBitmap  最大千字节数
     * @param targetPath 目标图片地址
     * @return
     */
    public  static boolean compressBitmap(Bitmap srcBitmap,int maxBitmap,String targetPath){
        boolean result=false;
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        srcBitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
        int option=98;
        while (baos.toByteArray().length/1024>maxBitmap && option>0){
            baos.reset();
            srcBitmap.compress(Bitmap.CompressFormat.PNG,option,baos);
            option-=2;
        }
        byte[] bytes=baos.toByteArray();
        File targetFile=new File(targetPath);
        if (!targetFile.exists()){
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream outputStream=new FileOutputStream(targetFile);
            outputStream.write(bytes);
            outputStream.close();
            baos.close();
            result= true;
            if (!srcBitmap.isRecycled()){
                srcBitmap.recycle();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**获取接收到文件数
     * @return
     */
    public static int getReceiveFileCount(){
        int count=0;
        File rootDir=new File(getRootDirPath());
        if(rootDir!=null){
            count=getFileCount(rootDir);
        }
        return count;
    }
    /**获取指定文件下的文件数
     * @return
     */
    public static int getFileCount(File filePath){
        int count=0;
        if (filePath!=null&&filePath.exists()){
            for (File file:filePath.listFiles()) {
                if (file.isDirectory()){
                    count=count+getFileCount(file);
                }else {
                    count++;
                }
            }
        }
        return count;
    }

    /**获取收到全部文件的大小
     * @return
     */
    public static String getFileReceiveTotalLength(){
        long total=0;
        File rootDir=new File(getRootDirPath());
        if (rootDir!=null){
            total=getFileLength(rootDir);
        }
        return getFileSize(total);
    }

    /***递归获取指定文件的大小
     * @param rootDir
     * @return
     */
    public static long getFileLength(File rootDir){
        long len=0;
        if (rootDir.exists()&&rootDir!=null){
            for (File file:rootDir.listFiles() ) {
                if (file.isDirectory()){
                    len=len+getFileLength(file);
                }else {
                    len=len+ file.length();
                }
            }
        }
        return len;
    }

    /**打开文件
     * @param context
     * @param fielPath
     */
    public static void openFile(Context context,String fielPath){
        Intent intent=new Intent(Intent.ACTION_VIEW);
        Uri uri=Uri.fromFile(new File(fielPath));
        if (FileUtils.isJpgFile(fielPath)){
            intent.setDataAndType(uri,"image/*");
        }else if (FileUtils.isMp3File(fielPath)){
            intent.setDataAndType(uri,"audio/*");
        }else if (FileUtils.isMp4File(fielPath)){
            intent.setDataAndType(uri,"video/*");
        }
        context.startActivity(intent);
    }

    /**远程的FilePath获取本地的FilePath
     * @param remoteFilePath
     * @return
     */
    public static String getLocalFilePath(String remoteFilePath){
        String localPath="";
        if (FileUtils.isApkFile(remoteFilePath)){
            localPath=getSpecifyDirPath(FileInfo.TYPE_APK)+getFileName(remoteFilePath);
        }else if (FileUtils.isJpgFile(remoteFilePath)){
            localPath=getSpecifyDirPath(FileInfo.TYPE_JPG)+getFileName(remoteFilePath);

        }else if (FileUtils.isMp3File(remoteFilePath)){
            localPath=getSpecifyDirPath(FileInfo.TYPE_MP3)+getFileName(remoteFilePath);

        }else if (FileUtils.isMp4File(remoteFilePath)){
            localPath=getSpecifyDirPath(FileInfo.TYPE_MP4)+getFileName(remoteFilePath);

        }
        return localPath;
    }

    /**判断缩略图是否存在
     *
     * @param fileName
     * @return
     */
    public static boolean isScreenshotExits(String fileName){
        File file=new File(FileUtils.getScreenshotDirPath()+fileName);
        if (file.exists()){
            return true;
        }
        return false;
    }

    /**获取文件缩略图的路径
     * @param fileName
     * @return
     */
    public static String getScreenShotFilePath(String fileName){
        File dirFile=new File(FileUtils.getScreenshotDirPath());
        if (!dirFile.exists()){
            dirFile.mkdirs();
        }
        if (isMp3File(fileName)){
            return FileUtils.getScreenshotDirPath()+"mp3.png";
        }
        return FileUtils.getScreenshotDirPath() + fileName.replace(".", "_") + ".png";
    }
 public synchronized static void autoCreateScreenShot(Context context,String filePath) throws IOException {
     String fileName = FileUtils.getFileName(filePath);
     File screenshotFile = null;
     Bitmap screenshotBitmap = null;
     FileOutputStream fos = null;
     if (FileUtils.isApkFile(filePath)) {
         if (!FileUtils.isScreenshotExits(fileName)) {
             screenshotFile = new File(getScreenShotFilePath(fileName));
             if (!screenshotFile.exists()) {
                 screenshotFile.createNewFile();
             }
             fos = new FileOutputStream(screenshotFile);
             screenshotBitmap = FileUtils.drawableToBitmap(FileUtils.getApkThumbnail(context, filePath));
             screenshotBitmap = ScreenShotUtils.extractThumnail(screenshotBitmap, 96, 96);
             screenshotBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
         }
     } else if (FileUtils.isJpgFile(filePath)) {
         if (!FileUtils.isScreenshotExits(fileName)) {
             screenshotFile = new File(getScreenShotFilePath(fileName));
             if (!screenshotFile.exists()) {
                 screenshotFile.createNewFile();
             }
             fos = new FileOutputStream(screenshotFile);
             screenshotBitmap = FileUtils.getScreenshotBitmap(context, filePath, FileInfo.TYPE_JPG);
             screenshotBitmap = ScreenShotUtils.extractThumnail(screenshotBitmap, 96, 96);
             screenshotBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
         }
     }else if (FileUtils.isMp3File(filePath)) {

             screenshotFile = new File(getScreenShotFilePath(fileName) + "mp3.png");
             if (!screenshotFile.exists()) {
                 screenshotFile.createNewFile();
             }
             fos = new FileOutputStream(screenshotFile);
             screenshotBitmap = FileUtils.getScreenshotBitmap(context, filePath, FileInfo.TYPE_MP3);
             screenshotBitmap = ScreenShotUtils.extractThumnail(screenshotBitmap, 96, 96);
             screenshotBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

         } else if (FileUtils.isMp4File(filePath)) {
         if (!FileUtils.isScreenshotExits(fileName)) {
             screenshotFile = new File(getScreenShotFilePath(fileName));
             if (!screenshotFile.exists()) {
                 screenshotFile.createNewFile();
             }
             fos = new FileOutputStream(screenshotFile);
             screenshotBitmap = FileUtils.getScreenshotBitmap(context, filePath, FileInfo.TYPE_MP4);
             screenshotBitmap = ScreenShotUtils.extractThumnail(screenshotBitmap, 96, 96);
             screenshotBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
         }
     }
             if (screenshotBitmap != null) {
                 screenshotBitmap.recycle();
             }
             if (fos != null) {
                 fos.close();

             }

     }



}