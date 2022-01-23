package com.example.projectlogin.ui.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projectlogin.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListAdapterAlbum extends ArrayAdapter<Song> {

    public ListAdapterAlbum(Context context, ArrayList<Song> userArrayList){

        super(context,R.layout.list_adapter_album,userArrayList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Song song = getItem(position);

        if (convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_adapter_album,parent,false);

        }

        ImageView imageView = convertView.findViewById(R.id.profile_pic);
        TextView userName = convertView.findViewById(R.id.personName);

//        imageView.;
        userName.setText(song.name);
        Picasso.get().load(song.imageId).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(imageView);


        return convertView;
    }
}