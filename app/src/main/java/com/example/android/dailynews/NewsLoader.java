
package com.example.android.dailynews;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<word>> {
    private String murl;
    public NewsLoader(Context context, String url) {
        super(context);
        murl=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    public List<word> loadInBackground() {
        if (murl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<word> earthquakes = QueryUtils.fetchEarthData(murl);
        return earthquakes;
    }
}

