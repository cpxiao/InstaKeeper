package com.cpxiao.instakeeper.service;

import android.app.Service;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.cpxiao.AppConfig;
import com.cpxiao.ViewClick;

/**
 * @author cpxiao
 */
public class ClipBoardService extends Service {
    private static final String TAG = ClipBoardService.class.getSimpleName();
    private static final boolean DEBUG = AppConfig.DEBUG;

    //    private static final String INSTA_HOST = "https://www.instagram.com/p/(.*)";
    private static final String INSTA_HOST = "https://www.instagram.com/p/";

    @Override
    public IBinder onBind(Intent intent) {
        if (DEBUG) {
            Log.d(TAG, "onBind: ");
        }
        return null;
    }

    @Override
    public void onCreate() {
        if (DEBUG) {
            Log.d(TAG, "onCreate: ");
        }
        super.onCreate();
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(mOnPrimaryClipChangedListener);
    }

    @Override
    public void onDestroy() {
        if (DEBUG) {
            Log.d(TAG, "onDestroy: ");
        }
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (DEBUG) {
            Log.d(TAG, "onStartCommand: ");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private ClipboardManager.OnPrimaryClipChangedListener mOnPrimaryClipChangedListener =
            new ClipboardManager.OnPrimaryClipChangedListener() {
                @Override
                public void onPrimaryClipChanged() {
                    if (DEBUG) {
                        Log.d(TAG, "onPrimaryClipChanged: ");
                    }
                    String lastClipboardString = ViewClick.getLastClipboardString(getApplicationContext());

                    if (!TextUtils.isEmpty(lastClipboardString)
                            && lastClipboardString.matches(INSTA_HOST + "(.*)")) {
                        ViewClick.openApp(getApplicationContext(), getPackageName());
                    }
                }


            };


}

