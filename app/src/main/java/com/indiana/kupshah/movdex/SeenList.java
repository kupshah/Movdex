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

public class SeenList extends AppCompatActivity {

    private DBHandler movdexDB = new DBHandler(this);
    private ArrayList<Movie> seenList;
    private ArrayList<Movie> watchlist;

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

        watchlist = (ArrayList<Movie>)i.getSerializableExtra("watch_list");

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
        myIntent.putExtra("watch_list", watchlist);
        myIntent.putExtra("seen_list", seenList);
        startActivity(myIntent);
    }

    //transition to AddMovieEntry activity where user can add a movie
    public void onAddButtonClick(View v){
        Intent myIntent = new Intent(SeenList.this,AddMovieEntry.class);
        startActivity(myIntent);
    }

    //opens email with Seenlist
    public void onEmailClick(View v){
        String messageList = "";

        //adds each movie title as to messageList, separated by new lines
        for (Movie m : seenList){
            messageList += m.getmMovieTitle() + "\n";
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
