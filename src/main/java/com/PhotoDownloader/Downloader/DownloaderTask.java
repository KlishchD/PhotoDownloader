package com.PhotoDownloader.Downloader;

import java.io.IOException;
import java.util.List;

class DownloaderTask implements Runnable {

    private final List<String> links;
    private final String directoryPath;

    public DownloaderTask(List<String> links, String directoryPath) {
        this.links = links;
        this.directoryPath = directoryPath;
    }

    @Override
    public void run() {
        try {
            new Downloader(links, directoryPath).download();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
