package com.indiana.kupshah.movdex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class SeenList extends AppCompatActivity {

    private DBHandler movdexDB = new DBHandler(this);
    private ArrayList<Movie> seenList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seen_list);

        //finds list view and populates from movies from the seen list,
        //using the layout established in the MovieAdapter arrayadapter class
        ListView SeenListView = (ListView) findViewById(R.id.SeenList);

        Intent i = getIntent();

        //gets the list of seen movies
        seenList = (ArrayList<Movie>)i.getSerializableExtra("seen_list");

        //populates the listview with seen movies if seenList is not null
        if (seenList != null){
        MovieArrayAdapter myAdapter = new MovieArrayAdapter(this, seenList);
        SeenListView.setAdapter(myAdapter);}

        //Item click listener, when clicked transitions to DisplayInfo activity to get movie information
        SeenListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = (Movie)parent.getAdapter().getItem(position);
                System.out.println(movie.toString());
                Intent i = new Intent(SeenList.this, DisplayInfo.class);
                i.putExtra("movie",movie);
                startActivity(i);
            }
        });
    }


    //transition to MainActivity which displays the watchlist
    public void onWatchlistButtonClick(View v){
        Intent myIntent = new Intent(SeenList.this,MainActivity.class);
        startActivity(myIntent);
    }

    //transition to AddMovieEntry activity where user can add a movie
    public void onAddButtonClick(View v){
        Intent myIntent = new Intent(SeenList.this,AddMovieEntry.class);
        startActivity(myIntent);
    }
}
