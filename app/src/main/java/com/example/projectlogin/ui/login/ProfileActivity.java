package com.example.projectlogin.ui.login;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.projectlogin.R;




import com.example.projectlogin.R;


public class ProfileActivity  extends AppCompatActivity {

    ImageButton btnBackProfile;
    Button btnAvatar;
    ImageView ImageAvatar;
    int SELECT_PICTURE = 200;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_menu);
        btnBackProfile = findViewById(R.id.btnBackProfile);
        btnBackProfile.setOnClickListener(view ->{
            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
        });








            btnAvatar = findViewById(R.id. btnAvatar);
            ImageAvatar = findViewById(R.id.ImageAvatar);


            btnAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageChooser();
                    }
                });
            }


            void imageChooser() {


                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
            }


            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);

                if (resultCode == RESULT_OK) {


                    if (requestCode == SELECT_PICTURE) {

                        Uri selectedImageUri = data.getData();
                        if (null != selectedImageUri) {

                            ImageAvatar.setImageURI(selectedImageUri);
                        }
                    }
                }
            }
        }





