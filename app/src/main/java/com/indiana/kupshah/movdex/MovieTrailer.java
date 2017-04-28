package com.indiana.kupshah.movdex;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

//here the movie trailer will be played
//Here I will use the TMDB api and a JSON opener/parser class to find the tmdb title for the
//Movie Title field of movie object which is passed here.
//then i have to use that movie ID to do another query to find the trailer id
//will then use that trailer id to play video in the youtube player

public class MovieTrailer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

    YouTubePlayerView youTubePlayer;
    YouTubePlayer.OnInitializedListener onInitializedListener;

    private String api;
    private String videoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Intent i = getIntent();
        Movie m = (Movie)i.getSerializableExtra("movie");

        this.api = YoutubeAPIKey.getApiKey();

        //youtube id will be generated from an api call using TMDB
        //api call method will be in some TMDB JSON Parser class, I'm not sure how to do this right now
        //TMDB documentation says that the trailers on their site link to Youtube videos and that using the /videos query
        //returns Youtube ID, so the JSON parser will return a string which is the youtube video id, json parser will be
        //a class not an activity
        //method call will look like: this.videoID = JSONparser.getYoutubeID(Movie m)
        //current hard coded id links to trailer for Valerian and the City of a Thousand Planets
        this.videoPath = "NNrK7xVG3PM";

        APIReader reader = new APIReader();
        String movieID = "";
        try {
            movieID = reader.getIDFromTitle(m.getmMovieTitle());
            //System.out.println(movieID);
            Toast.makeText(this, movieID, Toast.LENGTH_LONG ).show();
        }
        catch (Exception e){
            Toast.makeText(this, "Failed to get ID" + e.toString(), Toast.LENGTH_LONG ).show();
        }

        try {
            videoPath = reader.getTrailerFromID(movieID);
            //System.out.println(videoPath);
            Toast.makeText(this, videoPath, Toast.LENGTH_LONG ).show();
        }
        catch(Exception e) {
            //System.out.println("failed to g et trailer path" + e.toString());
            Toast.makeText(this, "Failed to get trailer for " + m.getmMovieTitle(), Toast.LENGTH_LONG ).show();
        }

        //gets YoutubePlayerView view from XML layout
        //Initialize YouTubePlayerView
        youTubePlayer = (YouTubePlayerView)findViewById(R.id.youtubePlayer);
        youTubePlayer.initialize(api,this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.setPlayerStateChangeListener(pscl);
        youTubePlayer.setPlaybackEventListener(pel);

        if(!b) {
            youTubePlayer.loadVideo(videoPath);
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this,"Failure to Initialize video", Toast.LENGTH_LONG).show();
    }

    private YouTubePlayer.PlayerStateChangeListener pscl = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };

    private YouTubePlayer.PlaybackEventListener pel = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };
}