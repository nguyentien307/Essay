package com.example.tiennguyen.essay.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tiennguyen.essay.fragment.SearchTabFragments.AlbumTabFragment;
import com.example.tiennguyen.essay.fragment.SearchTabFragments.KaraokeTabFragment;
import com.example.tiennguyen.essay.fragment.SearchTabFragments.PlaylistTabFragment;
import com.example.tiennguyen.essay.fragment.SearchTabFragments.SongTabFragment;

/**
 * Created by Quyen Hua on 10/28/2017.
 */

public class SearchPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[] { "Bài hát", "Album", "Playlist", "Karaoke" };
    private String content;

    public SearchPagerAdapter(FragmentManager fm, String content) {
        super(fm);
        this.content = content;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return SongTabFragment.newInstance(0, content);
            case 1:
                return AlbumTabFragment.newInstance(1, content);
            case 2:
                return PlaylistTabFragment.newInstance(2, content);
            case 3:
                return KaraokeTabFragment.newInstance(3, content);
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
