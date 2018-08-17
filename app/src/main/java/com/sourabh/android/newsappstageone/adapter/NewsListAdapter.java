package com.sourabh.android.newsappstageone.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.sourabh.android.newsappstageone.R;
import com.sourabh.android.newsappstageone.modal.NewsItemModal;

import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    private List<NewsItemModal> mNewsList;
    private Context mContext;

    public NewsListAdapter(Context context, List<NewsItemModal> newsList) {
        mContext = context;
        mNewsList = newsList;
    }

    public void addNewsItemsToList(List<NewsItemModal> newsList) {
        mNewsList.addAll(newsList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new ViewHolder(inflater.inflate(R.layout.news_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(mNewsList.get(i));
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView webTitleTextView;
        TextView webPublicationTextView;
        TextView sectionNameTextView;
        TextView authorTextView;
        ImageView newsItemLayout;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            webPublicationTextView = itemView.findViewById(R.id.webPublicationDate);
            webTitleTextView = itemView.findViewById(R.id.webTitle);
            sectionNameTextView = itemView.findViewById(R.id.sectionName);
            authorTextView = itemView.findViewById(R.id.writer);
            newsItemLayout = itemView.findViewById(R.id.newsItemLayout);
        }

        void bindData(final NewsItemModal newsItem) {
            webTitleTextView.setText(newsItem.getWebTitle());
            webPublicationTextView.setText(newsItem.getWebPublicationDate());
            sectionNameTextView.setText(newsItem.getSectionName());
            authorTextView.setText(newsItem.getAuthor());
            Glide.with(mContext).load(newsItem.getThumbnail()).into(newsItemLayout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri address = Uri.parse(newsItem.getWebUrl());
                    intent.setData(address);
                    intent = Intent.createChooser(intent, "Open With");
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
