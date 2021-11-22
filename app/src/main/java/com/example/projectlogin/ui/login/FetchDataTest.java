package com.example.projectlogin.ui.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectlogin.R;
import com.example.projectlogin.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FetchDataTest extends AppCompatActivity {

    Button button;
    ListView listView;
    ActivityMainBinding binding;
    ArrayList<String> trackList;
    ArrayAdapter<String> listAdapter;
    Handler mainHandler = new Handler();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_json_test);
        initializeDataList();

        button = findViewById(R.id.buttonFetch);


        button.setOnClickListener(view -> {
            new fetchData().start();
        });

    }

    private void initializeDataList() {
        listView = findViewById(R.id.dataList);
        trackList = new ArrayList<>();
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,trackList);
        listView.setAdapter(listAdapter);
    }

    class fetchData extends  Thread {

        String data = "";

        @Override
        public void run() {

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    progressDialog = new ProgressDialog(FetchDataTest.this);
                    progressDialog.setMessage("Fetching...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            });

            try {
                URL url = new URL("https://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=eb3c89bc85f56023f61a0bef5f57befa&format=json");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while((line = bufferedReader.readLine()) != null){
                    data = data + line;
                }

                if (!data.isEmpty()){
                    JSONObject jsonObject = new JSONObject(data);
                    JSONObject dataLong = jsonObject.getJSONObject("tracks");
                    JSONArray data = dataLong.getJSONArray("track");

                    trackList.clear();

                    for (int i =0; i< data.length(); i++){
                        JSONObject tracks = data.getJSONObject(i);
                        String trackName = tracks.getString("name");
                        trackList.add(trackName);
                        Log.d("Track Name:",trackName);
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    listAdapter.notifyDataSetChanged();
                }
            });

        }
    }
}
