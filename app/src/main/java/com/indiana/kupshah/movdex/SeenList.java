package com.indiana.kupshah.movdex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class SeenList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seen_list);

        //finds list view and populates from movies from the seen list,
        //using the layout established in the MovieAdapter arrayadapter class
        ListView SeenListView = (ListView) findViewById(R.id.SeenList);

        Intent i = getIntent();
        ArrayList<Movie> seenList;
        //sets the list of seen and watched movies
        seenList = (ArrayList<Movie>)i.getSerializableExtra("seenlist_movies");

        for (Movie movie : seenList) {
            System.out.println(movie.toString());
        }
        //MovieArrayAdapter myAdapter = new MovieArrayAdapter(this, seenList);
        //SeenListView.setAdapter(myAdapter);
    }


    public void onWatchlistButtonClick(View v){
        Intent myIntent = new Intent(SeenList.this,MainActivity.class);
        startActivity(myIntent);
    }

    public void onAddButtonClick(View v){
        Intent myIntent = new Intent(SeenList.this,AddMovieEntry.class);
        startActivity(myIntent);
    }
}
