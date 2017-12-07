package com.cpxiao.instakeeper.imps;

import com.cpxiao.instakeeper.mode.bean.DataBean;

/**
 * @author cpxiao on 2017/12/01.
 */

public interface LoadListener {
    void onRequestUrlFail(String url);

    void onParseFail(String url, String errorMsg);

    void onParseSuccess(String url, DataBean dataBean);

}
