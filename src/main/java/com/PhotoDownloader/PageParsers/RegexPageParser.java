package com.PhotoDownloader.PageParsers;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RegexPageParser implements PageParserInterface {
    private final String HOME_PAGE_URL = "https://unsplash.com";
    private final Pattern PHOTO_URL_PATTERN = Pattern.compile("(?m)^\\s*<img\\sclass=\"YVj9w\"\\s.*\\ssrc=\"(.*)\"\\si.*>$");
    private final Pattern GROUPS_RELATIVE_URL_PATTERN = Pattern.compile("(?m).*<a\\sclass=\"p7ajO\"\\shref=\"(\\/[^\"]+)\".*>.*$");
    private int groupsNumber;
    private int photosNumber;

    public RegexPageParser() {
    }

    public RegexPageParser(int groupsNumber, int photosNumber) {
        this.groupsNumber = groupsNumber;
        this.photosNumber = photosNumber;
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
    public List<List<String>> parse(int groupsNumber, int photosNumber) throws IOException {
        return new RegexPageParser(groupsNumber, photosNumber).parse();
    }

    @Override
    public List<List<String>> parse() {
        return getPhotosLinksFromGroups(getGroupsLinks());
    }

    private List<String> getGroupsLinks() {
        return getRelativeGroupsURLs().stream().map(this::getURL).collect(Collectors.toList());
    }

    private List<String> getRelativeGroupsURLs() {
        return findPatternMatchingLinesInString(GROUPS_RELATIVE_URL_PATTERN, getHTMLFromPage(HOME_PAGE_URL), groupsNumber);
    }

    private List<List<String>> getPhotosLinksFromGroups(List<String> groupsLinks) {
        return groupsLinks.stream().map(this::getPhotosLinksFromGroup).collect(Collectors.toList());
    }

    private List<String> getPhotosLinksFromGroup(String link) {
        return findPatternMatchingLinesInString(PHOTO_URL_PATTERN, getHTMLFromPage(link), photosNumber)
                .stream().map(this::fixURL).collect(Collectors.toList());
    }

    private List<String> findPatternMatchingLinesInString(Pattern pattern, String string, int timesToMatch) {
        List<String> result = new ArrayList<>();

        Matcher matcher = pattern.matcher(string);
        for (int i = 0; i < timesToMatch && matcher.find(); ++i) {
            result.add(matcher.group(1));
        }

        return result;
    }

    private String getHTMLFromPage(String pageURL) {
        try {
            return Jsoup.connect(pageURL).get().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getURL(String relativePath) {
        return HOME_PAGE_URL + relativePath;
    }

    private String fixURL(String strings) {
        return strings.replace("amp;", "");
    }

}