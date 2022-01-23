package com.example.projectlogin.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectlogin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userEmail;
    ArrayList<Song> playlistTitles = new ArrayList<>();
    ArrayList<String> playlistTitlesActivity = new ArrayList<>();
    ListAdapterAlbum listAdapter;
    ArrayList<String> trackList;
    ArrayList<String> artistName;
    ArrayList<String> playListName;
    ArrayList<String> thumbnailUrl;
    ArrayList<String> songId;
    ArrayList<Song> songArrayList;
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        listView = findViewById(R.id.dataList);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user!=null)
        {
            userEmail= user.getEmail();
        }

        db.collection("users").document(userEmail).collection("stats").document("lastListened").collection("listenHistory").orderBy("timestamp", Query.Direction.DESCENDING).limit(50)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            trackList.clear();
                            songId.clear();
                            artistName.clear();
                            thumbnailUrl.clear();
                            playListName.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Song song = new Song(document.getId(), "", "", "", "https://lh3.googleusercontent.com/wr28amLh-pMk4vmrYv_Orhly8DTtdvZJFuLwmXG5RNvZJjGlFe_WMnKp4pWlZI1gL7ihQn-xZuzZ0A6VZZbv2Z-iTEH3dpjn");
                                playlistTitles.add(song);
                                playlistTitlesActivity.add(document.getId());
                            }
                            listAdapter = new ListAdapterAlbum(HistoryActivity.this, playlistTitles);
                            listView.setAdapter(listAdapter);
                            listView.setClickable(true);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Intent intent = new Intent(HistoryActivity.this, FirebasePlaylistActivity.class);
                                    intent.putExtra("type", "userCreatedPlaylist");
                                    intent.putExtra("name", playlistTitlesActivity.get(i));
                                    startActivity(intent);
                                }
                            });
                        } else {
                            Log.w("FIREBASE", "Error getting documents.", task.getException());
                        }
                    }
                });

        setupBottomNavigation();

    }

    private void setupBottomNavigation(){
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationHelper.enableNavigation(HistoryActivity.this,bottomNavigationViewEx);

        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
    }
}
