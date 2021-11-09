package com.example.projectlogin.ui.login;
import androidx.annotation.NonNull;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.projectlogin.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button btnLogOut;
    Button btnPlaySong;
    FirebaseAuth mAuth;
    SimpleExoPlayer exoPlayerView;
    PlayerView playerView;
    String songUrl = "https://firebasestorage.googleapis.com/v0/b/projectlogin-c32ae.appspot.com/o/Rick%20Astley%20-%20Never%20Gonna%20Give%20You%20Up.mp3?alt=media&token=c81d934f-5206-41c4-a25c-433494bcc96d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPlayer();

        btnLogOut = findViewById(R.id.btnLogout);
        btnPlaySong = findViewById(R.id.btnPlaySong);
        mAuth = FirebaseAuth.getInstance();

//        btnPlaySong.setOnClickListener(view -> {
//            MediaPlayer mediaPlayer = new MediaPlayer();
//            try{
//                mediaPlayer.setDataSource("https://firebasestorage.googleapis.com/v0/b/projectlogin-c32ae.appspot.com/o/Rick%20Astley%20-%20Never%20Gonna%20Give%20You%20Up.mp3?alt=media&token=c81d934f-5206-41c4-a25c-433494bcc96d");
//                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                    @Override
//                    public void onPrepared(MediaPlayer mediaPlayer) {
//                        mediaPlayer.start();
//                    }
//                });
//
//                mediaPlayer.prepare();
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        });

        btnLogOut.setOnClickListener(view ->{
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });

    }

    public void initPlayer(){
        playerView = findViewById(R.id.playerView);
        playerView.setControllerShowTimeoutMs(0);
        playerView.setCameraDistance(30);
        SimpleExoPlayer simpleExoPlayer = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(simpleExoPlayer);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this,"app"));
        MediaSource audiosource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse("https://firebasestorage.googleapis.com/v0/b/projectlogin-c32ae.appspot.com/o/Rick%20Astley%20-%20Never%20Gonna%20Give%20You%20Up.mp3?alt=media&token=c81d934f-5206-41c4-a25c-433494bcc96d"));
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