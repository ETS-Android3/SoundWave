package com.example.projectlogin.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectlogin.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class SearchActivity extends AppCompatActivity {

    EditText searchText;
    Button searchButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        searchText = findViewById(R.id.SearchQuery);
        searchButton = findViewById(R.id.SearchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchQuery = searchText.getText().toString();

                if (TextUtils.isEmpty(searchQuery)){
                    searchText.setError("No empty queries allowed.");
                    searchText.requestFocus();
                }else{

                    Intent i = new Intent(SearchActivity.this, SearchResultsActivity.class);
                    i.putExtra("query",searchQuery);
                    startActivity(i);

                }
            }
        });

        setupBottomNavigation();

    }

    private void setupBottomNavigation(){
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationHelper.enableNavigation(SearchActivity.this,bottomNavigationViewEx);

        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
    }
}
