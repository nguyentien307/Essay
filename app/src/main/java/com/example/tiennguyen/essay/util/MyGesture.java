package com.example.tiennguyen.essay.util;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Quyen Hua on 10/28/2017.
 */

public class MyGesture extends GestureDetector.SimpleOnGestureListener {
    OnScrollScreen onScrollScreen;

    public MyGesture(OnScrollScreen onScrollScreen) {
        this.onScrollScreen = onScrollScreen;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        onScrollScreen.onScrollScreen(e1, e2, distanceX, distanceY);
        return super.onScroll(e1, e2, distanceX, distanceY);
    }

    public interface OnScrollScreen {
        void onScrollScreen(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY);
    }
}
