package com.example.projectlogin.ui.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectlogin.R;
import com.example.projectlogin.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FirebasePlaylistActivity extends AppCompatActivity {

    ListView listView;
    ActivityMainBinding binding;
    ArrayList<String> trackList;
    ArrayList<String> artistName;
    ArrayList<String> playListName;
    ArrayList<String> thumbnailUrl;
    ArrayList<String> songId;
    ListAdapter listAdapter;
    ArrayList<Song> songArrayList;
    Handler mainHandler = new Handler();
    CollectionReference collectionReference;
    ProgressDialog progressDialog;
    ImageView imageView;
    TextView playlistTitle;
    FirebaseFirestore db;
    String typeOfPlaylist;
    String userEmail;
    String urlFinal;
    FirebaseUser user;
    String playlistName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        Intent intent = this.getIntent();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        imageView = findViewById(R.id.Image);
        playlistTitle = findViewById(R.id.PlaylistTitle);
        user = FirebaseAuth.getInstance().getCurrentUser();
        urlFinal = ("https://yma-server.herokuapp.com/playlist/RDTMAK5uy_lr0LWzGrq6FU9GIxWvFHTRPQD2LHMqlFA");
        if (user!=null)
        {
            userEmail= user.getEmail();
        }
        initializeDataList();
        new FirebasePlaylistActivity.fetchData().start();

        if (intent != null){
            typeOfPlaylist = intent.getStringExtra("type");
            switch (typeOfPlaylist){
                case "onRepeat":
                    Picasso.get().load(R.drawable.repeat).placeholder(R.drawable.missingbackground)
                            .error(R.drawable.missingbackground).fit().centerCrop().into(imageView);
                    playlistTitle.setText("On Repeat");
                    collectionReference = db.collection("users").document(userEmail).collection("stats").document("lastListened").collection("listenHistory");
                    break;
                case "history":
                    Picasso.get().load("https://cdn3.iconfinder.com/data/icons/google-material-design-icons/48/ic_history_48px-512.png").placeholder(R.drawable.missingbackground)
                            .error(R.drawable.missingbackground).fit().centerCrop().into(imageView);
                    playlistTitle.setText("History");
                    break;
                case "likedSongs":
                    Picasso.get().load(R.drawable.likedsongs).placeholder(R.drawable.missingbackground)
                            .error(R.drawable.missingbackground).fit().centerCrop().into(imageView);
                    playlistTitle.setText("Liked Songs");
                    collectionReference = db.collection("users").document(userEmail).collection("playlists").document("likedSongs").collection("songs");
                    break;
                case "userCreatedPlaylist":
                    Picasso.get().load("https://lh3.googleusercontent.com/wr28amLh-pMk4vmrYv_Orhly8DTtdvZJFuLwmXG5RNvZJjGlFe_WMnKp4pWlZI1gL7ihQn-xZuzZ0A6VZZbv2Z-iTEH3dpjn").placeholder(R.drawable.missingbackground)
                            .error(R.drawable.missingbackground).fit().centerCrop().into(imageView);
                    playlistName = intent.getStringExtra("name");
                    collectionReference = db.collection("users").document(userEmail).collection("playlists").document(playlistName).collection("songs");
                    break;
            }
        }
        setupBottomNavigation();
    }

    private void initializeDataList() {
        listView = findViewById(R.id.dataList);
        trackList = new ArrayList<String>();
        artistName = new ArrayList<String>();
        thumbnailUrl = new ArrayList<String>();
        playListName = new ArrayList<String>();
        songId = new ArrayList<String>();
        songArrayList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();


        listAdapter = new ListAdapter(this, songArrayList);
        listView.setAdapter(listAdapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent i = new Intent(FirebasePlaylistActivity.this, MainActivity.class);
                i.putExtra("track", trackList.get(position));
                i.putExtra("artist", artistName.get(position));
                i.putExtra("thumbnail", thumbnailUrl.get(position));
                i.putExtra("id", songId.get(position));
                Map<String, Object> song = new HashMap<>();
                song.put("artist",artistName.get(position));
                song.put("songId", songId.get(position));
                song.put("thumbnailUrl", thumbnailUrl.get(position));
                song.put("title", trackList.get(position));
                song.put("timestamp", new Timestamp(new Date()));
                db.collection("users").document(userEmail).collection("stats").document("lastListened").collection("listenHistory").document(trackList.get(position)).set(song, SetOptions.merge());
                db.collection("users").document(userEmail).collection("stats").document("lastListened").collection("listenHistory").document(trackList.get(position)).update("repeat", FieldValue.increment(1));
                i.putExtra("listLength",trackList.size());
                i.putExtra("songIdArray",songId);
                i.putExtra("artistArray", artistName);
                i.putExtra("thumbnailArray", thumbnailUrl);
                i.putExtra("trackArray", trackList);
                i.putExtra("position",position);
                startActivity(i);
                MainActivity.releasePlayer();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String songTitle=trackList.get(i);
                String songArtist = artistName.get(i);
                String songIdNum = songId.get(i);
                String songThumbnail = thumbnailUrl.get(i);
                showAlert(songTitle,songThumbnail,songIdNum,songArtist,userEmail);
                return true;
            }
        });
    }

    AlertDialog myDialog;
    AlertDialog createDialog;
    ArrayList<String> playlistsDialog;
    CharSequence[] playlistDialogFinal = {};


    private void showAlert(String songTitle, String songThumbnail, String songIdNum, String songArtist, String userEmail) {
        playlistsDialog = new ArrayList<>();
        playlistDialogFinal = playlistsDialog.toArray(new CharSequence[playlistsDialog.size()]);
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(this);
        playlistsDialog.add("+ Create A Playlist");
        db.collection("users").document(userEmail).collection("playlists")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                playlistsDialog.add(document.getId());
                            }
                            playlistDialogFinal = playlistsDialog.toArray(new CharSequence[playlistsDialog.size()]);
                            myBuilder.setTitle("Add to playlist").setItems(playlistDialogFinal, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(i==0){
                                        AlertDialog.Builder createBuilder = new AlertDialog.Builder(FirebasePlaylistActivity.this);
                                        LayoutInflater inflater = FirebasePlaylistActivity.this.getLayoutInflater();
                                        createBuilder.setView(inflater.inflate(R.layout.create_playlist,null)).setPositiveButton("Create and add", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Dialog dlg = (Dialog) createDialog;
                                                EditText etPlaylistTile= (EditText) dlg.findViewById(R.id.playlistName);

                                                String titlePlaylist = etPlaylistTile.getText().toString();
                                                if(TextUtils.isEmpty(titlePlaylist)){
                                                    etPlaylistTile.setError("Title cannot be empty");
                                                    etPlaylistTile.requestFocus();
                                                }
                                                else {
                                                    Map<String, Object> song = new HashMap<>();
                                                    song.put("artist", songArtist);
                                                    song.put("title", songTitle);
                                                    song.put("songId", songIdNum);
                                                    song.put("thumbnailUrl", songThumbnail);
                                                    song.put("timestamp", new Timestamp(new Date()));
                                                    Map<String, Object> initializer = new HashMap<>();
                                                    initializer.put("init","init");
                                                    db.collection("users").document(userEmail).collection("playlists").document(titlePlaylist).set(initializer);
                                                    db.collection("users").document(userEmail).collection("playlists").document(titlePlaylist).collection("songs").document(songTitle).set(song);
                                                    Toast.makeText(FirebasePlaylistActivity.this, "Added to playlist", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                createDialog.hide();
                                            }
                                        });
                                        createDialog = createBuilder.create();
                                        createDialog.show();
                                    }
                                    else {
                                        Map<String, Object> song = new HashMap<>();
                                        song.put("artist", songArtist);
                                        song.put("title", songTitle);
                                        song.put("songId", songIdNum);
                                        song.put("thumbnailUrl", songThumbnail);
                                        song.put("timestamp", new Timestamp(new Date()));
                                        db.collection("users").document(userEmail).collection("playlists").document(playlistDialogFinal[i].toString()).collection("songs").document(songTitle).set(song);
                                        Toast.makeText(FirebasePlaylistActivity.this, "Added to playlist", Toast.LENGTH_SHORT).show();                                    }
                                }
                            });
                            myDialog=myBuilder.create();
                            myDialog.show();
                        } else {
                            Log.w("ERROR", "Error getting documents.", task.getException());
                        }}
                });
    }

    class fetchData extends  Thread {

        @Override
        public void run() {

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    List<String> list = Arrays.asList(
                            "Reticulating splines...",
                            "Generating witty dialog...",
                            "Swapping time and space...",
                            "Spinning violently around the y-axis...",
                            "Tokenizing real life...",
                            "Bending the spoon...",
                            "Filtering morale...",
                            "Don't think of purple hippos...",
                            "We need a new fuse...",
                            "Have a good day.",
                            "Upgrading Windows, your PC will restart several times. Sit back and relax.",
                            "640K ought to be enough for anybody",
                            "The architects are still drafting",
                            "The bits are breeding",
                            "We're building the buildings as fast as we can",
                            "Would you prefer chicken, steak, or tofu?",
                            "(Pay no attention to the man behind the curtain)",
                            "...and enjoy the elevator music...",
                            "Please wait while the little elves draw your map",
                            "Don't worry - a few bits tried to escape, but we caught them",
                            "Would you like fries with that?",
                            "Checking the gravitational constant in your locale...",
                            "Go ahead -- hold your breath!",
                            "...at least you're not on hold...",
                            "Hum something loud while others stare",
                            "You're not in Kansas any more",
                            "The server is powered by a lemon and two electrodes.",
                            "Please wait while a larger software vendor in Seattle takes over the world",
                            "We're testing your patience",
                            "As if you had any other choice",
                            "Follow the white rabbit",
                            "Why don't you order a sandwich?",
                            "While the satellite moves into position",
                            "keep calm and npm install",
                            "The bits are flowing slowly today",
                            "Dig on the 'X' for buried treasure... ARRR!",
                            "It's still faster than you could draw it",
                            "The last time I tried this the monkey didn't survive. Let's hope it works better this time.",
                            "I should have had a V8 this morning.",
                            "My other loading screen is much faster.",
                            "Testing on Timmy... We're going to need another Timmy.",
                            "Reconfoobling energymotron...",
                            "(Insert quarter)",
                            "Are we there yet?",
                            "Have you lost weight?",
                            "Just count to 10",
                            "Why so serious?",
                            "It's not you. It's me.",
                            "Counting backwards from Infinity",
                            "Don't panic...",
                            "Embiggening Prototypes",
                            "Do not run! We are your friends!",
                            "Do you come here often?",
                            "Warning: Don't set yourself on fire.",
                            "We're making you a cookie.",
                            "Creating time-loop inversion field",
                            "Spinning the wheel of fortune...",
                            "Loading the enchanted bunny...",
                            "Computing chance of success",
                            "I'm sorry Dave, I can't do that.",
                            "Looking for exact change",
                            "All your web browser are belong to us",
                            "All I really need is a kilobit.",
                            "I feel like im supposed to be loading something. . .",
                            "What do you call 8 Hobbits? A Hobbyte.",
                            "Should have used a compiled language...",
                            "Is this Windows?",
                            "Adjusting flux capacitor...",
                            "Please wait until the sloth starts moving.",
                            "Don't break your screen yet!",
                            "I swear it's almost done.",
                            "Let's take a mindfulness minute...",
                            "Unicorns are at the end of this road, I promise.",
                            "Listening for the sound of one hand clapping...",
                            "Keeping all the 1's and removing all the 0's...",
                            "Putting the icing on the cake. The cake is not a lie...",
                            "Cleaning off the cobwebs...",
                            "Making sure all the i's have dots...",
                            "We need more dilithium crystals",
                            "Where did all the internets go",
                            "Connecting Neurotoxin Storage Tank...",
                            "Granting wishes...",
                            "Time flies when you’re having fun.",
                            "Get some coffee and come back in ten minutes..",
                            "Spinning the hamster…",
                            "99 bottles of beer on the wall..",
                            "Stay awhile and listen..",
                            "Be careful not to step in the git-gui",
                            "You edhall not pass! yet..",
                            "Load it and they will come",
                            "Convincing AI not to turn evil..",
                            "There is no spoon. Because we are not done loading it",
                            "Your left thumb points to the right and your right thumb points to the left.",
                            "How did you get here?",
                            "Wait, do you smell something burning?",
                            "Computing the secret to life, the universe, and everything.",
                            "When nothing is going right, go left!!...",
                            "I love my job only when I'm on vacation...",
                            "i'm not lazy, I'm just relaxed!!",
                            "Never steal. The government hates competition....",
                            "Why are they called apartments if they are all stuck together?",
                            "Life is Short – Talk Fast!!!!",
                            "Optimism – is a lack of information.....",
                            "Save water and shower together",
                            "Whenever I find the key to success, someone changes the lock.",
                            "Sometimes I think war is God’s way of teaching us geography.",
                            "I’ve got problem for your solution…..",
                            "Where there’s a will, there’s a relative.",
                            "User: the word computer professionals use when they mean !!idiot!!",
                            "Adults are just kids with money.",
                            "I think I am, therefore, I am. I think.",
                            "A kiss is like a fight, with mouths.",
                            "You don’t pay taxes—they take taxes.",
                            "Coffee, Chocolate, Men. The richer the better!",
                            "I am free of all prejudices. I hate everyone equally.",
                            "git happens",
                            "May the forks be with you",
                            "A commit a day keeps the mobs away",
                            "This is not a joke, it's a commit.",
                            "Constructing additional pylons...",
                            "Roping some seaturtles...",
                            "Locating Jebediah Kerman...",
                            "We are not liable for any broken screens as a result of waiting.",
                            "Hello IT, have you tried turning it off and on again?",
                            "If you type Google into Google you can break the internet",
                            "Well, this is embarrassing.",
                            "What is the airspeed velocity of an unladen swallow?",
                            "Hello, IT... Have you tried forcing an unexpected reboot?",
                            "They just toss us away like yesterday's jam.",
                            "They're fairly regular, the beatings, yes. I'd say we're on a bi-weekly beating.",
                            "The Elders of the Internet would never stand for it.",
                            "Space is invisible mind dust, and stars are but wishes.",
                            "Didn't know paint dried so quickly.",
                            "Everything sounds the same",
                            "I'm going to walk the dog",
                            "I didn't choose the engineering life. The engineering life chose me.",
                            "Dividing by zero...",
                            "Spawn more Overlord!",
                            "If I’m not back in five minutes, just wait longer.",
                            "Some days, you just can’t get rid of a bug!",
                            "We’re going to need a bigger boat.",
                            "Chuck Norris never git push. The repo pulls before.",
                            "Web developers do it with <style>",
                            "I need to git pull --my-life-together",
                            "Java developers never RIP. They just get Garbage Collected.",
                            "Cracking military-grade encryption...",
                            "Simulating traveling salesman...",
                            "Proving P=NP...",
                            "Entangling superstrings...",
                            "Twiddling thumbs...",
                            "Searching for plot device...",
                            "Trying to sort in O(n)...",
                            "Laughing at your pictures-i mean, loading...",
                            "Sending data to NS-i mean, our servers.",
                            "Looking for sense of humour, please hold on.",
                            "Please wait while the intern refills his coffee.",
                            "A different error message? Finally, some progress!",
                            "Hold on while we wrap up our git together...sorry",
                            "Please hold on as we reheat our coffee",
                            "Kindly hold on as we convert this bug to a feature...",
                            "Kindly hold on as our intern quits vim...",
                            "Winter is coming...",
                            "Installing dependencies",
                            "Switching to the latest JS framework...",
                            "Distracted by cat gifs",
                            "Finding someone to hold my beer",
                            "BRB, working on my side project",
                            "@todo Insert witty loading message",
                            "Let's hope it's worth the wait",
                            "Aw, snap! Not..",
                            "Ordering 1s and 0s...",
                            "Updating dependencies...",
                            "Whatever you do, don't look behind you...",
                            "Please wait... Consulting the manual...",
                            "It is dark. You're likely to be eaten by a grue.",
                            "Loading funny message...",
                            "It's 10:00pm. Do you know where your children are?",
                            "Waiting Daenerys say all her titles...",
                            "Feel free to spin in your chair",
                            "What the what?",
                            "format C: ...",
                            "Forget you saw that password I just typed into the IM ...",
                            "What's under there?",
                            "Your computer has a virus, its name is Windows!",
                            "Go ahead, hold your breath and do an ironman plank till loading complete",
                            "Bored of slow loading spinner, buy more RAM!",
                            "Help, I'm trapped in a loader!",
                            "What is the difference btwn a hippo and a zippo? One is really heavy, the other is a little lighter",
                            "Please wait, while we purge the Decepticons for you. Yes, You can thanks us later!",
                            "Chuck Norris once urinated in a semi truck's gas tank as a joke....that truck is now known as Optimus Prime.",
                            "Chuck Norris doesn’t wear a watch. HE decides what time it is.",
                            "Mining some bitcoins...",
                            "Downloading more RAM..",
                            "Updating to Windows Vista...",
                            "Deleting System32 folder",
                            "Hiding all ;'s in your code",
                            "Alt-F4 speeds things up.",
                            "Initializing the initializer...",
                            "When was the last time you dusted around here?",
                            "Optimizing the optimizer...",
                            "Last call for the data bus! All aboard!",
                            "Running swag sticker detection...",
                            "Never let a computer know you're in a hurry.",
                            "A computer will do what you tell it to do, but that may be much different from what you had in mind.",
                            "Some things man was never meant to know. For everything else, there's Google.",
                            "Unix is user-friendly. It's just very selective about who its friends are.",
                            "Shovelling coal into the server",
                            "Pushing pixels...",
                            "How about this weather, eh?",
                            "Building a wall...",
                            "Everything in this universe is either a potato or not a potato",
                            "The severity of your issue is always lower than you expected.",
                            "Updating Updater...",
                            "Downloading Downloader...",
                            "Debugging Debugger...",
                            "Reading Terms and Conditions for you.",
                            "Digested cookies being baked again.",
                            "Live long and prosper.",
                            "There is no cow level, but there's a goat one!",
                            "Deleting all your hidden porn...",
                            "Running with scissors...",
                            "Definitely not a virus...",
                            "You may call me Steve.",
                            "You seem like a nice person...",
                            "Coffee at my place, tommorow at 10A.M. - don't be late!",
                            "Work, work...",
                            "Patience! This is difficult, you know...",
                            "Discovering new ways of making you wait...",
                            "Your time is very important to us. Please wait while we ignore you...",
                            "Time flies like an arrow; fruit flies like a banana",
                            "Two men walked into a bar; the third ducked...",
                            "Sooooo... Have you seen my vacation photos yet?",
                            "Sorry we are busy catching em' all, we're done soon",
                            "TODO: Insert elevator music",
                            "Still faster than Windows update",
                            "Composer hack: Waiting for reqs to be fetched is less frustrating if you add -vvv to your command.",
                            "Please wait while the minions do their work",
                            "Grabbing extra minions",
                            "Doing the heavy lifting",
                            "We're working very Hard .... Really",
                            "Waking up the minions",
                            "You are number 2843684714 in the queue",
                            "Please wait while we serve other customers...",
                            "Our premium plan is faster",
                            "Feeding unicorns...");
                    Random rand = new Random();
                    String randomElement = list.get(rand.nextInt(list.size()));
                    progressDialog = new ProgressDialog(FirebasePlaylistActivity.this);
                    progressDialog.setMessage(randomElement);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            });

            try {
                if(typeOfPlaylist.equals("onRepeat")){
                    collectionReference.orderBy("repeat", Query.Direction.DESCENDING).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if(typeOfPlaylist.equals("userCreatedPlaylist")){
                                        playlistTitle.setText(playlistName);
                                    }
                                    trackList.clear();
                                    songId.clear();
                                    artistName.clear();
                                    thumbnailUrl.clear();
                                    playListName.clear();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String trackName = (String) document.get("title");
                                        String id = (String) document.get("songId");
                                        String name = (String) document.get("artist");
                                        String songThumbnailFinal = (String) document.get("thumbnailUrl");

                                        trackList.add(trackName);
                                        songId.add(id);
                                        artistName.add(name);
                                        thumbnailUrl.add(songThumbnailFinal);
                                        playListName.add("On Repeat");

                                        for (int l = 0; l < 1; l++) {
                                            Song song = new Song(trackName, name, id, "Greece", songThumbnailFinal);
                                            songArrayList.add(song);
                                        }

                                    }
                                } else {
                                    Log.d("FIREBASERESPONSE", "Error getting documents: ", task.getException());
                                }
                            }
                        });
                }
                else if (typeOfPlaylist.equals("history")){
                    db.collection("users").document(userEmail).collection("stats").document("lastListened").collection("listenHistory").orderBy("timestamp", Query.Direction.DESCENDING).limit(50).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if (typeOfPlaylist.equals("userCreatedPlaylist")) {
                                            playlistTitle.setText(playlistName);
                                        }
                                        trackList.clear();
                                        songId.clear();
                                        artistName.clear();
                                        thumbnailUrl.clear();
                                        playListName.clear();
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            String trackName = (String) document.get("title");
                                            String id = (String) document.get("songId");
                                            String name = (String) document.get("artist");
                                            String songThumbnailFinal = (String) document.get("thumbnailUrl");

                                            trackList.add(trackName);
                                            songId.add(id);
                                            artistName.add(name);
                                            thumbnailUrl.add(songThumbnailFinal);
                                            playListName.add("On Repeat");

                                            for (int l = 0; l < 1; l++) {
                                                Song song = new Song(trackName, name, id, "Greece", songThumbnailFinal);
                                                songArrayList.add(song);
                                                Log.d("FIREBASERESPONSE", song.name);
                                            }

                                        }
                                    } else {
                                        Log.d("FIREBASERESPONSE", "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                } else {
                    collectionReference.get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if (typeOfPlaylist.equals("userCreatedPlaylist")) {
                                            playlistTitle.setText(playlistName);
                                        }
                                        trackList.clear();
                                        songId.clear();
                                        artistName.clear();
                                        thumbnailUrl.clear();
                                        playListName.clear();
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            String trackName = (String) document.get("title");
                                            String id = (String) document.get("songId");
                                            String name = (String) document.get("artist");
                                            String songThumbnailFinal = (String) document.get("thumbnailUrl");

                                            trackList.add(trackName);
                                            songId.add(id);
                                            artistName.add(name);
                                            thumbnailUrl.add(songThumbnailFinal);
                                            playListName.add("On Repeat");

                                            for (int l = 0; l < 1; l++) {
                                                Song song = new Song(trackName, name, id, "Greece", songThumbnailFinal);
                                                songArrayList.add(song);
                                                Log.d("FIREBASERESPONSE", song.name);
                                            }

                                        }
                                    } else {
                                        Log.d("FIREBASERESPONSE", "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                }

                // JUNK CODE TO GET TO LOAD BECAUSE TOO MUCH WORK TO BUILD NEW ACTIVITY FROM SCRATCH
                URL url = new URL(urlFinal);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    listAdapter.notifyDataSetChanged();
                }
            });

        }
    }

    private void setupBottomNavigation(){
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationHelper.enableNavigation(FirebasePlaylistActivity.this,bottomNavigationViewEx);

        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
    }
}
