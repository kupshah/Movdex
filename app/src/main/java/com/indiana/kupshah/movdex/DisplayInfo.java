package com.indiana.kupshah.movdex;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

//Displays Movie Information when list item is clicked
public class DisplayInfo extends AppCompatActivity {

    private Movie m;
    private APIReader reader = new APIReader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_info);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

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

        //sets plot as found online
        TextView plot = (TextView) findViewById(R.id.plotText);
        try {
            plot.setText(reader.getPlotFromTitle(m.getmMovieTitle()));
        }
        catch (Exception e) { plot.setText("Movie plot not found");}

        //sets movie cast as found online
        TextView cast = (TextView) findViewById(R.id.castList);
        try {
            cast.setText(reader.getCastFromID(reader.getIDFromTitle(m.getmMovieTitle())));
        }
        catch (Exception e) {
            cast.setText("Cannot find cast for " + m.getmMovieTitle());
            Toast.makeText(this, "Cannot find cast for " + m.getmMovieTitle(), Toast.LENGTH_SHORT).show();
        }

        String imageURL = "";
        try {
            imageURL = reader.getPosterURLFromTitle(m.getmMovieTitle());
            // show The Image in a ImageView
            new DownloadImageTask((ImageView) findViewById(R.id.poster))
                    .execute(imageURL);
        }
        catch (Exception e) {
            Toast.makeText(this, "Cannot find movie poster for " + m.getmMovieTitle(), Toast.LENGTH_SHORT).show();;
        }


    }
    public void onTrailerButtonClick(View v){
        Intent myIntent = new Intent(DisplayInfo.this,MovieTrailer.class);
        myIntent.putExtra("movie",m);
        startActivity(myIntent);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
