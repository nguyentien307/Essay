package com.example.tiennguyen.essay.fragment.SearchTabFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tiennguyen.essay.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link KaraokeTabFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link KaraokeTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KaraokeTabFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static KaraokeTabFragment newInstance(int page, String data) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        KaraokeTabFragment fragment = new KaraokeTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_karaoke_tab, container, false);
        return view;
    }
}
