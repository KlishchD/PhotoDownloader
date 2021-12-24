package com.PhotoDownloader.PageParsers;

import java.io.IOException;
import java.util.List;

public interface PageParserInterface {

    void setGroupsNumber(int groupsNumber);

    void setPhotosNumber(int photosNumber);

    List<List<String>> parse(int groupsNumber, int photosNumber) throws IOException;

    List<List<String>> parse() throws IOException;

}
