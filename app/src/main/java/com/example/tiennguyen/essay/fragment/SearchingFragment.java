package com.example.tiennguyen.essay.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
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
import com.example.tiennguyen.essay.util.ViewAnimator;
import com.example.tiennguyen.essay.util.WriteData;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

public class SearchingFragment extends Fragment implements ScreenShotable {

    private MaterialSearchView searchView;

    private TextView tvClearSearchHistory;
    private String searchHistory = "search_history.xml";
    private ArrayList<String> searchHistoryArr;
    private ListView lvSearchHistory;
    private SearchHistoryAdapter searchHistoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searching, viewGroup, false);
        setInitial(view);
        getSearchHistory();
        searched();
        event();
        return view;
    }

    private void setInitial(View view) {
        searchView = (MaterialSearchView) getActivity().findViewById(R.id.search_view);
        tvClearSearchHistory = (TextView) view.findViewById(R.id.tvClearSearchHistory);
        lvSearchHistory = (ListView) view.findViewById(R.id.lvSearchHistory);
    }

    private void searched() {
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                saveData(query + "\n", Context.MODE_APPEND);
                StringUtils convertedToUnsigned = new StringUtils();
                String name = convertedToUnsigned.convertedToUnsigned(query);
                ScreenShotable screenShot = new SearchFragment();
                replaceFragment(screenShot, 0, name);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
    }

    private void event() {
        tvClearSearchHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder confirmDialog = new AlertDialog.Builder(getActivity());
                confirmDialog.setTitle("Clear history");
                confirmDialog.setMessage("Bạn thật sự muốn xóa lịch sử tìm kiếm?");
                confirmDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveData("", Context.MODE_PRIVATE);
                        getSearchHistory();
                    }
                });
                confirmDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                confirmDialog.create().show();
            }
        });
    }

    private void saveData(String data, final int mode) {
        WriteData writeData = new WriteData(new WriteData.GetFileOutputStream() {
            @Override
            public FileOutputStream getFileOutputStream() {
                FileOutputStream fos = null;
                try {
                    fos = getActivity().openFileOutput(searchHistory, mode);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return fos;
            }
        });
        writeData.saveData(data);
    }

    private void replaceFragment(ScreenShotable screenShotable, int topPosition, String name) {
        //this.res = this.res == R.drawable.content_music ? R.drawable.content_films : R.drawable.content_music;
        View view = getActivity().findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        getActivity().findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        SearchFragment searchFragment = new SearchFragment();
        searchFragment = searchFragment.newInstance(name);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, searchFragment).commit();
    }

    private void getSearchHistory() {
        StringBuilder sb = new StringBuilder();
        searchHistoryArr = new ArrayList<>();
        try {
            FileInputStream in = getActivity().openFileInput(searchHistory);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String s = null;
            while ((s = br.readLine()) != null) {
                searchHistoryArr.add(s);
                sb.append(s).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setListSearchHistory();
    }

    private void setListSearchHistory() {
        searchHistoryAdapter = new SearchHistoryAdapter(getContext(), R.layout.search_history_item, searchHistoryArr);
        lvSearchHistory.setAdapter(searchHistoryAdapter);
    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }

}
