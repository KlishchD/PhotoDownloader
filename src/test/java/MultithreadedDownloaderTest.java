import com.PhotoDownloader.Downloader.MultithreadedDownloader;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

public class MultithreadedDownloaderTest {
    @Before
    public void cleaner() {
        String path = "/Users/dklishch/Documents/IntelliJ Idea Projects/PhotoDownloader/src/test/resources/MultithreadedDownloaderTest";
        for (File file : Objects.requireNonNull(new File(path).listFiles())) {
            file.delete();
        }
    }

    @Test
    public void download_RightData_DownloadsFiles() throws InterruptedException, IOException {
        String path = "/Users/dklishch/Documents/IntelliJ Idea Projects/PhotoDownloader/src/test/resources/MultithreadedDownloaderTest";
        List<List<String>> links = Arrays.asList(Arrays.asList("https://images.unsplash.com/photo-1639610834110-0de6043492f0?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDF8ZWRRT21paGVVdFl8fGVufDB8fHx8&w=1000&q=80"), Arrays.asList("https://images.unsplash.com/photo-1639610834110-0de6043492f0?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDF8ZWRRT21paGVVdFl8fGVufDB8fHx8&w=1000&q=80"));
        MultithreadedDownloader.download(links, path);
        Thread.sleep(2000);
        assertEquals(2, Objects.requireNonNull(new File(path).list()).length);
    }

}
