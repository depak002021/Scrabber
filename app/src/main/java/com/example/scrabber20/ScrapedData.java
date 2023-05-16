package com.example.scrabber20;

import java.io.Serializable;

//import android.graphics.Bitmap;
public class ScrapedData implements Serializable {
    private String htmlContent;
    private String scrapedTitle;
    private String scrapedDescription;
    private String scrapedLinks;
    private String screenshotImageUrl;
//    private String seoScore;

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getScrapedTitle() {
        return scrapedTitle;
    }

    public void setScrapedTitle(String scrapedTitle) {
        this.scrapedTitle = scrapedTitle;
    }

    public String getScrapedDescription() {
        return scrapedDescription;
    }

    public void setScrapedDescription(String scrapedDescription) {
        this.scrapedDescription = scrapedDescription;
    }

    public String getScrapedLinks() {
        return scrapedLinks;
    }

    public void setScrapedLinks(String scrapedLinks) {
        this.scrapedLinks = scrapedLinks;
    }

    public String getScreenshotImageUrl() {
        return screenshotImageUrl;
    }

    public void setScreenshotImageUrl(String screenshotImageUrl) {
        this.screenshotImageUrl = screenshotImageUrl;
    }

//    public String getSeoScore() {
//        return seoScore;
//    }
//
//    public void setSeoScore(String seoScore) {
//        this.seoScore = seoScore;
//    }
}



