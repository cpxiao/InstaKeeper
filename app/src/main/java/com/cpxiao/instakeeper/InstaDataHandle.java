package com.cpxiao.instakeeper;

import android.util.Log;

import com.cpxiao.AppConfig;
import com.cpxiao.instakeeper.imps.LoadListener;
import com.cpxiao.instakeeper.mode.bean.DataBean;
import com.cpxiao.instakeeper.mode.bean.InstaDataBean;
import com.cpxiao.instakeeper.utils.OkHttpManager;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author cpxiao on 2017/12/01.
 */

public class InstaDataHandle {

    private static final String TAG = InstaDataHandle.class.getSimpleName();
    private static final boolean DEBUG = AppConfig.DEBUG;

    public static void request(final String url, final LoadListener listener) {

        final Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        OkHttpManager.getInstance()
                .getOkHttpClient()
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        if (DEBUG) {
                            Log.d(TAG, "onFailure: ");
                        }
                        // 注：该回调是子线程，非主线程
                        if (listener != null) {
                            listener.onRequestUrlFail(url);
                        }
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (DEBUG) {
                            Log.d(TAG, "onResponse: ");
                        }
                        // 注：该回调是子线程，非主线程
                        String result = response.body().string();

                        String jsonString = InstaDataHandle.parseInstagramHtml(result);
                        InstaDataBean instaDataBean = InstaDataHandle.parseInstagramJson(jsonString);
                        if (instaDataBean == null) {
                            //parse fail
                            if (listener != null) {
                                listener.onParseFail(url, "instaDataBean == null");
                            }
                        } else {
                            try {
                                InstaDataBean.EntryDataBean.PostPageBean.GraphqlBean.ShortcodeMediaBean shortcodeMediaBean
                                        = instaDataBean.getEntry_data().getPostPage().get(0).getGraphql().getShortcode_media();
                                DataBean dataBean = new DataBean();
                                dataBean.setVideo(shortcodeMediaBean.isIs_video());
                                dataBean.setVideoUrl(shortcodeMediaBean.getVideo_url());
                                dataBean.setDisplayUrl(shortcodeMediaBean.getDisplay_url());

                                // parse success
                                if (listener != null) {
                                    listener.onParseSuccess(url, dataBean);
                                }
                            } catch (NullPointerException e) {
                                // parse fail
                                if (listener != null) {
                                    listener.onParseFail(url, "instaDataBean != null, " + e.getMessage()+"\n"+jsonString);
                                }
                            }
                        }
                    }

                });
    }

    /**
     * 截取json数据
     */
    private static String parseInstagramHtml(String htmlString) {
        if (htmlString == null) {
            return null;
        }
        int startIndex = htmlString.indexOf("window._sharedData");
        htmlString = htmlString.substring(startIndex);
        startIndex = htmlString.indexOf("{");
        //        int endIndex = htmlString.indexOf(";");//不能以';'作为结束，json里某些字段可能会出现';'
        //        int endIndex1 = htmlString.indexOf("</script>");
        int endIndex2 = htmlString.indexOf(";</script>");
        htmlString = htmlString.substring(startIndex, endIndex2);

        return htmlString;
    }

    private static InstaDataBean parseInstagramJson(String json) {
        if (json == null) {
            return null;
        }
        if (DEBUG) {
            Log.d(TAG, "parseInstagramJson: json = " + json.substring(0, 20));
            Log.d(TAG, "parseInstagramJson: json = " + json.substring(json.length() - 20));
        }
        InstaDataBean instaDataBean = null;
        try {
            Gson gson = new Gson();
            instaDataBean = gson.fromJson(json, InstaDataBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (instaDataBean != null) {
            if (DEBUG) {
                try {
                    Log.d(TAG, "parseInstagramJson: " + instaDataBean.getEntry_data().getPostPage().get(0).getGraphql().getShortcode_media().getDisplay_url());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return instaDataBean;
    }
}
