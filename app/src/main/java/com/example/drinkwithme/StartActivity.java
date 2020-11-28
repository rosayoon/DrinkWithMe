package com.example.drinkwithme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class StartActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
 YouTubePlayerView youTubePlayerView;
 Button btn;
 YouTubePlayer.OnInitializedListener listener;
 private YouTubePlayerView ytpv;
 private YouTubePlayer ytp;
 final String serverKey = "AIzaSyCUriG-LgMYQ4nv2eArlYuIA-jQHvTd3NA"; //콘솔에서 받아온 서버키를 넣어줍니다


 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_start);
  ytpv = (YouTubePlayerView) findViewById(R.id.youtubeView);
  ytpv.initialize(serverKey, this);

 }

 @Override
 public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                     YouTubePlayer youTubePlayer, boolean b) {
  ytp = youTubePlayer;

  Intent gt =getIntent();
  ytp.loadVideo(gt.getStringExtra("id"));



 }

 @Override
 public void onInitializationFailure(YouTubePlayer.Provider provider,
                                     YouTubeInitializationResult youTubeInitializationResult) {
  Toast.makeText(this, "Initialization Fail", Toast.LENGTH_LONG).show();

 }
}






