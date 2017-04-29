package com.indiana.kupshah.movdex;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.indiana.kupshah.movdex.R.id.WatchList;

//for now, watchlist and seen list only appear when transitioning from AddMovieEntry activity
//need help with this - passing database between activities

public class MainActivity extends AppCompatActivity {

    private DBHandler movedexDB = new DBHandler(this);
    private ArrayList<Movie> watchlist;
    private ArrayList<Movie> seenlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView WatchListView = (ListView) findViewById(WatchList);
        Intent i = getIntent();

        //gets the list of movies on watchlist
        watchlist = (ArrayList<Movie>) i.getSerializableExtra("watch_list");
        seenlist = (ArrayList<Movie>) i.getSerializableExtra("seen_list");

        //populates watchlist if watchlist is not null
        if (watchlist != null) {
            MovieArrayAdapter myAdapter = new MovieArrayAdapter(this, watchlist);
            WatchListView.setAdapter(myAdapter);
        }

        WatchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = (Movie)parent.getAdapter().getItem(position);
                System.out.println(movie.toString());
                Intent i = new Intent(MainActivity.this, DisplayInfo.class);
                i.putExtra("movie",movie);
                startActivity(i);
            }
        });
    }


    //transition to Seen Movies screen
    public void onSeenButtonClick(View v)
    {
        Intent myIntent = new Intent(MainActivity.this,SeenList.class);
        myIntent.putExtra("seen_list", seenlist);
        myIntent.putExtra("watch_list", watchlist);
        startActivity(myIntent);
    }

    //transition to AddMovieEntry activity
    public void onAddButtonClick(View v)
    {
        Intent myIntent = new Intent(MainActivity.this,AddMovieEntry.class);
        startActivity(myIntent);
    }

    //opens email with watchlist
    public void onEmailClick(View v){
        String messageList = "";

        //adds each movie title as to messageList, separated by new lines
        if (watchlist != null) {
            for (Movie m : watchlist) {
                messageList += m.getmMovieTitle() + "\n";
            }
            Toast.makeText(this, "Watchlist message created", Toast.LENGTH_SHORT).show();
        }


        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"kupshah@indiana.edu","kunaalshah20@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_TEXT, messageList);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "List of movies I've seen");

        try {

            startActivity(Intent.createChooser(emailIntent,"Send seenlist via:"));
        }

        catch (Exception e) {
            Toast.makeText(this, "Failed sending Email", Toast.LENGTH_SHORT).show();
        }
    }
}
