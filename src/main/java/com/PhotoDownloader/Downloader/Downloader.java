package com.PhotoDownloader.Downloader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Downloader {
    private final int BUFFER_SIZE_FOR_FILE_COPYING_SIZE = 1024;
    private List<String> photoLinks = new ArrayList<>();
    private String directoryPath;

    public Downloader() {
    }

    public Downloader(List<String> photoLinks, String directoryPath) {
        this.photoLinks = photoLinks;
        this.directoryPath = directoryPath;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public List<String> getPhotoLinks() {
        return photoLinks;
    }

    public void setPhotoLinks(List<String> photoLinks) {
        this.photoLinks = photoLinks;
    }

    public void download() throws IOException {
        for (String link : photoLinks) {
            downloadFromLink(link);
        }
    }

    private void downloadFromLink(String link) throws IOException {
        File file = new File(directoryPath + "/" + getName(link) + ".png");

        file.createNewFile();

        copyDataFromURLToFile(link, file);
    }

    private void copyDataFromURLToFile(String URL, File file) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file); BufferedInputStream inputStream = new BufferedInputStream(new URL(URL).openStream())) {
            int bytesRead;
            byte[] buffer = new byte[BUFFER_SIZE_FOR_FILE_COPYING_SIZE];
            while ((bytesRead = inputStream.read(buffer, 0, BUFFER_SIZE_FOR_FILE_COPYING_SIZE)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getName(String link) {
        Pattern pattern = Pattern.compile(".*/([\\w=\\-?&.;]*)$");
        Matcher matcher = pattern.matcher(link);
        matcher.find();
        return matcher.group(1) + matcher.hashCode();
    }

}
