package com.example.tristanfreeman.podcastlist;

/**
 * Created by tristanfreeman on 1/20/18.
 */

public class RssItem {
    private String title;
    private String pageLink;
    private String description;
    private String pubDate;
    private String audioUrl;
    private String summary;
    private String thumbnailUri;

    public RssItem(){

    }

    public RssItem(String title, String pageLink) {
        this.title = title;
        this.pageLink = pageLink;
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

    public String getPageLink() {
        return pageLink;
    }
    public void setPageLink(String pageLink) {
        this.pageLink = pageLink;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAudioUrl() {
        return audioUrl;
    }
    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getSummary(){
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getThumbnailUri() {
        return thumbnailUri;
    }
    public void setThumbnailUri(String thumbnailUri) {
        this.thumbnailUri = thumbnailUri;
    }

    @Override
    public String toString() {
        return "RssItem [title=" + title + "]";
    }
}
