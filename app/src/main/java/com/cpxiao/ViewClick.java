package com.cpxiao;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;

import com.cpxiao.androidutils.library.utils.RateAppUtils;
import com.cpxiao.androidutils.library.utils.ShareAppUtils;
import com.cpxiao.instakeeper.R;

/**
 * @author cpxiao on 2017/12/02.
 */

public class ViewClick {
    public static String getLastClipboardString(Context context) {
        String lastClipboardString = null;
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        try {
            ClipData clipData = clipboardManager.getPrimaryClip();
            lastClipboardString = clipData.getItemAt(0).getText().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastClipboardString;
    }

    public static void rateApp(Context context) {
        RateAppUtils.rate(context);
    }

    public static void shareApp(Context context) {
        String msg = context.getString(R.string.share_msg) + "\n" +
                context.getString(R.string.app_name) + "\n" +
                "https://play.google.com/store/apps/details?id=" + context.getPackageName();
        ShareAppUtils.share(context, context.getString(R.string.share), msg);
    }

    public static void openApp(Context context, String packageName) {
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (launchIntent != null) {
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            try {
                context.startActivity(launchIntent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                //                    e.printStackTrace();
            }
        }
    }


}
