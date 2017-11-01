package com.example.tiennguyen.essay.fragment.SearchTabFragments;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tiennguyen.essay.Adapter.SongAdapter;
import com.example.tiennguyen.essay.R;
import com.example.tiennguyen.essay.fragment.SearchFragment;
import com.example.tiennguyen.essay.fragment.SearchingFragment;
import com.example.tiennguyen.essay.interfaces.ScreenShotable;
import com.example.tiennguyen.essay.model.SongItem;
import com.example.tiennguyen.essay.service.BaseURI;
import com.example.tiennguyen.essay.service.GetData;
import com.example.tiennguyen.essay.util.MyHandle;
import com.example.tiennguyen.essay.util.ViewAnimator;
import com.example.tiennguyen.essay.util.WriteData;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

public class SongTabFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_CONTENT = "ARG_CONTENT";

    private int mPage;
    private String content;

    private MaterialSearchView searchView;
    private TextView tvNumFound;
    private BaseURI baseURI;
    private String searchLink;
    private GetData getData;
    private String numFound;
    private ArrayList<SongItem> searchingArray;
    private LinearLayout layoutSearched;
    private ListView lvSongSearched;
    private SongAdapter songAdapter;
    private ProgressBar loading;
    private View footerView;
    private MyHandle myHandle;
    private boolean isLoading = false;
    private ArrayList<SongItem> footerSongArray;
    private int startSearchItem = 1;

    private String fileSave = "songList.json";

    public static SongTabFragment newInstance(int page, String data) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString(ARG_CONTENT, data);
        SongTabFragment fragment = new SongTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song_tab, container, false);
        setInitial(view);
        getArgument();
        searched();
        event();
//        searchEvent();
        return view;
    }

    private void setInitial(View view) {
        searchView = (MaterialSearchView) getActivity().findViewById(R.id.search_view);
        tvNumFound = (TextView) view.findViewById(R.id.tvNumFound);
        baseURI = new BaseURI();
        layoutSearched = (LinearLayout) view.findViewById(R.id.layoutSearched);
        lvSongSearched = (ListView) view.findViewById(R.id.listSongSearched);
        loading = (ProgressBar) view.findViewById(R.id.loading);
        LayoutInflater li = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        footerView = li.inflate(R.layout.footer_view, null);
        myHandle = new MyHandle(myHandle());
    }

    private MyHandle.HandleFooterView myHandle() {
        return new MyHandle.HandleFooterView() {
            @Override
            public void setFooterView(Message msg) {
                lvSongSearched.addFooterView(footerView);
            }

            @Override
            public void setMoreListView(Message msg) {
                songAdapter.addListItemToAdapter((ArrayList<SongItem>) msg.obj);
                lvSongSearched.removeFooterView(footerView);
                isLoading = false;
            }
        };
    }

    private void getArgument() {
        mPage = getArguments().getInt(ARG_PAGE);
        content = getArguments().getString(ARG_CONTENT);
    }

    private void searched() {
        setLoading(true);
        searchLink = baseURI.getSearchedSong(content, "song", startSearchItem, startSearchItem + 10);
        startSearchItem += 10;
        getData = new GetData();
        getData.execute(searchLink);
        getData.setDataDownloadListener(new GetData.DataDownloadListener() {
            @Override
            public void dataDownloadedSuccessfully(JSONObject data) {
                setLoading(false);
                saveData(String.valueOf(data), Context.MODE_PRIVATE);
                searchingArray = getList(data);
                setAdapter();
            }
            @Override
            public void dataDownloadFailed() {
            }
        });
    }

    private void setLoading(boolean b) {
        if (b == true) {
            layoutSearched.setVisibility(View.INVISIBLE);
            loading.setVisibility(View.VISIBLE);
        } else {
            layoutSearched.setVisibility(View.VISIBLE);
            loading.setVisibility(View.INVISIBLE);
        }
    }

    private void setAdapter() {
        tvNumFound.setText("Có " + numFound + " kết quả được tìm thấy");
        songAdapter = new SongAdapter(getActivity(), R.layout.song_searched_item, searchingArray);
        lvSongSearched.setAdapter(songAdapter);
    }

    private ArrayList<SongItem> getList(JSONObject data) {
        ArrayList<SongItem> songArr = new ArrayList<>();
        try {
            numFound = data.getString("total");
            JSONArray songList = data.getJSONArray("list");
            for(int i = 0; i < songList.length(); i++) {
                JSONObject jsObject = songList.getJSONObject(i);
                String singerList = getSingerList(jsObject);
                SongItem item = new SongItem(jsObject.getString("title"), singerList, jsObject.getString("key"), jsObject.getString("href"));
                songArr.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return songArr;
    }

    private String getSingerList(JSONObject jsObject) {
        String singers = "";
        try {
            JSONArray jsArr = jsObject.getJSONArray("singers");
            singers = jsArr.getJSONObject(0).getString("singerName");
            for (int i = 1; i < jsArr.length(); i++) {
                JSONObject object = jsArr.getJSONObject(i);
                singers += ", " + object.getString("singerName");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  singers;
    }

    private void event() {
        lvSongSearched.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (view.getLastVisiblePosition() == totalItemCount - 1 && lvSongSearched.getCount() >= 10 && isLoading == false) {
                    isLoading = true;
                    Thread thread = new ThreadGetMoreData();
                    thread.start();
                }
            }
        });
    }

    public ArrayList<SongItem> getMoreData() {
        footerSongArray = new ArrayList<>();
        searchLink = baseURI.getSearchedSong(content, "song", startSearchItem, startSearchItem + 10);
        startSearchItem += 10;
        getData = new GetData();
        getData.execute(searchLink);
        getData.setDataDownloadListener(new GetData.DataDownloadListener() {
            @Override
            public void dataDownloadedSuccessfully(JSONObject data) {
                footerSongArray = getList(data);
                Message msg = myHandle.obtainMessage(1, footerSongArray);
                myHandle.sendMessage(msg);
            }
            @Override
            public void dataDownloadFailed() {

            }
        });
        return footerSongArray;
    }

    public class ThreadGetMoreData extends Thread {
        @Override
        public void run() {
            myHandle.sendEmptyMessage(0);
            getMoreData();
        }
    }

    private void saveData(String data, final int mode) {
        WriteData writeData = new WriteData(new WriteData.GetFileOutputStream() {
            @Override
            public FileOutputStream getFileOutputStream() {
                FileOutputStream fos = null;
                try {
                    fos = getActivity().openFileOutput(fileSave, mode);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return fos;
            }
        });
        writeData.saveData(data);
    }
}
