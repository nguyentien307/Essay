package com.example.tiennguyen.essay.model;

import java.util.ArrayList;

/**
 * Created by Quyen Hua on 10/22/2017.
 */

public class SongItem {
    private String title;
    private String singers;
    private String img;
    private String href;
    private String key;

    public SongItem(String title, String singers, String key, String href) {
        this.title = title;
        this.singers = singers;
        this.key = key;
        this.href = href;
    }

    public SongItem(String title, String singers, String img, String href, String key) {
        this.title = title;
        this.singers = singers;
        this.img = img;
        this.href = href;
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSingers() {
        return singers;
    }

    public void setSingers(String singers) {
        this.singers = singers;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
