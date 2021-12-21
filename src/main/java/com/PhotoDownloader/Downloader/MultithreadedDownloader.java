package com.PhotoDownloader.Downloader;

import java.util.List;

public class MultithreadedDownloader {

    private MultithreadedDownloader() {
    }

    public static void download(List<List<String>> links, String directoryPath) {
        for (List<String> groupLinks : links) {
            new Thread(new DownloaderTask(groupLinks, directoryPath)).start();
        }
    }
}