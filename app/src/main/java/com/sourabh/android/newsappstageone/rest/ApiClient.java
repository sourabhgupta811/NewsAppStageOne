package com.sourabh.android.newsappstageone.rest;


import com.sourabh.android.newsappstageone.constants.AppConstant;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiClient {
    private static final ApiClient sApiClient = new ApiClient();
    private static final String API_URL = AppConstant.BASE_URL;

    private ApiClient() {
    }

    public static ApiClient getInstance() {
        return sApiClient;
    }

    public String fetchData(String query, int pageNumber) throws Exception {
        URL pageUrl;
        if (query == null)
            pageUrl = new URL(API_URL + "?" + AppConstant.PAGE_NUMBER_PARAMETER + "=" + pageNumber + "&show-tags=contributor&show-fields=thumbnail&" + AppConstant.API_KEY_QUERY_PARAMETER + "=" + AppConstant.API_KEY);
        else
            pageUrl = new URL(API_URL + "?" + AppConstant.SEARCH_QUERY_PARAMETER + "=" + query + "&show-tags=contributor&show-fields=thumbnail&" + AppConstant.PAGE_NUMBER_PARAMETER + "=" + pageNumber + "&" + AppConstant.API_KEY_QUERY_PARAMETER + "=" + AppConstant.API_KEY);
        HttpURLConnection connection = (HttpURLConnection) pageUrl.openConnection();
        InputStream stream = connection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(stream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String tempData;
        StringBuilder stringBuilder = new StringBuilder();
        while ((tempData = bufferedReader.readLine()) != null) {
            stringBuilder.append(tempData);
        }
        connection.disconnect();
        bufferedReader.close();
        inputStreamReader.close();
        stream.close();
        return stringBuilder.toString();
    }
}
