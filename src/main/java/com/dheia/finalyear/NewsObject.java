package com.dheia.finalyear;

import java.util.ArrayList;

/**
 * Created by Dheia on 03/04/2019.
 */

public class NewsObject {

    private String title,body,newsUrl,time,site,iconURL ;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public NewsObject(String titles, String body , String newsUrl, String time, String site, String iconURL){
        title = titles;
        this.body = body;
        this.newsUrl = newsUrl;
        this.time = time;
        this.site = site;
        this.iconURL = iconURL;

    }
}
