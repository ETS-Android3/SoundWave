package com.example.projectlogin.ui.login

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.android.exoplayer2.ui.PlayerView
import android.widget.TextView
import android.os.Bundle
import com.example.projectlogin.R
import android.content.Intent
import com.example.projectlogin.ui.login.FetchDataTest
import com.squareup.picasso.Picasso
import com.example.projectlogin.ui.login.MainActivity
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.firebase.auth.FirebaseUser
import com.example.projectlogin.ui.login.LoginActivity
import android.app.Activity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.example.projectlogin.ui.login.ProfileActivity
import com.example.projectlogin.ui.login.HomeActivity
import com.example.projectlogin.ui.login.PlaylistActivity
import com.example.projectlogin.ui.login.SettingsActivity
import com.google.android.exoplayer2.MediaItem
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    var mAuth: FirebaseAuth? = null
    var playerView: PlayerView? = null
    var thumbnail: String? = null
    var imageProfile: ImageView? = null
    var albumImage: ImageView? = null
    var songTitle: TextView? = null
    var songArtist: TextView? = null
    var songIdArray: ArrayList<String>? = null
    var songArtistArray: ArrayList<String>? = null
    var thumbnailArray: ArrayList<String>? = null
    var songTitleArray: ArrayList<String>? = null
    var positionPlaylist = 0
    var listLength = 0
    var lastWindowIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        albumImage = findViewById(R.id.AlbumImage)
        val intent = this.intent
        songIdArray = ArrayList()
        songArtistArray = ArrayList()
        thumbnailArray = ArrayList()
        songTitleArray = ArrayList()
        positionPlaylist = 0
        listLength = 0
        songTitle = findViewById(R.id.video_overlay_title)
        songArtist = findViewById(R.id.songArtist)
        songArtist?.setOnClickListener(View.OnClickListener {
            val i = Intent(this@MainActivity, FetchDataTest::class.java)
            i.putExtra("artist", songArtist!!.getText().toString())
            startActivity(i)
        })
        if (intent != null) {
            val track = intent.getStringExtra("track")
            val artist = intent.getStringExtra("artist")
            thumbnail = intent.getStringExtra("thumbnail")
            val replacedThumbnail = thumbnail!!.replace("(=).*".toRegex(), " ")
            songTitle?.setText(track)
            songArtist?.setText(artist)
            positionPlaylist = intent.getSerializableExtra("position") as Int
            Picasso.get().load(replacedThumbnail).placeholder(R.drawable.missingbackground)
                .error(R.drawable.missingbackground).fit().centerCrop().into(albumImage)
            listLength = intent.getSerializableExtra("listLength") as Int
            songIdArray = intent.getSerializableExtra("songIdArray") as ArrayList<String>?
            songArtistArray = intent.getSerializableExtra("artistArray") as ArrayList<String>?
            thumbnailArray = intent.getSerializableExtra("thumbnailArray") as ArrayList<String>?
            songTitleArray = intent.getSerializableExtra("trackArray") as ArrayList<String>?
        }
        initPlayer()
        for (i in 0 until listLength) {
            simpleExoPlayer!!.addMediaItem(
                i,
                MediaItem.fromUri("https://stream-server-youtube.herokuapp.com/" + songIdArray!![i])
            )
        }
        simpleExoPlayer!!.seekTo(positionPlaylist, C.TIME_UNSET)
        simpleExoPlayer!!.addListener(object : Player.Listener {
            //@Override
            override fun onPositionDiscontinuity(DISCONTINUITY_REASON_SKIP: Int) {
                val latestWindowIndex = simpleExoPlayer!!.currentWindowIndex
                if (latestWindowIndex != lastWindowIndex) {
                    lastWindowIndex = latestWindowIndex
                    songTitle?.setText(songTitleArray!![latestWindowIndex])
                    songArtist?.setText(songArtistArray!![latestWindowIndex])
                    thumbnail = thumbnailArray!![latestWindowIndex]
                    val replacedThumbnail = thumbnail!!.replace("(=).*".toRegex(), " ")
                    Picasso.get().load(replacedThumbnail).placeholder(R.drawable.missingbackground)
                        .error(R.drawable.missingbackground).fit().centerCrop().into(albumImage)
                }
            }
        })
        mAuth = FirebaseAuth.getInstance()

    }

    fun initPlayer() {
        playerView = findViewById(R.id.playerView)
        playerView?.setControllerShowTimeoutMs(0)
        playerView?.setCameraDistance(30f)
        simpleExoPlayer = SimpleExoPlayer.Builder(this).build()
        playerView?.setPlayer(simpleExoPlayer)
        simpleExoPlayer!!.prepare()
        simpleExoPlayer!!.playWhenReady = true
    }

    companion object {
        var simpleExoPlayer: SimpleExoPlayer? = null
        @JvmStatic
        fun releasePlayer() {
            if (simpleExoPlayer != null) {
                simpleExoPlayer!!.playWhenReady = false
                simpleExoPlayer!!.stop()
                simpleExoPlayer!!.seekTo(0)
            }
        }
    }
}