package com.sourabh.android.newsappstageone.utils;

import android.util.Log;

import com.sourabh.android.newsappstageone.modal.NewsItemModal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONUtils {
    public static List<NewsItemModal> convertRawDataToNewsList(String rawData) throws JSONException {
        final List<NewsItemModal> list = new ArrayList<>();
        JSONObject data = new JSONObject(rawData);
        JSONObject response = data.getJSONObject("response");
        JSONArray rawNewsArray = response.getJSONArray("results");
        Log.e("rawNewsArray", rawNewsArray.toString());
        for (int i = 0; i < rawNewsArray.length(); i++) {
            JSONObject newsItem = rawNewsArray.getJSONObject(i);
            final String sectionName = newsItem.getString("sectionName");
            final String webPublicationDate = newsItem.getString("webPublicationDate");
            final String webTitle = newsItem.getString("webTitle");
            final String webUrl = newsItem.getString("webUrl");
            final JSONObject fields = newsItem.getJSONObject("fields");
            final String thumbnail = fields.getString("thumbnail");
            final JSONArray tags = newsItem.getJSONArray("tags");
            String author = "";
            if(tags!=null && tags.length()>0){
                for(int j=0;j<tags.length();j++){
                    final JSONObject authorObject = tags.getJSONObject(j);
                    author = author  + authorObject.getString("webTitle")+"\n";
                }
            }
            NewsItemModal newsItemModal;
            if(!author.isEmpty())
                newsItemModal = new NewsItemModal(sectionName, webPublicationDate, webTitle, webUrl,thumbnail,author);
            else
                newsItemModal = new NewsItemModal(sectionName, webPublicationDate, webTitle, webUrl,thumbnail,"Unknown Sources");
            list.add(newsItemModal);
        }
        return list;
    }
}
