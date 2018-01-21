package com.example.tristanfreeman.podcastlist;

/**
 * Created by tristanfreeman on 1/20/18.
 */

public class RssItem {
    private String title;
    private String link;
    private String description;
    private String pubDate;
    private String url;
    private String summary;

    public RssItem(){

    }

    public RssItem(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getSummary(){
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    @Override
    public String toString() {
        return "RssItem [title=" + title + "]";
    }
}
