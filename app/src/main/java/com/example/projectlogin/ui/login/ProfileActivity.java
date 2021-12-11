package com.example.projectlogin.ui.login;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectlogin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class ProfileActivity  extends AppCompatActivity {

    ImageButton btnBackProfile;
    Button btnAvatar;
    ImageButton ImageAvatar;
    TextView emailText;
    TextView phoneText;
    TextView userNameText;
    FirebaseFirestore db;
    String userEmail;
    ProgressDialog progressDialog;
    int SELECT_PICTURE = 200;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_menu);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null)
        {
            userEmail= user.getEmail();
        }
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(userEmail);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                progressDialog = new ProgressDialog(ProfileActivity.this);
                progressDialog.setMessage("Loading");
                progressDialog.setCancelable(false);
                progressDialog.show();
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("DOCUMENTDATA", "DocumentSnapshot data: " + document.getData());
                        emailText.setText(document.getString("email"));
                        phoneText.setText(document.getString("phoneNumber"));
                        userNameText.setText(document.getString("username"));
                        progressDialog.dismiss();
                    } else {
                        Log.d("DOCUMENTMISSING", "No such document");
                        progressDialog.dismiss();
                    }
                } else {
                    Log.d("DOCUMENTERROR", "get failed with ", task.getException());
                    progressDialog.dismiss();
                }
            }
        });
        ImageAvatar = findViewById(R.id. ImageAvatar);
        ImageAvatar = findViewById(R.id.ImageAvatar);
        emailText = findViewById(R.id.Email);
        phoneText = findViewById(R.id.Phone);
        userNameText = findViewById(R.id.Username);

        ImageAvatar.setOnClickListener(new View.OnClickListener() {
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





