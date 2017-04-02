package com.indiana.kupshah.movdex;

/**
 * Created by Kunaal on 4/1/2017.
 */
import java.io.Serializable;

/**
 * Created by Kunaal on 2/17/2017.
 */

public class Movie implements Serializable{
    private String movieTitle; //String Movie's title
    private String recommend; //User enters who reccomended movie
    private String discover; //User enters where they discvered the movie
    private String notes; //User enters any additional notes they have for the movie
    private boolean seen; //true if user has seen movie
    private boolean addToWatchlist; //if user wants to add movie to watchlist

    //Movie constructor
    //Add Movie to Movie array in watched list and seen list
    public Movie(String mt, String r, String d, String n, boolean s, boolean add)
    {
        this.movieTitle = mt;
        this.recommend = r;
        this.discover = d;
        this.notes = n;
        this.seen = s;
        this.addToWatchlist = add;

    }

    public Movie()
    {
        movieTitle = "default";
    }

    //Returns Movie's Title
    public String getmMovieTitle() {return movieTitle;}

    //Sets movie's title
    public void setMovieTitle(String MovieTitle) {this.movieTitle = MovieTitle;}

    //Sets who recommended movie
    public void setRecommend(String rec) {this.recommend = rec;}

    //Returns who recommended movie
    public String getmRecommend() {return this.recommend;}

    //Sets where movie was discovered
    public void setDiscover(String disc) {this.discover = disc;}

    //Returns where movie was discovered
    public String getmDiscover() {return this.discover;}

    //Sets user's notes about a movie
    public void setNotes(String notes) {this.notes = notes;}

    //Returns user's notes on about a movie
    public String getmNotes() {return notes;}

    //Returns true if movie has sbeen seen, false if it has not
    public boolean ismSeen() {return seen;}

    //set whether or not a movie has been seen
    public void setmSeen(boolean s) {this.seen = s;}

    //Returns true if movie is to be on watchlist, false if it is not
    public boolean ismAddToWatchlist() {return addToWatchlist;}

    //set whether or not a movie should appear on watchlist
    public void setmAddToWatchlist(boolean toWatch) {addToWatchlist = toWatch;}

    public String toString(){
        return this.movieTitle + this.discover + this.recommend + this.notes;
    }
}