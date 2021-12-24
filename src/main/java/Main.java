import com.PhotoDownloader.Downloader.MultithreadedDownloader;
import com.PhotoDownloader.PageParsers.RegexPageParser;
import com.PhotoDownloader.PageParsers.SelectorsPageParser;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
/*
        for (int i = 0; i < 6; ++i) {

            RegexPageParser regexPageParser = new RegexPageParser(3, 5);

            String pathForRegex = System.getProperty("user.home") + "/Documents/Test/Regex";

            MultithreadedDownloader.download(regexPageParser.parse(), pathForRegex);
        }
*/
        SelectorsPageParser selectorsPageParser = new SelectorsPageParser(3, 5);

        String pathForSelectors = System.getProperty("user.home") + "/Documents/Test/Selectors";


        List<List<String>> links = selectorsPageParser.parse();

        for (List<String> groupLinks : links) {
            for (String link : groupLinks) {
                System.out.println(link);
            }
            System.out.println("---------_____________-------------");
        }
        MultithreadedDownloader.download(links, pathForSelectors);
    }
}
