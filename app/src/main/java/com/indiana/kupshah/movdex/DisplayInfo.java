package com.indiana.kupshah.movdex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

//Displays Movie Information when list item is clicked
public class DisplayInfo extends AppCompatActivity {

    private Movie m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_info);
        Intent i = getIntent();

        //receives movie object to populate textviews
        m = (Movie) i.getSerializableExtra("movie");

        //sets Text boxes to values as per the Movie object
        TextView title = (TextView) findViewById(R.id.movieTitle);
        title.setText(m.getmMovieTitle());
        TextView rec = (TextView) findViewById(R.id.Rec);
        rec.setText(m.getmRecommend());
        TextView disc = (TextView) findViewById(R.id.discoveredAt);
        disc.setText(m.getmDiscover());
        TextView note = (TextView) findViewById(R.id.additionalNotes);
        note.setText(m.getmNotes());
    }
    public void onTrailerButtonClick(View v){
        Intent myIntent = new Intent(DisplayInfo.this,MovieTrailer.class);
        myIntent.putExtra("movie",m);
        startActivity(myIntent);
    }
}
