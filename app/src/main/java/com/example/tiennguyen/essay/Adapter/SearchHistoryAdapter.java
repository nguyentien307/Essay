package com.example.tiennguyen.essay.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tiennguyen.essay.R;
import com.example.tiennguyen.essay.model.SongItem;

import java.util.ArrayList;

/**
 * Created by Quyen Hua on 10/26/2017.
 */

public class SearchHistoryAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<String> arrayList;

    public SearchHistoryAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<String> arrayList) {
        super(context, resource, arrayList);
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.search_history_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvSeachHistoryName = (TextView) convertView.findViewById(R.id.tvSearchHistoryName);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvSeachHistoryName.setText(arrayList.get(position));
        return convertView;
    }

    public class ViewHolder {
        TextView tvSeachHistoryName;
    }
}
