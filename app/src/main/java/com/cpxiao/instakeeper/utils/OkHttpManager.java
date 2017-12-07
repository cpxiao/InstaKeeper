package com.cpxiao.instakeeper.utils;

import com.cpxiao.AppConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author cpxiao on 2017/11/30.
 */

public class OkHttpManager {
    private static final String TAG = OkHttpManager.class.getSimpleName();
    private static final boolean DEBUG = AppConfig.DEBUG;

    private static volatile OkHttpManager manager = null;
    private OkHttpClient okHttpClient = null;

    private OkHttpManager() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    public static OkHttpManager getInstance() {
        if (manager == null) {
            synchronized (OkHttpManager.class) {
                if (manager == null) {
                    manager = new OkHttpManager();
                }
            }
        }
        return manager;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
