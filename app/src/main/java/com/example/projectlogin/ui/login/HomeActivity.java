package com.example.projectlogin.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectlogin.R;

public class HomeActivity extends AppCompatActivity {

    ImageView recommended1image;
    ImageView recommended4image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);
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
