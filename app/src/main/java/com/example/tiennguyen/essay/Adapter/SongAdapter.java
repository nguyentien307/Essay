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
import com.example.tiennguyen.essay.model.AlbumItem;
import com.example.tiennguyen.essay.model.SingerItem;
import com.example.tiennguyen.essay.model.SongItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quyen Hua on 10/26/2017.
 */

public class SongAdapter extends ArrayAdapter {

    private Context context;
    private int resource;
    private ArrayList<SongItem> arrayList;

    public SongAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<SongItem> arrayList) {
        super(context, resource, arrayList);
        this.context = context;
        this.resource = resource;
        this.arrayList = arrayList;
    }

    public void addListItemToAdapter(ArrayList<SongItem> songList) {
        arrayList.addAll(songList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.song_searched_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvSongName = (TextView) convertView.findViewById(R.id.tvSongName);
            viewHolder.tvSingers = (TextView) convertView.findViewById(R.id.tvSingerName);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SongItem songItem = arrayList.get(position);
        //viewHolder.tvAvatar.setBackgroundColor(contact.getColor());
        // viewHolder.tvAvatar.setText(String.valueOf(position+1));
        //viewHolder.tvName.setText(contact.getName());
        viewHolder.tvSongName.setText(newText(songItem.getTitle()));
        viewHolder.tvSingers.setText(songItem.getSingers());
        return convertView;
    }

    public class ViewHolder {
        TextView tvSongName;
        TextView tvSingers;
    }

    private String newText(String text){
        String newText = "";
        if(text.length() > 26){
            for(int i = 0; i < 26;i++){
                newText = newText + text.charAt(i);
            }
            newText = newText + " ...";
        }
        else{
            newText = text;
        }
        return newText;
    }
}
