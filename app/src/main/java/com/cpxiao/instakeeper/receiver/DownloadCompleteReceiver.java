package com.cpxiao.instakeeper.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.cpxiao.AppConfig;
import com.cpxiao.instakeeper.utils.SDCardUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author cpxiao on 2017/12/03.
 */

public class DownloadCompleteReceiver extends BroadcastReceiver {
    private static final String TAG = DownloadCompleteReceiver.class.getSimpleName();
    private static final boolean DEBUG = AppConfig.DEBUG;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (DEBUG) {
            Log.d(TAG, "onReceive: ....");
        }
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {

            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (DEBUG) {
                Log.d(TAG, "onReceive: id = " + id);
            }


            if (id != -1) {
                DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(id);
                Cursor cursor = manager.query(query);
                while (cursor.moveToNext()) {
                    String downId = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
                    String title = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));
                    String uri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    String status = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    String size = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    String sizeTotal = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    if (DEBUG) {
                        Log.d(TAG, "onReceive: downId = " + downId);
                        Log.d(TAG, "onReceive: title = " + title);
                        Log.d(TAG, "onReceive: uri = " + uri);
                        Log.d(TAG, "onReceive: status = " + status);
                        Log.d(TAG, "onReceive: size = " + size);
                        Log.d(TAG, "onReceive: sizeTotal = " + sizeTotal);
                    }
                    File file = new File(uri);
                    String fileName = file.getName();
                    String diaPath = file.getAbsolutePath();
                    String parent = file.getParent();
                    String path = file.getPath();
                    if (DEBUG) {
                        Log.d(TAG, "onReceive: fileName = " + fileName);
                        Log.d(TAG, "onReceive: path = " + path);
                        Log.d(TAG, "onReceive: parent = " + parent);
                        Log.d(TAG, "onReceive: dirPath = " + diaPath);
                    }

                    // 把文件插入到系统图库
                    try {
                        String filePath = SDCardUtils.getInstaKeeperDia() + "/" + fileName;
                        MediaStore.Images.Media.insertImage(context.getContentResolver(), filePath, fileName, null);
                    } catch (FileNotFoundException e) {
                        if (DEBUG) {
                            Log.d(TAG, "onReceive: ..." + e.getMessage());
                        }
                        e.printStackTrace();
                    }
                    scannerFile(context, uri);

                }

            }

        }
    }


    private void scannerFile(Context context, String filePath) {
        if (DEBUG) {
            Log.d(TAG, "scannerFile: filePath = " + filePath);
        }
        Uri contentUri = Uri.fromFile(new File(filePath));
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    private void scannerFile(Context context, Uri uri) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        context.sendBroadcast(mediaScanIntent);
    }
}
