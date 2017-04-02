package com.indiana.kupshah.movdex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView WatchListView = (ListView) findViewById(R.id.WatchList);
        Intent i = getIntent();
        ArrayList<Movie> watchlist;
        watchlist = (ArrayList<Movie>) i.getSerializableExtra("watch_list");
        if (watchlist != null) {
            MovieArrayAdapter myAdapter = new MovieArrayAdapter(this, watchlist);
            WatchListView.setAdapter(myAdapter);
        }
    }


    //transition to Seen Movies screen
    public void onSeenButtonClick(View v)
    {
        Intent myIntent = new Intent(MainActivity.this,SeenList.class);
        startActivity(myIntent);
    }

    public void onAddButtonClick(View v)
    {
        Intent myIntent = new Intent(MainActivity.this,AddMovieEntry.class);
        startActivity(myIntent);
    }
}
