package com.sourabh.android.newsappstageone.loaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.sourabh.android.newsappstageone.rest.ApiClient;

public class NewsLoader extends AsyncTaskLoader<String> {
    private ApiClient mApiClient;
    private String query;
    private int pageNumber;

    public NewsLoader(@NonNull Context context, ApiClient apiClient, String query, int pageNumber) {
        super(context);
        mApiClient = apiClient;
        this.query = query;
        this.pageNumber = pageNumber;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
    }

    @Override
    public void deliverResult(@Nullable String data) {
        super.deliverResult(data);
    }

    @Nullable
    @Override
    public String loadInBackground() {
        try {
            return mApiClient.fetchData(query, pageNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }
}
