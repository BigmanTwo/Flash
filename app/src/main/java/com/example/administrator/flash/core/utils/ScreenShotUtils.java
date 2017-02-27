package com.example.administrator.flash.core.utils;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

/**
 * Created by KingHua on 2017/2/20.
 */
public class ScreenShotUtils {
    /**创建缩略图
     * @param filePath
     * @return
     */
    public static Bitmap  createVideoThumbnail(String filePath){
        Bitmap bitmap= ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Images.Thumbnails.MICRO_KIND);
        return bitmap;
    }

    /**将图片转换成指定宽高
     * @param source
     * @param width
     * @param height
     * @return
     */
    public static Bitmap extractThumnail(Bitmap source,int width,int height){
        Bitmap bitmap=ThumbnailUtils.extractThumbnail(source,width,height);
        return bitmap;
    }
}
