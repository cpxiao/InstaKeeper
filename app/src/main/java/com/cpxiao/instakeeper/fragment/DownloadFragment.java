package com.cpxiao.instakeeper.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cpxiao.AppConfig;
import com.cpxiao.ViewClick;
import com.cpxiao.androidutils.library.utils.ThreadUtils;
import com.cpxiao.instakeeper.InstaDataHandle;
import com.cpxiao.instakeeper.R;
import com.cpxiao.instakeeper.imps.LoadListener;
import com.cpxiao.instakeeper.mode.bean.DataBean;
import com.cpxiao.instakeeper.utils.DownloadUtils;

/**
 * @author cpxiao on 2017/11/29.
 */

public class DownloadFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = DownloadFragment.class.getSimpleName();
    private static final boolean DEBUG = AppConfig.DEBUG;

    private String mPictureUrl;

    private ImageView mImageView;
    private EditText mUrlEditText;
    private TextView mMsgTextView;
    private TextView mUrlTextView;

    public static DownloadFragment newInstance() {
        Bundle args = new Bundle();
        DownloadFragment fragment = new DownloadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (DEBUG) {
            Log.d(TAG, "onCreateView: ");
        }

        View view = inflater.inflate(R.layout.fragment_download, container, false);
        mImageView = (ImageView) view.findViewById(R.id.image_view);
        mUrlEditText = (EditText) view.findViewById(R.id.url_edit_text);
        mMsgTextView = (TextView) view.findViewById(R.id.msg_text_view);
        mUrlTextView = (TextView) view.findViewById(R.id.url_text_view);
        Button pasteBtn = (Button) view.findViewById(R.id.paste_btn);
        Button downloadBtn = (Button) view.findViewById(R.id.download_btn);
        pasteBtn.setOnClickListener(this);
        downloadBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onDestroy() {
        if (DEBUG) {
            Log.d(TAG, "onDestroy: ");
        }
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        if (DEBUG) {
            Log.d(TAG, "onDestroyView: ");
        }
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        Context context = getActivity();
        int id = v.getId();
        if (id == R.id.paste_btn) {
            mPictureUrl = ViewClick.getLastClipboardString(context);
            if (!TextUtils.isEmpty(mPictureUrl)) {
                mUrlEditText.setText(mPictureUrl);
            }
        } else if (id == R.id.download_btn) {
            if (!TextUtils.isEmpty(mPictureUrl)) {

                InstaDataHandle.request(mPictureUrl, mLoadListener);//.mp4
                setupTextView(getString(R.string.loading));
            } else {
                setupTextView(getString(R.string.input_the_link));
            }
        }
    }

    private LoadListener mLoadListener = new LoadListener() {
        @Override
        public void onRequestUrlFail(String url) {
            if (DEBUG) {
                Log.d(TAG, "onRequestUrlFail: url = " + url);
            }
            String msg;
            if (DEBUG) {
                msg = "Request Fail, url = " + url;
            } else {
                msg = "Request Fail";
            }
            setupTextView(msg);
        }

        @Override
        public void onParseFail(String url, String errorMsg) {
            if (DEBUG) {
                Log.d(TAG, "onParseFail: errorMsg = " + errorMsg);
            }
            String msg;
            if (DEBUG) {
                msg = "Parse Fail, url = " + url + ", errorMst = " + errorMsg;
            } else {
                msg = "Parse Fail";
            }
            setupTextView(msg);
        }

        @Override
        public void onParseSuccess(String url, final DataBean dataBean) {
            if (DEBUG) {
                Log.d(TAG, "onParseSuccess: dataBean.isVideo() = " + dataBean.isVideo());
                Log.d(TAG, "onParseSuccess: dataBean.getDisplayUrl() = " + dataBean.getDisplayUrl());
                Log.d(TAG, "onParseSuccess: dataBean.getVideoUrl() = " + dataBean.getVideoUrl());
            }
            String msg;
            if (DEBUG) {
                msg = getString(R.string.load_success);
            } else {
                msg = getString(R.string.load_success);
            }
            setupTextView(msg);

            setupImageView(getActivity(), dataBean.getDisplayUrl());

            download(getActivity().getApplicationContext(), dataBean);
        }
    };

    private void setupTextView(final String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        ThreadUtils.getInstance().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMsgTextView.setText(msg);
            }
        });

    }

    private void setupImageView(final Context context, final String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        ThreadUtils.getInstance().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(context)
                        .asBitmap()
                        .load(url)
                        .into(mImageView);
            }
        });
    }


    private void download(Context context, DataBean dataBean) {
        if (DEBUG) {
            Log.d(TAG, "download: ...");
        }
        if (dataBean == null) {
            return;
        }
        String url;
        String fileName;

        if (dataBean.isVideo()) {
            //download video
            url = dataBean.getVideoUrl();
            fileName = System.currentTimeMillis() + ".mp4";

        } else {
            //download picture
            url = dataBean.getDisplayUrl();
            fileName = System.currentTimeMillis() + ".jpg";
        }
        if (DEBUG) {
            Log.d(TAG, "download: url = " + url);
            Log.d(TAG, "download: fileName = " + fileName);
        }
        DownloadUtils.download(context, url, fileName);
    }


}
