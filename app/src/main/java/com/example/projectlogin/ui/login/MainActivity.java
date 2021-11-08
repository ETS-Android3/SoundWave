package com.example.projectlogin.ui.login;
import androidx.annotation.NonNull;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.projectlogin.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogOut = findViewById(R.id.btnLogout);
        btnPlaySong = findViewById(R.id.btnPlaySong);
        mAuth = FirebaseAuth.getInstance();

        btnPlaySong.setOnClickListener(view -> {
            MediaPlayer mediaPlayer = new MediaPlayer();
            try{
                mediaPlayer.setDataSource("https://firebasestorage.googleapis.com/v0/b/projectlogin-c32ae.appspot.com/o/Rick%20Astley%20-%20Never%20Gonna%20Give%20You%20Up.mp3?alt=media&token=c81d934f-5206-41c4-a25c-433494bcc96d");
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.prepare();
            }catch (IOException e){
                e.printStackTrace();
            }
        });

        btnLogOut.setOnClickListener(view ->{
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });

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