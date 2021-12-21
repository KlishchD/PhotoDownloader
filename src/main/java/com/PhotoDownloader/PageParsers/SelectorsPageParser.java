package com.PhotoDownloader.PageParsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SelectorsPageParser implements PageParserInterface {
    private final String HOME_PAGE_URL = "https://unsplash.com";
    private final String PHOTO_CSS_SELECTOR = "img[class=\"YVj9w\"][itemprop=\"thumbnailUrl\"]";
    private final String GROUPS_CSS_SELECTOR = "a[class=p7ajO]";

    private int groupsNumber;
    private int photosNumber;

    public SelectorsPageParser(int groupsNumber, int photosNumber) {
        this.groupsNumber = groupsNumber;
        this.photosNumber = photosNumber;
    }

    public SelectorsPageParser() {
    }

    @Override
    public void setGroupsNumber(int groupsNumber) {
        this.groupsNumber = groupsNumber;
    }

    @Override
    public void setPhotosNumber(int photosNumber) {
        this.photosNumber = photosNumber;
    }

    @Override
    public List<List<String>> parse(int groupsNumber, int photosNumber) {
        return new SelectorsPageParser(groupsNumber, photosNumber).parse();
    }

    @Override
    public List<List<String>> parse() {
        return getGroupsLinks().stream().map(this::getPhotosLinks).collect(Collectors.toList());
    }

    private List<String> getPhotosLinks(String link) {
        return getDocumentFromURL(link).select(PHOTO_CSS_SELECTOR).eachAttr("src").stream().limit(photosNumber).collect(Collectors.toList());
    }

    private List<String> getGroupsLinks() {
        return getDocumentFromURL(HOME_PAGE_URL).select(GROUPS_CSS_SELECTOR).eachAttr("href").stream().limit(groupsNumber).map(this::getURL).collect(Collectors.toList());
    }

    private String getURL(String relativeURL) {
        return HOME_PAGE_URL + relativeURL;
    }

    private Document getDocumentFromURL(String URL) {
        Document document = null;
        try {
            document = Jsoup.connect(URL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

}