package com.indiana.kupshah.movdex;

/**
 * Created by Kunaal on 4/1/2017.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kunaal on 3/30/2017.
 */

public class MovieArrayAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Movie> movieList;

    //adapter constructor
    public MovieArrayAdapter(Context context, ArrayList<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    //abstract method returns size of movie list
    public int getCount() {
            return this.movieList.size();
    }

    //abstract method returns item at input position in list
    public Object getItem(int pos){return this.movieList.get(pos);}

    // abstract method gets item id at position i
    public long getItemId(int pos){return pos;}

    public View getView(int position, View convertView, ViewGroup parent){

        View v = View.inflate(context, R.layout.movie_list_entry, null);

        //finds MovieTitle textview field in the movie_list entry layout file
        TextView title = (TextView)v.findViewById(R.id.MovieTitle);

        //sets MovieTitle field to title of movie from list
        title.setText(movieList.get(position).getmMovieTitle());

        return v;}
}

