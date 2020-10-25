
package com.example.android.dailynews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<word>>, NewsAdapter.NewsAdapterOnClickHandler,SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int News_ID = 22;
    private static final String url="http://newsapi.org/v2/top-headlines";
    private RecyclerView mRecycler;
    private TextView mError;
    private ProgressBar mProgress;
    private static boolean PREFERENCES_HAVE_BEEN_UPDATED = false;
    private NewsAdapter mNewsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecycler=(RecyclerView) findViewById(R.id.recycle);
        mError=(TextView)findViewById(R.id.error_message);


        mProgress=(ProgressBar) findViewById(R.id.loading_indicator);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setHasFixedSize(true);
        mNewsAdapter = new NewsAdapter(this);


        mRecycler.setAdapter(mNewsAdapter);
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();


        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getSupportLoaderManager();

            loaderManager.initLoader(News_ID, null, this);
        } else {


            mProgress.setVisibility(View.GONE);
            showError();
        }
        PreferenceManager.getDefaultSharedPreferences(
                this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    public void showData(){
        mError.setVisibility(View.INVISIBLE);

        mRecycler.setVisibility(View.VISIBLE);

    }
    public void showError(){
        mError.setVisibility(View.VISIBLE);
        mRecycler.setVisibility(View.INVISIBLE);

    }
    public Loader<List<word>> onCreateLoader(int id,  Bundle args) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
String cate=sharedPrefs.getString(getString(R.string.category_key),getString(R.string.category_default));


        Uri baseUrl= Uri.parse(url);

        mProgress.setVisibility(View.VISIBLE);
        Uri.Builder uriBuilder = baseUrl.buildUpon();
        uriBuilder.appendQueryParameter("country","in");
        uriBuilder.appendQueryParameter("category",cate);
        uriBuilder.appendQueryParameter("apiKey","6934b2adcaaf4edfb045afed1213f318");


        return new NewsLoader(this, uriBuilder.toString());

    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

            PREFERENCES_HAVE_BEEN_UPDATED = true;

     }


public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.news_menu,menu);
    return true;
    }
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int it=item.getItemId();
        if(it==R.id.action_refresh) {
            onStart();
return  true;
        }

        else if(it==R.id.news_menu){
            Intent intent=new Intent(this,settingActivity.class);
            startActivity(intent);
           return  true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onLoadFinished(@NonNull Loader<List<word>> loader, List<word> data) {
        mProgress.setVisibility(View.GONE);


        mProgress.setVisibility(View.INVISIBLE);
        mNewsAdapter.setNewsData(data);
        if (null == data) {
            showError();
        } else {
            showData();
        }}

    public void onLoaderReset(@NonNull Loader<List<word>> loader) {

    }
    public void onClick(String NewsDay) {
        String url = NewsDay;
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (PREFERENCES_HAVE_BEEN_UPDATED) {
            Log.d(TAG, "onStart: preferences were updated");
            getSupportLoaderManager().restartLoader(News_ID, null, this);
            PREFERENCES_HAVE_BEEN_UPDATED = false;
        }
    }

    protected void onStart() {
        super.onStart();


        if (PREFERENCES_HAVE_BEEN_UPDATED) {
            Log.d(TAG, "onStart: preferences were updated");
            getSupportLoaderManager().restartLoader(News_ID, null, this);
            PREFERENCES_HAVE_BEEN_UPDATED = false;
        }
    }
}
