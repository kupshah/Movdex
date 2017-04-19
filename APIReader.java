
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.*;

/**
 * Created by Kunaal on 4/18/2017.
 */

public class APIReader {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

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

    public String getIDFromTitle(String title)  throws IOException, JSONException {
    	String urlTitle = title.replace(" ", "+");
        JSONObject json = readJsonFromUrl("http://api.themoviedb.org/3/search/movie?api_key=07cd671663902a29a58a82640635b99b&query=" + urlTitle);
        JSONArray results = (JSONArray)json.get("results");   
        JSONObject first = (JSONObject)results.get(0);
        String id = first.get("id").toString();
        return id;
    }
    
    public String getTrailerFromID(String id) throws IOException, JSONException{
    	
    	JSONObject json = readJsonFromUrl("http://api.themoviedb.org/3/movie/" + id + "/videos?api_key=07cd671663902a29a58a82640635b99b");
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
    	
    	return trailerKey;
    	
    }
    
    public String getPosterURLFromTitle(String title)  throws IOException, JSONException {
    	String urlTitle = title.replace(" ", "+");
        JSONObject json = readJsonFromUrl("http://api.themoviedb.org/3/search/movie?api_key=07cd671663902a29a58a82640635b99b&query=" + urlTitle);
        JSONArray results = (JSONArray)json.get("results");   
        JSONObject first = (JSONObject)results.get(0);
        String path = first.get("poster_path").toString();
        return "http://image.tmdb.org/t/p/w150" + path;
    }


    public static void main(String[] args) throws IOException, JSONException {
    	APIReader api = new APIReader();
    	String id = api.getIDFromTitle("The Dark Knight");
    	System.out.println(api.getTrailerFromID(id));
    	}
    }

