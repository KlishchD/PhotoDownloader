import com.PhotoDownloader.PageParsers.PageParserInterface;
import com.PhotoDownloader.PageParsers.SelectorsPageParser;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SelectorsPageParserTests {

    @Test
    public void getGroupsLinks_ReturnsLinksList() throws Exception {
        List<String> expected = Arrays.asList("https://unsplash.com/t/holidays", "https://unsplash.com/t/blockchain", "https://unsplash.com/t/wallpapers");

        PageParserInterface parser = new SelectorsPageParser(3, 1);
        List<String> actual = Whitebox.invokeMethod(parser, "getGroupsLinks");

        assertEquals(expected, actual);
    }

    @Test
    public void getPhotosLinks_RightLink_ReturnsPhotoLinks() throws Exception {
        List<String> expected = Arrays.asList("https://images.unsplash.com/photo-1639610834110-0de6043492f0?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDF8ZWRRT21paGVVdFl8fGVufDB8fHx8&w=1000&q=80", "https://images.unsplash.com/photo-1639736778061-b1dda98d7882?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDN8ZWRRT21paGVVdFl8fGVufDB8fHx8&w=1000&q=80");

        PageParserInterface parser = new SelectorsPageParser(3, 2);
        List<String> actual = Whitebox.invokeMethod(parser, "getPhotosLinks", "https://unsplash.com/t/holidays");

        assertEquals(expected, actual);
    }

    @Test
    public void parse_ReturnsPhotoLinksForGroups() throws Exception {
        List<List<String>> expected = Arrays.asList(Arrays.asList("https://images.unsplash.com/photo-1639610834110-0de6043492f0?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDF8ZWRRT21paGVVdFl8fGVufDB8fHx8&w=1000&q=80", "https://images.unsplash.com/photo-1639736778061-b1dda98d7882?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDN8ZWRRT21paGVVdFl8fGVufDB8fHx8&w=1000&q=80"), Arrays.asList("https://images.unsplash.com/photo-1506555191898-a76bacf004ca?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDF8T3h5bnRKb0JERll8fGVufDB8fHx8&w=1000&q=80", "https://images.unsplash.com/photo-1639815188498-e23242c9c796?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDV8T3h5bnRKb0JERll8fGVufDB8fHx8&w=1000&q=80"), Arrays.asList("https://images.unsplash.com/photo-1639610357225-ba07813b4364?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDF8Ym84alFLVGFFMFl8fGVufDB8fHx8&w=1000&q=80", "https://images.unsplash.com/photo-1639762661512-bf0b2e455297?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDN8Ym84alFLVGFFMFl8fGVufDB8fHx8&w=1000&q=80"));
        PageParserInterface parser = new SelectorsPageParser(3, 2);
        List<List<String>> actual = Whitebox.invokeMethod(parser, "parse");

        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getDocumentFromURL_WrongURL_ThrowsException() throws Exception {
        PageParserInterface parser = new SelectorsPageParser(1, 1);
        Whitebox.invokeMethod(parser, "getDocumentFromURL", "sdaa");
    }
}
