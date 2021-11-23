package com.example.projectlogin.ui.login;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.projectlogin.R;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {



    FirebaseAuth mAuth;
    SimpleExoPlayer simpleExoPlayer;
    PlayerView playerView;
    String songUrl = "https://firebasestorage.googleapis.com/v0/b/projectlogin-c32ae.appspot.com/o/Rick%20Astley%20-%20Never%20Gonna%20Give%20You%20Up.mp3?alt=media&token=c81d934f-5206-41c4-a25c-433494bcc96d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                // do your code
                return true;
            case R.id.idBtnSettings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                releasePlayer();
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



    private void releasePlayer() {
        simpleExoPlayer.stop();
    }

    public void initPlayer(){
        playerView = findViewById(R.id.playerView);
        playerView.setControllerShowTimeoutMs(0);
        playerView.setCameraDistance(30);
        simpleExoPlayer = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(simpleExoPlayer);
        DefaultExtractorsFactory extractorsFactory =
                new DefaultExtractorsFactory().setConstantBitrateSeekingEnabled(true);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this,"app"));
        MediaSource audiosource = new ProgressiveMediaSource.Factory(dataSourceFactory, extractorsFactory).createMediaSource(Uri.parse("https://stream-server-youtube.herokuapp.com/dQw4w9WgXcQ"));
        simpleExoPlayer.prepare(audiosource);
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
}