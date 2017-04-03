package com.indiana.kupshah.movdex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;

public class AddMovieEntry extends AppCompatActivity {

    DBHandler movdexDB = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie_entry);
    }


    //transition to WatchList activity where list of movies that are on the watchlist will
    //appear in ListView
    public void onWatchlistButtonClick(View v){
        Intent myIntent = new Intent(AddMovieEntry.this,MainActivity.class);
        myIntent.putExtra("watch_list", movdexDB.getWatchlistMovies());
        startActivity(myIntent);
    }

    //transition to SeenList activity where list of movies that have been seen will
    //appear in ListView
    public void onSeenButtonClick(View v){
        Intent myIntent = new Intent(AddMovieEntry.this,SeenList.class);
        myIntent.putExtra("seen_list", movdexDB.getSeenMovies());
        startActivity(myIntent);
    }

    public void onAddButtonClick(View v){
        //gets View values when addbutton is clicked
        EditText movieTitle = (EditText) findViewById(R.id.TitleText);
        EditText notes = (EditText) findViewById(R.id.NotesTextView);
        EditText recommend = (EditText) findViewById(R.id.RecText);
        EditText disc = (EditText) findViewById(R.id.FoundText);
        CheckBox seen = (CheckBox) findViewById(R.id.SeenCheck);
        CheckBox watch = (CheckBox) findViewById(R.id.WatchCheck);

        //converts View values to types suitable for Movie class
        String addTitle = movieTitle.getText().toString();
        String addNote = notes.getText().toString();
        String addRecommend = recommend.getText().toString();
        String addDiscover = disc.getText().toString();
        Boolean addSeen = seen.isChecked();
        Boolean addWatch = watch.isChecked();

        Movie movie = new Movie (addTitle,addRecommend,addDiscover,addNote,addSeen,addWatch);

        movdexDB.addMovie(movie);
    }

    public void onSelectButtonClick(View v){
        ArrayList<Movie> seenList = movdexDB.getWatchlistMovies();
        for (Movie m : seenList) {
        System.out.println(m.toString());
        }

    }
}
