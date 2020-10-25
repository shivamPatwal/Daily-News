package com.example.android.dailynews;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public static List<word> fetchEarthData(String mUrl) {
        URL url=createUrl(mUrl);


        String JsonResponses=null;
        try{
            JsonResponses= makeHttpRequest(url);
        }catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<word> earthquakes = extractFeatureFromJson(JsonResponses);

        // Return the list of {@link Earthquake}s
        return earthquakes;
    }

    private static List<word> extractFeatureFromJson(String jsonResponses) {
        NewsAdapter mnewsadapter = null;
        List<word> news= new ArrayList<>();
        try {
            JSONObject root= new JSONObject(jsonResponses);
            JSONArray array=root.getJSONArray("articles");

            for(int i=0;i<array.length();i++){
                JSONObject current = array.getJSONObject(i);
                String title=current.getString("title");
                String descriptiom=current.getString("description");
                String url=current.getString("url");
                String urlToImage=current.getString("urlToImage");
                 word News =new word(urlToImage,url,descriptiom,title);
               news.add(News);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return news;

    }

    private static String makeHttpRequest(URL url) throws IOException {
        String JsonResponses=null;
        if(url==null) return JsonResponses;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{  urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                JsonResponses = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return JsonResponses;
    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createUrl(String requestUrl) {
        URL url=null;
        try{
            url= new URL(requestUrl);

        }catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;

    }

}
