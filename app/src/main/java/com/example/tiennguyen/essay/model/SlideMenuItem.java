package com.example.tiennguyen.essay.model;

import com.example.tiennguyen.essay.interfaces.Resourceble;

/**
 * Created by TIENNGUYEN on 10/6/2017.
 */

public class SlideMenuItem implements Resourceble {
    private String name;
    private int imageRes;

    public SlideMenuItem(String name, int imageRes) {
        this.name = name;
        this.imageRes = imageRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }
}
