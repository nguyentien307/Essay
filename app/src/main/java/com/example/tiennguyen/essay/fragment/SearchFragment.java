package com.example.tiennguyen.essay.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.example.tiennguyen.essay.Adapter.SearchHistoryAdapter;
import com.example.tiennguyen.essay.Adapter.SearchPagerAdapter;
import com.example.tiennguyen.essay.R;
import com.example.tiennguyen.essay.interfaces.ScreenShotable;
import com.example.tiennguyen.essay.util.StringUtils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SearchFragment extends Fragment implements ScreenShotable {

    public static final String ARG_CONTENT = "ARG_CONTENT";
    private String name;
    private ViewPager searchPager;
    private PagerSlidingTabStrip tabsStrip;

    public static SearchFragment newInstance(String data) {
        Bundle args = new Bundle();
        args.putString(ARG_CONTENT, data);
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, viewGroup, false);
        setInitial(view);
        getArgument();
        return view;
    }

    private void getArgument() {
        Bundle bundle = getArguments();
        name = bundle.getString(ARG_CONTENT);
        setViewPager(name);
    }

    private void setInitial(View view) {
        searchPager = (ViewPager) view.findViewById(R.id.search_pager);
        tabsStrip = (PagerSlidingTabStrip) view.findViewById(R.id.search_tabs);
    }

    private void setViewPager(String songName) {
        SearchPagerAdapter searchPageAdapter = new SearchPagerAdapter(getActivity().getSupportFragmentManager(), songName);
        searchPager.setAdapter(searchPageAdapter);

        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(searchPager);

        tabsStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });
    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }

}
