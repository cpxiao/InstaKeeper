package com.cpxiao.instakeeper.utils;

import com.cpxiao.AppConfig;
import com.cpxiao.androidutils.library.utils.SdcardUtils;

/**
 * @author cpxiao on 2017/12/06.
 */

public class SDCardUtils {

    private static final String TAG = SDCardUtils.class.getSimpleName();
    private static final boolean DEBUG = AppConfig.DEBUG;

    public static String getInstaKeeperDia() {
        //        String dir = Environment.DIRECTORY_PICTURES + "/InstaKeeper";
        //        File file = new File(dir);
        //        if (!file.exists()) {
        //            file.mkdir();
        //        }
        //        if (DEBUG) {
        //            Log.d(TAG, "getInstaKeeperDia: dir = " + dir);
        //        }
        //        return dir;
        return SdcardUtils.getSdcardPath()+"/InstaKeeper";
//        File file = new File(Environment.DIRECTORY_PICTURES, "InstaKeeper");
//        if (!file.exists()) {
//            file.mkdir();
//        }
//        String dir = file.getAbsolutePath();
//        if (DEBUG) {
//            Log.d(TAG, "getInstaKeeperDia: dir = " + dir);
//        }
//        return dir;

    }
}
