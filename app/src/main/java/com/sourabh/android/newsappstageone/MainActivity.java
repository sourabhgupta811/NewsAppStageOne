package com.sourabh.android.newsappstageone;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sourabh.android.newsappstageone.adapter.NewsListAdapter;
import com.sourabh.android.newsappstageone.loaders.NewsLoader;
import com.sourabh.android.newsappstageone.modal.NewsItemModal;
import com.sourabh.android.newsappstageone.rest.ApiClient;
import com.sourabh.android.newsappstageone.utils.JSONUtils;

import org.json.JSONException;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ApiClient mApiClient;
    private RecyclerView newsRecyclerView;
    private static final int LOADER_ID = 101;
    private Loader<String> newsLoader;
    private int pageNumber = 1;
    private ProgressBar progressBar;
    private View errorLayout;
    private View noDataLayout;
    private Toast errorToast;
    private NewsListAdapter mNewsListAdapter;
    private View.OnClickListener retryButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                progressBar.setVisibility(View.VISIBLE);
                errorLayout.setVisibility(View.GONE);
                newsRecyclerView.setVisibility(View.GONE);
                fetchNews();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    final LoaderManager.LoaderCallbacks<String> mLoaderCallbacks = new LoaderManager.LoaderCallbacks<String>() {
        @NonNull
        @Override
        public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
            return new NewsLoader(MainActivity.this, mApiClient, null, pageNumber);
        }

        @Override
        public void onLoadFinished(@NonNull Loader<String> loader, String rawData) {
            List<NewsItemModal> newsList;
            try {
                newsList = JSONUtils.convertRawDataToNewsList(rawData);
                if (mNewsListAdapter == null && newsList.size()>0) {
                    progressBar.setVisibility(View.GONE);
                    errorLayout.setVisibility(View.GONE);
                    mNewsListAdapter = new NewsListAdapter(MainActivity.this, newsList);
                    newsRecyclerView.setAdapter(mNewsListAdapter);
                    newsRecyclerView.setVisibility(View.VISIBLE);
                }
                else if(newsList.size()==0){
                    noDataLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    newsRecyclerView.setVisibility(View.GONE);
                }
                else {
                    mNewsListAdapter.addNewsItemsToList(newsList);
                    errorLayout.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    newsRecyclerView.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader loader) {
            loader.stopLoading();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        errorLayout = findViewById(R.id.errorLayout);
        noDataLayout = findViewById(R.id.noDataLayout);
        noDataLayout.setVisibility(View.GONE);
        progressBar = findViewById(R.id.progressBar);
        errorLayout.setVisibility(View.GONE);
        Button retryButton = findViewById(R.id.retry);
        newsRecyclerView = findViewById(R.id.recyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsRecyclerView.setVisibility(View.GONE);
        newsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    pageNumber++;
                    progressBar.setVisibility(View.VISIBLE);
                    fetchNews();
                }
            }
        });
        mApiClient = ApiClient.getInstance();
        retryButton.setOnClickListener(retryButtonClickListener);
        retryButton.performClick();
    }

    private void fetchNews() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo currentNetworkInfo;
        boolean connected = false;
        if (connectivityManager != null) {
            currentNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (currentNetworkInfo != null)
                connected = currentNetworkInfo.isConnected();
        }
        if (connected) {
            LoaderManager loaderManager = getSupportLoaderManager();
            if (newsLoader == null) {
                newsLoader = loaderManager.initLoader(LOADER_ID, null, mLoaderCallbacks);
                newsLoader.forceLoad();
            } else
                getSupportLoaderManager().restartLoader(LOADER_ID, null, mLoaderCallbacks).forceLoad();
        } else if (newsRecyclerView.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
            if(errorToast!=null)
                errorToast.cancel();
            errorToast = Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT);
            errorToast.show();
        } else {
            progressBar.setVisibility(View.GONE);
            newsRecyclerView.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }
    }
}
