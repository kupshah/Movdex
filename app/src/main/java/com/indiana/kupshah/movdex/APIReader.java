package com.indiana.kupshah.movdex;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Kunaal on 4/18/2017.
 */

//All of this works :-)
//reads data from TMDB API queries
//has methods for getting movie id, youtube trailer path, image path, and plot description
public class APIReader {

    private String api = TmdbAPI.getAPI();

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    //opens and converts url text to JSONObject
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    //returns movie'd TMDB id when given title
    public String getIDFromTitle(String title)  throws IOException, JSONException {
        String urlTitle = title.replace(" ", "+");
        JSONObject json = readJsonFromUrl("http://api.themoviedb.org/3/search/movie?api_key=" + api +"&query=" + urlTitle);
        JSONArray results = (JSONArray)json.get("results");
        JSONObject first = (JSONObject)results.get(0);
        String id = first.get("id").toString();
        System.out.println(id);
        return id;
    }

    //returns url for movie's poster
    public String getPosterURLFromTitle(String title)  throws IOException, JSONException {
        String urlTitle = title.replace(" ", "+");
        JSONObject json = readJsonFromUrl("http://api.themoviedb.org/3/search/movie?api_key=" + api +"&query=" + urlTitle);
        JSONArray results = (JSONArray)json.get("results");
        JSONObject first = (JSONObject)results.get(0);
        String path = first.get("poster_path").toString();
        return "http://image.tmdb.org/t/p/w300" + path;
    }

    //returns movie's plot
    public String getPlotFromTitle(String title)  throws IOException, JSONException {
        String urlTitle = title.replace(" ", "+");
        JSONObject json = readJsonFromUrl("http://api.themoviedb.org/3/search/movie?api_key=" + api +"&query=" + urlTitle);
        JSONArray results = (JSONArray)json.get("results");
        JSONObject first = (JSONObject)results.get(0);
        String plot = first.get("overview").toString();
        return plot;
    }

    //uses TMDB id to find movie's youtube trailer path
    public String getTrailerFromID(String id) throws IOException, JSONException{

        JSONObject json = readJsonFromUrl("http://api.themoviedb.org/3/movie/" + id + "/videos?api_key=" + api);
        JSONArray results = (JSONArray)json.get("results");
        boolean isTrailer = false;
        int resultIndex = 0;
        String trailerKey = "";
        while (isTrailer == false && resultIndex < results.length()){
            JSONObject result = (JSONObject)results.get(resultIndex);
            if (result.get("type").toString().equals("Trailer")){
                trailerKey = result.get("key").toString();
                isTrailer = true;
            }
            else {resultIndex += 1;}
        }
        System.out.println(trailerKey);
        return trailerKey;
    }


    //uses TMDB id to find movie's top 5 cast
    public String getCastFromID(String id) throws IOException, JSONException{

        JSONObject json = readJsonFromUrl("http://api.themoviedb.org/3/movie/" + id + "/casts?api_key=" + api);
        JSONArray results = (JSONArray)json.get("cast");
        String castString = "";

        if (results.length() >= 5){
            for (int i = 0; i < 5; i++)
            {
                JSONObject result = (JSONObject)results.get(i);
                castString += result.get("name").toString() + " | ";
                castString += result.get("character").toString() + "\n";
            }
        }

        else {
            for (int i = 0; i < results.length(); i++){
                JSONObject result = (JSONObject)results.get(i);
                castString += result.get("name").toString() + " | ";
                castString += result.get("character").toString() + "\n";
            }
        }
        //while (isTrailer == false && resultIndex < results.length()){
            //JSONObject result = (JSONObject)results.get(resultIndex);
            //if (result.get("type").toString().equals("Trailer")){
                //castString = result.get("key").toString();
                //isTrailer = true;
            //}
            //else {resultIndex += 1;}
        //}
        System.out.println(castString);
        return castString;
    }
}

