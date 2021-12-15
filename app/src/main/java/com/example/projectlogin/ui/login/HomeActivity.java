package com.example.projectlogin.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectlogin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ImageView recommended1image;
    ImageView recommended4image;

    ImageView lastListened1;
    ImageView lastListened2;
    ImageView lastListened3;
    ImageView lastListened4;
    ImageView lastListened5;
    ImageView lastListened6;
    TextView lastListened1Text;
    TextView lastListened2Text;
    TextView lastListened3Text;
    TextView lastListened4Text;
    TextView lastListened5Text;
    TextView lastListened6Text;
    ArrayList<String> thumbnailUrl;
    ArrayList<String> songId;
    ArrayList<String> songTitle;
    ArrayList<String> songArtist;
    FirebaseFirestore db;
    String userEmail;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);
        lastListened1 = findViewById(R.id.Morning1image);
        lastListened2 = findViewById(R.id.Morning2image);
        lastListened3 = findViewById(R.id.Morning3image);
        lastListened4 = findViewById(R.id.Morning4image);
        lastListened5 = findViewById(R.id.Morning5image);
        lastListened6 = findViewById(R.id.Morning6image);
        lastListened1Text = findViewById(R.id.Morning1text);
        lastListened2Text = findViewById(R.id.Morning2text);
        lastListened3Text = findViewById(R.id.Morning3text);
        lastListened4Text = findViewById(R.id.Morning4text);
        lastListened5Text = findViewById(R.id.Morning5text);
        lastListened6Text = findViewById(R.id.Morning6text);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        lastListened1Text.setText("");
        lastListened2Text.setText("");
        lastListened3Text.setText("");
        lastListened4Text.setText("");
        lastListened5Text.setText("");
        lastListened6Text.setText("");
        Picasso.get().load(R.drawable.missingbackground).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(lastListened1);
        Picasso.get().load(R.drawable.missingbackground).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(lastListened2);
        Picasso.get().load(R.drawable.missingbackground).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(lastListened3);
        Picasso.get().load(R.drawable.missingbackground).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(lastListened4);
        Picasso.get().load(R.drawable.missingbackground).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(lastListened5);
        Picasso.get().load(R.drawable.missingbackground).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(lastListened6);
        thumbnailUrl = new ArrayList<>();
        songTitle = new ArrayList<>();
        songId = new ArrayList<>();
        songArtist = new ArrayList<>();
        count = 0;
        if (user!=null)
        {
            userEmail= user.getEmail();
        }
        db = FirebaseFirestore.getInstance();
        db.collection("users").document(userEmail).collection("stats").document("lastListened").collection("listenHistory").orderBy("timestamp").limitToLast(6).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                Log.d("DOCUMENTINFO", document.getId()+"=> " + document.getData());
                                count++;
                                thumbnailUrl.add(document.getString("thumbnailUrl"));
                                songTitle.add(document.getString("title"));
                                songArtist.add(document.getString("artist"));
                                songId.add(document.getString("songId"));
                            }
                            switch (count) {
                                case 1:
                                    Picasso.get().load(thumbnailUrl.get(0)).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(lastListened1);
                                    lastListened1Text.setText(songTitle.get(0));
                                    lastListened1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(HomeActivity.this, MainActivity.class);
                                            i.putExtra("track", songTitle.get(0) );
                                            i.putExtra("artist", songArtist.get(0));
                                            i.putExtra("thumbnail", thumbnailUrl.get(0) );
                                            i.putExtra("id", songId.get(0) );
                                            startActivity(i);
                                            MainActivity.releasePlayer();
                                        }
                                    });
                                    break;
                                case 2:
                                    Picasso.get().load(thumbnailUrl.get(0)).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(lastListened1);
                                    Picasso.get().load(thumbnailUrl.get(1)).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(lastListened2);
                                    lastListened1Text.setText(songTitle.get(0));
                                    lastListened2Text.setText(songTitle.get(1));
                                    lastListened1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(HomeActivity.this, MainActivity.class);
                                            i.putExtra("track", songTitle.get(0) );
                                            i.putExtra("artist", songArtist.get(0));
                                            i.putExtra("thumbnail", thumbnailUrl.get(0) );
                                            i.putExtra("id", songId.get(0) );
                                            startActivity(i);
                                            MainActivity.releasePlayer();
                                        }
                                    });
                                    lastListened2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(HomeActivity.this, MainActivity.class);
                                            i.putExtra("track", songTitle.get(1) );
                                            i.putExtra("artist", songArtist.get(1));
                                            i.putExtra("thumbnail", thumbnailUrl.get(1) );
                                            i.putExtra("id", songId.get(1) );
                                            startActivity(i);
                                            MainActivity.releasePlayer();
                                        }
                                    });
                                    break;
                                case 3:
                                    Picasso.get().load(thumbnailUrl.get(0)).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(lastListened1);
                                    Picasso.get().load(thumbnailUrl.get(1)).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(lastListened2);
                                    Picasso.get().load(thumbnailUrl.get(2)).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(lastListened3);
                                    lastListened1Text.setText(songTitle.get(0));
                                    lastListened2Text.setText(songTitle.get(1));
                                    lastListened3Text.setText(songTitle.get(2));
                                    lastListened1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(HomeActivity.this, MainActivity.class);
                                            i.putExtra("track", songTitle.get(0) );
                                            i.putExtra("artist", songArtist.get(0));
                                            i.putExtra("thumbnail", thumbnailUrl.get(0) );
                                            i.putExtra("id", songId.get(0) );
                                            startActivity(i);
                                            MainActivity.releasePlayer();
                                        }
                                    });
                                    lastListened2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(HomeActivity.this, MainActivity.class);
                                            i.putExtra("track", songTitle.get(1) );
                                            i.putExtra("artist", songArtist.get(1));
                                            i.putExtra("thumbnail", thumbnailUrl.get(1) );
                                            i.putExtra("id", songId.get(1) );
                                            startActivity(i);
                                            MainActivity.releasePlayer();
                                        }
                                    });
                                    lastListened3.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(HomeActivity.this, MainActivity.class);
                                            i.putExtra("track", songTitle.get(2) );
                                            i.putExtra("artist", songArtist.get(2));
                                            i.putExtra("thumbnail", thumbnailUrl.get(2) );
                                            i.putExtra("id", songId.get(2) );
                                            startActivity(i);
                                            MainActivity.releasePlayer();
                                        }
                                    });
                                    break;
                                case 4:
                                    Picasso.get().load(thumbnailUrl.get(0)).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(lastListened1);
                                    Picasso.get().load(thumbnailUrl.get(1)).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(lastListened2);
                                    Picasso.get().load(thumbnailUrl.get(2)).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(lastListened3);
                                    Picasso.get().load(thumbnailUrl.get(3)).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(lastListened4);
                                    lastListened1Text.setText(songTitle.get(0));
                                    lastListened2Text.setText(songTitle.get(1));
                                    lastListened3Text.setText(songTitle.get(2));
                                    lastListened4Text.setText(songTitle.get(3));
                                    lastListened1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(HomeActivity.this, MainActivity.class);
                                            i.putExtra("track", songTitle.get(0) );
                                            i.putExtra("artist", songArtist.get(0));
                                            i.putExtra("thumbnail", thumbnailUrl.get(0) );
                                            i.putExtra("id", songId.get(0) );
                                            startActivity(i);
                                            MainActivity.releasePlayer();
                                        }
                                    });
                                    lastListened2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(HomeActivity.this, MainActivity.class);
                                            i.putExtra("track", songTitle.get(1) );
                                            i.putExtra("artist", songArtist.get(1));
                                            i.putExtra("thumbnail", thumbnailUrl.get(1) );
                                            i.putExtra("id", songId.get(1) );
                                            startActivity(i);
                                            MainActivity.releasePlayer();
                                        }
                                    });
                                    lastListened3.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(HomeActivity.this, MainActivity.class);
                                            i.putExtra("track", songTitle.get(2) );
                                            i.putExtra("artist", songArtist.get(2));
                                            i.putExtra("thumbnail", thumbnailUrl.get(2) );
                                            i.putExtra("id", songId.get(2) );
                                            startActivity(i);
                                            MainActivity.releasePlayer();
                                        }
                                    });
                                    lastListened4.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(HomeActivity.this, MainActivity.class);
                                            i.putExtra("track", songTitle.get(3) );
                                            i.putExtra("artist", songArtist.get(3));
                                            i.putExtra("thumbnail", thumbnailUrl.get(3) );
                                            i.putExtra("id", songId.get(3) );
                                            startActivity(i);
                                            MainActivity.releasePlayer();
                                        }
                                    });
                                    break;
                                case 5:
                                    Picasso.get().load(thumbnailUrl.get(0)).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(lastListened1);
                                    Picasso.get().load(thumbnailUrl.get(1)).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(lastListened2);
                                    Picasso.get().load(thumbnailUrl.get(2)).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(lastListened3);
                                    Picasso.get().load(thumbnailUrl.get(3)).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(lastListened4);
                                    Picasso.get().load(thumbnailUrl.get(4)).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(lastListened5);
                                    lastListened1Text.setText(songTitle.get(0));
                                    lastListened2Text.setText(songTitle.get(1));
                                    lastListened3Text.setText(songTitle.get(2));
                                    lastListened4Text.setText(songTitle.get(3));
                                    lastListened5Text.setText(songTitle.get(4));
                                    lastListened1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(HomeActivity.this, MainActivity.class);
                                            i.putExtra("track", songTitle.get(0) );
                                            i.putExtra("artist", songArtist.get(0));
                                            i.putExtra("thumbnail", thumbnailUrl.get(0) );
                                            i.putExtra("id", songId.get(0) );
                                            startActivity(i);
                                            MainActivity.releasePlayer();
                                        }
                                    });
                                    lastListened2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(HomeActivity.this, MainActivity.class);
                                            i.putExtra("track", songTitle.get(1) );
                                            i.putExtra("artist", songArtist.get(1));
                                            i.putExtra("thumbnail", thumbnailUrl.get(1) );
                                            i.putExtra("id", songId.get(1) );
                                            startActivity(i);
                                            MainActivity.releasePlayer();
                                        }
                                    });
                                    lastListened3.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(HomeActivity.this, MainActivity.class);
                                            i.putExtra("track", songTitle.get(2) );
                                            i.putExtra("artist", songArtist.get(2));
                                            i.putExtra("thumbnail", thumbnailUrl.get(2) );
                                            i.putExtra("id", songId.get(2) );
                                            startActivity(i);
                                            MainActivity.releasePlayer();
                                        }
                                    });
                                    lastListened4.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(HomeActivity.this, MainActivity.class);
                                            i.putExtra("track", songTitle.get(3) );
                                            i.putExtra("artist", songArtist.get(3));
                                            i.putExtra("thumbnail", thumbnailUrl.get(3) );
                                            i.putExtra("id", songId.get(3) );
                                            startActivity(i);
                                            MainActivity.releasePlayer();
                                        }
                                    });
                                    lastListened5.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(HomeActivity.this, MainActivity.class);
                                            i.putExtra("track", songTitle.get(4) );
                                            i.putExtra("artist", songArtist.get(4));
                                            i.putExtra("thumbnail", thumbnailUrl.get(4) );
                                            i.putExtra("id", songId.get(4) );
                                            startActivity(i);
                                            MainActivity.releasePlayer();
                                        }
                                    });
                                    break;
                                case 6:
                                    Picasso.get().load(thumbnailUrl.get(0)).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(lastListened1);
                                    Picasso.get().load(thumbnailUrl.get(1)).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(lastListened2);
                                    Picasso.get().load(thumbnailUrl.get(2)).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(lastListened3);
                                    Picasso.get().load(thumbnailUrl.get(3)).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(lastListened4);
                                    Picasso.get().load(thumbnailUrl.get(4)).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(lastListened5);
                                    Picasso.get().load(thumbnailUrl.get(5)).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(lastListened6);
                                    lastListened1Text.setText(songTitle.get(0));
                                    lastListened2Text.setText(songTitle.get(1));
                                    lastListened3Text.setText(songTitle.get(2));
                                    lastListened4Text.setText(songTitle.get(3));
                                    lastListened5Text.setText(songTitle.get(4));
                                    lastListened6Text.setText(songTitle.get(5));
                                    lastListened1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(HomeActivity.this, MainActivity.class);
                                            i.putExtra("track", songTitle.get(0) );
                                            i.putExtra("artist", songArtist.get(0));
                                            i.putExtra("thumbnail", thumbnailUrl.get(0) );
                                            i.putExtra("id", songId.get(0) );
                                            startActivity(i);
                                            MainActivity.releasePlayer();
                                        }
                                    });
                                    lastListened2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(HomeActivity.this, MainActivity.class);
                                            i.putExtra("track", songTitle.get(1) );
                                            i.putExtra("artist", songArtist.get(1));
                                            i.putExtra("thumbnail", thumbnailUrl.get(1) );
                                            i.putExtra("id", songId.get(1) );
                                            startActivity(i);
                                            MainActivity.releasePlayer();
                                        }
                                    });
                                    lastListened3.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(HomeActivity.this, MainActivity.class);
                                            i.putExtra("track", songTitle.get(2) );
                                            i.putExtra("artist", songArtist.get(2));
                                            i.putExtra("thumbnail", thumbnailUrl.get(2) );
                                            i.putExtra("id", songId.get(2) );
                                            startActivity(i);
                                            MainActivity.releasePlayer();
                                        }
                                    });
                                    lastListened4.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(HomeActivity.this, MainActivity.class);
                                            i.putExtra("track", songTitle.get(3) );
                                            i.putExtra("artist", songArtist.get(3));
                                            i.putExtra("thumbnail", thumbnailUrl.get(3) );
                                            i.putExtra("id", songId.get(3) );
                                            startActivity(i);
                                            MainActivity.releasePlayer();
                                        }
                                    });
                                    lastListened5.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(HomeActivity.this, MainActivity.class);
                                            i.putExtra("track", songTitle.get(4) );
                                            i.putExtra("artist", songArtist.get(4));
                                            i.putExtra("thumbnail", thumbnailUrl.get(4) );
                                            i.putExtra("id", songId.get(4) );
                                            startActivity(i);
                                            MainActivity.releasePlayer();
                                        }
                                    });
                                    lastListened6.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(HomeActivity.this, MainActivity.class);
                                            i.putExtra("track", songTitle.get(5) );
                                            i.putExtra("artist", songArtist.get(5));
                                            i.putExtra("thumbnail", thumbnailUrl.get(5) );
                                            i.putExtra("id", songId.get(5) );
                                            startActivity(i);
                                            MainActivity.releasePlayer();
                                        }
                                    });
                                    break;
                                default:
                                    Log.d("DEFAULT", "DEFAULT");
                            }
                        }
                    }
                });

        recommended1image = findViewById(R.id.Recommended1image);
        recommended4image = findViewById(R.id.Recommended4image);
        recommended1image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this,PlaylistActivity.class);
                i.putExtra("playlistUrl","RDCLAK5uy_l3aBsRkpGKTFr2oQl4PsxfWm0bzJZUTZU");
                startActivity(i);
            }
        });
        recommended4image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this,PlaylistActivity.class);
                i.putExtra("playlistUrl","RDTMAK5uy_lr0LWzGrq6FU9GIxWvFHTRPQD2LHMqlFA");
                startActivity(i);
            }
        });
    }
}
