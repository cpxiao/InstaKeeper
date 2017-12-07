package com.cpxiao.instakeeper.mode;

/**
 * @author cpxiao on 2017/12/01.
 */

public class DataBean {
    private boolean isVideo = false;
    private String videoUrl;
    private String displayUrl;

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDisplayUrl() {
        return displayUrl;
    }

    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }
}
