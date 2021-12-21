import com.PhotoDownloader.Downloader.Downloader;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

public class DownloaderTest {
    @Before
    public void cleanUp() {
        String path = "/Users/dklishch/Documents/IntelliJ Idea Projects/PhotoDownloader/src/test/resources/downloader_download_test";
        for (File i : Objects.requireNonNull(new File(path).listFiles())) {
            i.delete();
        }
    }

    @Test
    public void downloadFromLink_RightLinkAndDirectory() throws Exception {
        String path = "/Users/dklishch/Documents/IntelliJ Idea Projects/PhotoDownloader/src/test/resources/downloader_download_test";
        List<String> links = Arrays.asList("https://images.unsplash.com/photo-1639610834110-0de6043492f0?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDF8ZWRRT21paGVVdFl8fGVufDB8fHx8&w=1000&q=80");
        Downloader downloader = new Downloader(links, path);
        Whitebox.invokeMethod(downloader, "downloadFromLink", links.get(0));
        assertEquals(1, Objects.requireNonNull(new File(path).listFiles()).length);
    }

    @Test(expected = IOException.class)
    public void downloadFromLink_WrongDirectory_ThrowsIOException() throws Exception {
        String path = "/Users/dklishch/Documents/IntelliJ Idea Projects/PhotoDownloader/src/test/resources/downloader_download_asdafasdaadasad";
        List<String> links = Arrays.asList("https://images.unsplash.com/photo-1639610834110-0de6043492f0?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDF8ZWRRT21paGVVdFl8fGVufDB8fHx8&w=1000&q=80");
        Downloader downloader = new Downloader(links, path);
        Whitebox.invokeMethod(downloader, "downloadFromLink", links.get(0));
    }

    @Test(expected = IllegalStateException.class)
    public void downloadFromLink_WrongLink_ThrowsIllegalStateException() throws Exception {
        String path = "/Users/dklishch/Documents/IntelliJ Idea Projects/PhotoDownloader/src/test/resources/downloader_download_test";
        List<String> links = Arrays.asList("sdq=80");
        Downloader downloader = new Downloader(links, path);
        Whitebox.invokeMethod(downloader, "downloadFromLink", links.get(0));
    }

    @Test
    public void download_RightLinkAndDirectory() throws IOException {
        String path = "/Users/dklishch/Documents/IntelliJ Idea Projects/PhotoDownloader/src/test/resources/downloader_download_test";
        Downloader downloader = new Downloader(Arrays.asList("https://images.unsplash.com/photo-1639610834110-0de6043492f0?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDF8ZWRRT21paGVVdFl8fGVufDB8fHx8&w=1000&q=80"), path);
        downloader.download();
        assertEquals(1, Objects.requireNonNull(new File(path).listFiles()).length);
    }

    @Test(expected = IOException.class)
    public void download_WrongDirectory_ThrowsIOException() throws IOException {
        String path = "/Users/dklishch/Documents/IntelliJ Idea Projects/PhotoDownloader/src/test/resources/downloader_download_asdafasdaadasad";
        Downloader downloader = new Downloader(Arrays.asList("https://images.unsplash.com/photo-1639610834110-0de6043492f0?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDF8ZWRRT21paGVVdFl8fGVufDB8fHx8&w=1000&q=80"), path);
        downloader.download();
    }

    @Test(expected = IllegalStateException.class)
    public void download_WrongLink_ThrowsIllegalStateException() throws IOException {
        String path = "/Users/dklishch/Documents/IntelliJ Idea Projects/PhotoDownloader/src/test/resources/downloader_download_test";
        Downloader downloader = new Downloader(Arrays.asList("asdafaaad"), path);
        downloader.download();
    }

    @Test(expected = IllegalArgumentException.class)
    public void copyDataFromURLToFile_WrongURL_ThrowsIllegalArgumentException() throws Exception {
        Downloader downloader = new Downloader();
        Whitebox.invokeMethod(downloader, "copyDataFromURLToFile", "sada");
    }

    @Test
    public void copyDataFromURLToFile_RightURL_DownloadsFile() throws Exception {
        String path = "/Users/dklishch/Documents/IntelliJ Idea Projects/PhotoDownloader/src/test/resources";
        Downloader downloader = new Downloader(null, path);
        File file = new File(path + "/text.txt");
        Whitebox.invokeMethod(downloader, "copyDataFromURLToFile", "https://gist.githubusercontent.com/KlishchD/f8f7a403241538ebb97a795b5c97e3bc/raw/9428130adab13ccac3f12bf824f241f023706f72/hello.txt", file);

        assertEquals("hello world", readFromFile(file));
    }

    private String readFromFile(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);

        StringBuilder builder = new StringBuilder();
        int tmp;
        while ((tmp = inputStream.read()) != -1) {
            builder.append((char)tmp);
        }

        return builder.toString();
    }

}
