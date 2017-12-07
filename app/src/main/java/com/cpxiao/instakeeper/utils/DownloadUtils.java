package com.cpxiao.instakeeper.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.cpxiao.AppConfig;

/**
 * @author cpxiao on 2017/12/03.
 */

public class DownloadUtils {

    private static final String TAG = DownloadUtils.class.getSimpleName();
    private static final boolean DEBUG = AppConfig.DEBUG;

    public static long download(Context context, String url, String fileName) {
        if (context == null || TextUtils.isEmpty(url) || TextUtils.isEmpty(fileName)) {
            return -1;
        }
        if (DEBUG) {
            Log.d(TAG, "download: url = " + url);
            Log.d(TAG, "download: fileName = " + fileName);
        }

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //        request.setTitle("title.jpg");
        //        request.setDescription("is downloading");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //指定下载到SD卡的/Pictures/InstaKeeper/目录下
//        request.setDestinationInExternalPublicDir(SDCardUtils.getInstaKeeperDia(), fileName);
        request.setDestinationInExternalPublicDir("InstaKeeper", fileName);

        long id = downloadManager.enqueue(request);
        if (DEBUG) {
            Log.d(TAG, "download: id = " + id);
        }
        return id;
    }

    public static void remore(Context context, long... ids) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.remove(ids);
    }


}
