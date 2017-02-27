package com.example.administrator.flash.core.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.administrator.flash.core.entity.FileInfo;

/**
 * Created by KingHua on 2017/2/27.
 */
public abstract class SelectedFileListChangedBroadcastReceiver extends BroadcastReceiver {
    public static final String TAG=SelectedFileListChangedBroadcastReceiver.class.getSimpleName();
    public static final String ACTION_CHOOSE_FILE_CHANGED="ACTION_CHOOSE_FILE_CHANGED";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        if (action.equals(ACTION_CHOOSE_FILE_CHANGED)){
            Log.i(TAG,"ACTION_CHOOSE_FILE_CHANGED==>");
            onSelectedFileChanged();
        }
    }
    public abstract void onSelectedFileChanged();
}
