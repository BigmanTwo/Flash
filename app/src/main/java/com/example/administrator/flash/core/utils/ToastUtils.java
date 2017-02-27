package com.example.administrator.flash.core.utils;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by KingHua on 2017/2/22.
 */
public class ToastUtils {
    static Toast toast=null;
    public static void show(Context context,String text){
        try {
            if (toast != null) {
                toast.setText(text);
            } else {
                toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            }
            toast.show();
        }catch (Exception e){
            Looper.prepare();
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }

    }
}
