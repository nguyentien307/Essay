package com.example.tiennguyen.essay.model;

/**
 * Created by Quyen Hua on 10/22/2017.
 */

public class SingerItem {
    private String name;
    private String href;

    public SingerItem(String name, String href) {
        this.name = name;
        this.href = href;
    }

    public String getSingerName() {
        return name;
    }

    public void setSingerName(String name) {
        this.name = name;
    }

    public String getSingerHref() {
        return href;
    }

    public void setSingerHref(String href) {
        this.href = href;
    }
}
