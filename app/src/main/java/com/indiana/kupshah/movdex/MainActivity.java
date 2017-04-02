package com.indiana.kupshah.movdex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
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
