package com.indiana.kupshah.movdex;

/**
 * Created by Kunaal on 4/1/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Kunaal on 3/7/2017.
 */

public class DBHandler extends SQLiteOpenHelper implements Serializable{
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "MovieDB";
    // Contacts table name
    private static final String MOVIE_TABLE = "Movies";
    // Shops Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "movie_title";
    private static final String KEY_RECOMMEND = "rec";
    private static final String KEY_DISCOVER = "discover";
    private static final String KEY_NOTES = "notes";
    private static final String KEY_SEEN = "seen";
    private static final String KEY_WATCHLIST = "watchlist";
    private static final long serialVersionUID = 1L;


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOVIE_TABLE = "CREATE TABLE " + MOVIE_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_RECOMMEND + " TEXT," + KEY_DISCOVER + " TEXT," + KEY_NOTES +
                " TEXT," + KEY_SEEN + " BOOLEAN, " + KEY_WATCHLIST + " BOOLEAN " + ")";
        db.execSQL(CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + MOVIE_TABLE);
        // Creating tables again
        onCreate(db);
    }

    // Adds movie record to DB
    public void addMovie(Movie m) {
        SQLiteDatabase db = this.getWritableDatabase();


        //check to see if movie is being added to db
        ContentValues values = new ContentValues(); //object holds values to be added to db
        values.put(KEY_TITLE, m.getmMovieTitle()); // adds movie title
        values.put(KEY_RECOMMEND, m.getmRecommend()); // adds movie recommendation notes
        values.put(KEY_DISCOVER, m.getmDiscover()); // adds discovery notes
        values.put(KEY_NOTES,m.getmNotes()); //adds movie notes
        if (m.ismSeen()) {
            values.put(KEY_SEEN, 1); //adds if movie was seen or not
        }
        else {values.put(KEY_SEEN,0);}

        //adds if movie is to be added to watchlist or not
        if (m.ismAddToWatchlist()) {
            values.put(KEY_WATCHLIST, 1);
        }
        else {values.put(KEY_WATCHLIST,0);}
        System.out.println(values.toString());

        // Inserting Row
        db.insert(MOVIE_TABLE, null, values);
        db.close(); // Closes connection to database
    }

    // Gets all movies to be added to Watch list
    public ArrayList<Movie> getWatchlistMovies() {
        ArrayList<Movie> watchList = new ArrayList<Movie>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + MOVIE_TABLE + " WHERE " + KEY_WATCHLIST + ";" ;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Movie m = new Movie();
                m.setMovieTitle(cursor.getString(1));
                m.setRecommend(cursor.getString(2));
                m.setDiscover(cursor.getString(3));
                m.setNotes(cursor.getString(4));
                m.setmSeen(true); //since movies in this query are definitely seen

                m.setmAddToWatchlist(Integer.parseInt(cursor.getString(5)) == 1);//sets seen to bool value of seen field

                // Adding a movie to seen list
                watchList.add(m);
            }
            while (cursor.moveToNext());
        }
        // return movie list
        return watchList;
    }

    // Gets all movies to be added to seen list
    public ArrayList<Movie> getSeenMovies(){
        ArrayList<Movie> seenList = new ArrayList<Movie>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + MOVIE_TABLE + " WHERE " + KEY_SEEN +";" ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Movie m = new Movie();
                m.setMovieTitle(cursor.getString(1));
                m.setRecommend(cursor.getString(2));
                m.setDiscover(cursor.getString(3));
                m.setNotes(cursor.getString(4));

                m.setmSeen(true); //since the select query checks for movies where this field is true

                // new object should also be true
                m.setmAddToWatchlist(Integer.parseInt(cursor.getString(6)) == 1);//sets to boolean value of the wathclist field

                seenList.add(m);

            }
            while (cursor.moveToNext());
        }

        return seenList;
    }

    // Updating a movie's information
    public int updateMovie(Movie m) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, m.getmMovieTitle()); // updates movie title
        values.put(KEY_RECOMMEND, m.getmRecommend()); // updates movie recommendation notes
        values.put(KEY_DISCOVER, m.getmDiscover()); // updates discovery notes
        values.put(KEY_NOTES,m.getmNotes()); //updates movie notes
        if (m.ismSeen()) {
            values.put(KEY_SEEN, 1); //adds if movie was seen or not
        }
        else {values.put(KEY_SEEN,0);}

        //adds if movie is to be added to watchlist or not
        if (m.ismAddToWatchlist()) {
            values.put(KEY_WATCHLIST, 1);
        }
        else {values.put(KEY_WATCHLIST,0);}

        // updating row
        return db.update(MOVIE_TABLE, values, KEY_ID + " = ?",
                new String[]{String.valueOf(m.getmMovieTitle())});
    }

    // Deletes a movie record from db
    public void deleteMovie(Movie m) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MOVIE_TABLE, KEY_TITLE + " = ?",
                new String[] {String.valueOf(m.getmMovieTitle())});
        db.close();
    }
}
