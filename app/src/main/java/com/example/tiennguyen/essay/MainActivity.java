package com.example.tiennguyen.essay;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tiennguyen.essay.fragment.ContentFragment;
import com.example.tiennguyen.essay.fragment.FragmentAlbum;
import com.example.tiennguyen.essay.fragment.FragmentChart;
import com.example.tiennguyen.essay.fragment.FragmentHome;
import com.example.tiennguyen.essay.fragment.FragmentMv;
import com.example.tiennguyen.essay.fragment.FragmentSong;
import com.example.tiennguyen.essay.fragment.FragmentUser;
import com.example.tiennguyen.essay.fragment.SearchFragment;
import com.example.tiennguyen.essay.fragment.SearchingFragment;
import com.example.tiennguyen.essay.interfaces.Resourceble;
import com.example.tiennguyen.essay.interfaces.ScreenShotable;
import com.example.tiennguyen.essay.model.SlideMenuItem;
import com.example.tiennguyen.essay.util.ViewAnimator;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewAnimator.ViewAnimatorListener {
    FloatingActionButton fab, fabHome, fabSongs, fabAlbums, fabChart, fabHot;
    boolean isExpand = false;
    Animation Move_Home, Move_Chart, Move_Song, Move_Album, Move_Love,
                Back_Home, Back_Chart, Back_Song, Back_Album, Back_Love;


    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
   // private ContentFragment contentFragment;
    private ViewAnimator viewAnimator;
    //private int res = R.drawable.content_music;
    private LinearLayout linearLayout;

    private ScreenShotable screenShot;
    private Fragment fragment = null;
    MaterialSearchView searchView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        refView();
        loadAnim();
        setClickEvent();
        setActionBar();
        createMenuList();

        screenShot = new FragmentHome();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, (Fragment) screenShot)
                .commit();

        drawerLayout.setScrimColor(Color.TRANSPARENT);
        viewAnimator = new ViewAnimator<>(this, list, screenShot, drawerLayout, this);
        //viewAnimator = new ViewAnimator<>(this, list, drawerLayout, this);
    }

    private void loadAnim(){
        Move_Home = AnimationUtils.loadAnimation(this, R.anim.move_home);
        Move_Chart = AnimationUtils.loadAnimation(this, R.anim.move_chart);
        Move_Song = AnimationUtils.loadAnimation(this, R.anim.move_song);
        Move_Album = AnimationUtils.loadAnimation(this, R.anim.move_album);
        Move_Love = AnimationUtils.loadAnimation(this, R.anim.move_love);

        Back_Home = AnimationUtils.loadAnimation(this, R.anim.back_home);
        Back_Chart = AnimationUtils.loadAnimation(this, R.anim.back_chart);
        Back_Song = AnimationUtils.loadAnimation(this, R.anim.back_song);
        Back_Album = AnimationUtils.loadAnimation(this, R.anim.back_album);
        Back_Love = AnimationUtils.loadAnimation(this, R.anim.back_love);
    }

    private void refView() {
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fabHome = (FloatingActionButton)findViewById(R.id.fab_home);
        fabSongs = (FloatingActionButton)findViewById(R.id.fab_songs);
        fabAlbums = (FloatingActionButton)findViewById(R.id.fab_albums);
        fabChart = (FloatingActionButton)findViewById(R.id.fab_chart);
        fabHot = (FloatingActionButton)findViewById(R.id.fab_hot);

        //-----------drawer menu-------------
        //contentFragment = ContentFragment.newInstance("Home");


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);

    }

    private  void setClickEvent() {
        fab.setOnClickListener(this);
        fabHome.setOnClickListener(this);
        fabChart.setOnClickListener(this);
        fabSongs.setOnClickListener(this);
        fabAlbums.setOnClickListener(this);
        fabHot.setOnClickListener(this);
        linearLayout.setOnClickListener(this);
    }

    //        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                if (isExpand == false) {
////                    Show();
////                    isExpand = true;
////                } else {
////                    Hide();
////                    isExpand = false;
////
////                }
//                Move();
//            }
//        });

    public void onClick(View v){
        switch(v.getId()){
            case R.id.fab: {
                if (isExpand == false) {
                    fab.setImageResource(R.drawable.ic_close_black_24dp);
                    expand();

                    isExpand = true;
                } else {
                    fab.setImageResource(R.drawable.ic_menu_black_24dp);
                    collapse();
                    isExpand = false;
                }
            }; break;
            case R.id.fab_home: {
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
            };break;
            case R.id.fab_songs: {
                Toast.makeText(this, "Songs", Toast.LENGTH_SHORT).show();
            };break;
            case R.id.fab_chart: {
                Toast.makeText(this, "Chart", Toast.LENGTH_SHORT).show();
            };break;
            case R.id.fab_albums: {
                Toast.makeText(this, "Albums", Toast.LENGTH_SHORT).show();
            };break;
            case R.id.fab_hot: {
                Toast.makeText(this, "Love", Toast.LENGTH_SHORT).show();
            };break;
            case R.id.left_drawer: {
                drawerLayout.closeDrawers();
            };break;
            default:
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();break;

        }
    }
    private void Show(){
        fabHome.show();
        fabChart.show();
        fabSongs.show();
        fabAlbums.show();
        fabHot.show();
    }

    private void Hide(){
        fabHome.hide();
        fabChart.hide();
        fabSongs.hide();
        fabAlbums.hide();
        fabHot.hide();
    }


    private void expand(){
        FrameLayout.LayoutParams paramsHome = (FrameLayout.LayoutParams) fabHome.getLayoutParams();
        paramsHome.rightMargin = (int)(fabHome.getWidth()*4);
        paramsHome.bottomMargin = (int)(fabHome.getWidth()*0.5);
        fabHome.setLayoutParams(paramsHome);
        fabHome.startAnimation(Move_Home);

        FrameLayout.LayoutParams paramsChart = (FrameLayout.LayoutParams) fabChart.getLayoutParams();
        paramsChart.rightMargin = (int)(fabChart.getWidth()*3.5);
        paramsChart.bottomMargin = (int)(fabChart.getHeight()*1.6);
        fabChart.setLayoutParams(paramsChart);
        fabChart.startAnimation(Move_Chart);

        FrameLayout.LayoutParams paramsSong = (FrameLayout.LayoutParams) fabSongs.getLayoutParams();
        paramsSong.rightMargin = (int)(fabSongs.getWidth()*2.8);
        paramsSong.bottomMargin = (int)(fabSongs.getHeight()*2.7);
        fabSongs.setLayoutParams(paramsSong);
        fabSongs.startAnimation(Move_Song);

        FrameLayout.LayoutParams paramsAlbum = (FrameLayout.LayoutParams) fabAlbums.getLayoutParams();
        paramsAlbum.rightMargin = (int)(fabAlbums.getWidth()*1.6);
        paramsAlbum.bottomMargin = (int)(fabAlbums.getHeight()*3.5);
        fabAlbums.setLayoutParams(paramsAlbum);
        fabAlbums.startAnimation(Move_Album);

        FrameLayout.LayoutParams paramsLove = (FrameLayout.LayoutParams) fabHot.getLayoutParams();
        paramsLove.rightMargin = (int)(fabHot.getWidth()*0.5);
        paramsLove.bottomMargin = (int)(fabHot.getHeight()*4);
        fabHot.setLayoutParams(paramsLove);
        fabHot.startAnimation(Move_Love);
    }

    private void collapse(){
        FrameLayout.LayoutParams paramsHome = (FrameLayout.LayoutParams) fabHome.getLayoutParams();
        paramsHome.rightMargin -= (int)(fabHome.getWidth()*3.0);
        paramsHome.bottomMargin -= (int)(fabHome.getWidth()*(-0.5));
        fabHome.setLayoutParams(paramsHome);
        fabHome.startAnimation(Back_Home);

        FrameLayout.LayoutParams paramsChart = (FrameLayout.LayoutParams) fabChart.getLayoutParams();
        paramsChart.rightMargin -= (int)(fabChart.getWidth()*2.5);
        paramsChart.bottomMargin -= (int)(fabChart.getHeight()*0.6);
        fabChart.setLayoutParams(paramsChart);
        fabChart.startAnimation(Back_Chart);

        FrameLayout.LayoutParams paramsSong = (FrameLayout.LayoutParams) fabSongs.getLayoutParams();
        paramsSong.rightMargin -= (int)(fabSongs.getWidth()*1.8);
        paramsSong.bottomMargin -= (int)(fabSongs.getHeight()*1.7);
        fabSongs.setLayoutParams(paramsSong);
        fabSongs.startAnimation(Back_Song);

        FrameLayout.LayoutParams paramsAlbum = (FrameLayout.LayoutParams) fabAlbums.getLayoutParams();
        paramsAlbum.rightMargin -= (int)(fabAlbums.getWidth()*0.6);
        paramsAlbum.bottomMargin -= (int)(fabAlbums.getHeight()*2.5);
        fabAlbums.setLayoutParams(paramsAlbum);
        fabAlbums.startAnimation(Back_Album);

        FrameLayout.LayoutParams paramsLove = (FrameLayout.LayoutParams) fabHot.getLayoutParams();
        paramsLove.rightMargin -= (int)(fabHot.getWidth()*(-0.5));
        paramsLove.bottomMargin -= (int)(fabHot.getHeight()*3.0);
        fabHot.setLayoutParams(paramsLove);
        fabHot.startAnimation(Back_Love);
    }


    /////////////////////////////////

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(ContentFragment.CLOSE, R.drawable.icn_close);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(ContentFragment.USER, R.drawable.icons8_user_50);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(ContentFragment.HOME, R.drawable.icons8_home_page_50);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(ContentFragment.CHART, R.drawable.icons8_crown_50);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(ContentFragment.SONG, R.drawable.icons8_musical_50);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(ContentFragment.ALBUM, R.drawable.icons8_rhythm_50);
        list.add(menuItem5);
        SlideMenuItem menuItem6 = new SlideMenuItem(ContentFragment.MV, R.drawable.icons8_music_video_50);
        list.add(menuItem6);
        SlideMenuItem menuItem7 = new SlideMenuItem(ContentFragment.MOVIE, R.drawable.icn_7);
        list.add(menuItem7);
    }


    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        searchView.setMenuItem(search);
        searched(searchView);
        return true;
    }

    public void searched(MaterialSearchView searchView) {
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                replaceFragment(screenShot, 0, "Searching");
            }

            @Override
            public void onSearchViewClosed() {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition, String name) {
        //this.res = this.res == R.drawable.content_music ? R.drawable.content_films : R.drawable.content_music;
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        if (name == "Searching") {
            animator = ViewAnimationUtils.createCircularReveal(view, 370, 0, 0, finalRadius);
        }
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        //ContentFragment contentFragment = ContentFragment.newInstance(name);
        screenShot = new FragmentHome();
        switch(name) {
            case "User" : screenShot = new FragmentUser(); break;
            case "Home" : screenShot = new FragmentHome(); break;
            case "Chart" :screenShot = new FragmentChart(); break;
            case "Song" :screenShot = new FragmentSong(); break;
            case "Album" :screenShot = new FragmentAlbum(); break;
            case "Mv" :screenShot = new FragmentMv(); break;
            case "Searching": screenShot = new SearchingFragment(); break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, (Fragment) screenShot).commit();
        return screenShot;
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case ContentFragment.CLOSE:
                return screenShotable;
            default: {
                String fragmentName = slideMenuItem.getName();
                return replaceFragment(screenShotable, position, fragmentName);
            }
        }
    }


    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }
}
