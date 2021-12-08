package com.example.projectlogin.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectlogin.R;

public class HomeActivity extends AppCompatActivity {

    ImageView recommended1image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);
        recommended1image = findViewById(R.id.Recommended1image);
        recommended1image.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this,FetchDataTest.class));
        });
    }
}
