package com.example.projectlogin.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.projectlogin.R;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.common.collect.ImmutableList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {


    FirebaseAuth mAuth;
    public static SimpleExoPlayer simpleExoPlayer;
    PlayerView playerView;
    String songId;
    String thumbnail;
    String url;
    ImageView imageProfile;
    ImageView albumImage;
    TextView songTitle, songArtist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        albumImage = findViewById(R.id.AlbumImage);
        Intent intent = this.getIntent();
        songTitle = findViewById (R.id.songTitle);
        songArtist = findViewById (R.id.songArtist);
        songArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,FetchDataTest.class);
                i.putExtra("artist",songArtist.getText().toString());
                startActivity(i);
            }
        });
//        songArtist.setOnClickListener(view -> {
//            startActivity(new Intent(MainActivity.this,FetchDataTest.class));
//        });

        if (intent != null) {
            String track = intent.getStringExtra("track");
            String artist = intent.getStringExtra("artist");
            thumbnail = intent.getStringExtra("thumbnail");
            songId = intent.getStringExtra("id");
            String replacedThumbnail = thumbnail.replaceAll("(=).*"," ");
            songTitle.setText(track);
            songArtist.setText(artist);
            Picasso.get().load(replacedThumbnail).placeholder(R.drawable.missingbackground).error(R.drawable.missingbackground).fit().centerCrop().into(albumImage);
        }
        url = ("https://stream-server-youtube.herokuapp.com/"+songId);
        initPlayer();
        ImageButton btn = (ImageButton) findViewById(R.id.btnShow);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MainActivity.this, v);
                popup.setOnMenuItemClickListener(MainActivity.this);
                popup.inflate(R.menu.menu_example);
                popup.show();
            }

        });

        mAuth = FirebaseAuth.getInstance();

        final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                //releasePlayer();
                return true;
            case R.id.idBtnSettings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                //releasePlayer();
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                releasePlayer();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                return true;
            default:
                return true;
        }
    }
    public static void releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.setPlayWhenReady(false);
            simpleExoPlayer.stop();
            simpleExoPlayer.seekTo(0);
        }
    }

    public void initPlayer(){
        List<MediaItem> newItems = ImmutableList.of(
                MediaItem.fromUri(Uri.parse(url)));
        playerView = findViewById(R.id.playerView);
        playerView.setControllerShowTimeoutMs(0);
        playerView.setCameraDistance(30);
        simpleExoPlayer = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(simpleExoPlayer);
        simpleExoPlayer.setMediaItems(newItems,true);
        simpleExoPlayer.prepare();
        simpleExoPlayer.setPlayWhenReady(true);
    }


    @Override

    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imageUri = data.getData();
                imageProfile.setImageURI(imageUri);
            }
        }

    }

}



