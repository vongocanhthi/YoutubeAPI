package com.anhthi.youtubeapi.model;

public class Video {
    String title, thumbnail, videoId;

    public Video() {
    }

    public Video(String title, String thumbnail, String videoId) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
