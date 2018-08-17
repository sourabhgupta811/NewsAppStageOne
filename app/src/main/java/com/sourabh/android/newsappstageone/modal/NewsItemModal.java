package com.sourabh.android.newsappstageone.modal;

public class NewsItemModal {
    private String sectionName;
    private String webPublicationDate;
    private String webTitle;
    private String webUrl;
    private String thumbnail;
    private String author;
    public NewsItemModal(String sectionName, String webPublicationDate, String webTitle, String webUrl,String thumbnail, String author) {
        super();
        this.sectionName = sectionName;
        this.webPublicationDate = webPublicationDate;
        this.webTitle = webTitle;
        this.webUrl = webUrl;
        this.thumbnail = thumbnail;
        this.author = author;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getAuthor() {
        return author;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getWebPublicationDate() {
        return webPublicationDate.substring(0, 10);
    }

    public String getWebTitle() {
        return webTitle;
    }


    public String getWebUrl() {
        return webUrl;
    }

    @Override
    public String toString() {
        return sectionName + webPublicationDate + webTitle + webUrl;
    }
}
