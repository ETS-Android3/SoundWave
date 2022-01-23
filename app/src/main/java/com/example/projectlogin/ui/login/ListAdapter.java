package com.example.projectlogin.ui.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projectlogin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListAdapter extends ArrayAdapter<Song> {

    FirebaseFirestore db;
    String userEmail;


    public ListAdapter(Context context, ArrayList<Song> userArrayList){

        super(context,R.layout.list_adapter_song,userArrayList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Song song = getItem(position);

        if (convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_adapter_song,parent,false);

        }

        ImageView imageView = convertView.findViewById(R.id.profile_pic);
        Context context = getContext();
        ImageView btnAction = convertView.findViewById(R.id.like);
        TextView userName = convertView.findViewById(R.id.personName);
        TextView lastMsg = convertView.findViewById(R.id.lastMessage);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null)
        {
            userEmail= user.getEmail();
        }
        db = FirebaseFirestore.getInstance();
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> user = new HashMap<>();
                user.put("artist", song.lastMessage);
                user.put("title", song.name);
                user.put("songId", song.phoneNo);
                user.put("thumbnailUrl",song.imageId);
                db.collection("users").document(userEmail).collection("playlists").document("likedSongs").collection("songs").document(song.name).set(user);
                Toast.makeText(context, "Added to liked songs!" , Toast.LENGTH_SHORT ).show();
            }
        });

        userName.setText(song.name);
        lastMsg.setText(song.lastMessage);
        Picasso.get().load(song.imageId).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(imageView);


        return convertView;
    }
}