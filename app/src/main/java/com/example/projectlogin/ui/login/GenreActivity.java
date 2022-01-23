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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

public class GenreActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userEmail;
    ArrayList<Song> playlistTitles = new ArrayList<>();
    ArrayList<String> playlistTitlesActivity = new ArrayList<>();
    ListAdapterAlbum listAdapter;
    ListView listView;
    String genreActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        listView = findViewById(R.id.dataList);

        Intent intent = this.getIntent();

        if (intent != null){
            genreActivity = intent.getStringExtra("title");
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user!=null)
        {
            userEmail= user.getEmail();
        }

        db.collection("genres").document(genreActivity).collection("lists")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Song song = new Song(document.getId(), "", document.getString("id"), "", document.getString("image"));
                                playlistTitles.add(song);
                                playlistTitlesActivity.add(document.getString("id"));
                            }
                            listAdapter = new ListAdapterAlbum(GenreActivity.this, playlistTitles);
                            listView.setAdapter(listAdapter);
                            listView.setClickable(true);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Intent intent = new Intent(GenreActivity.this, PlaylistActivity.class);
                                    intent.putExtra("type", "userCreatedPlaylist");
                                    intent.putExtra("playlistUrl", playlistTitlesActivity.get(i));
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
        BottomNavigationHelper.enableNavigation(GenreActivity.this,bottomNavigationViewEx);

        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
    }
}
