import com.PhotoDownloader.PageParsers.PageParserInterface;
import com.PhotoDownloader.PageParsers.RegexPageParser;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class RegexPageParserTest {

    @Test
    public void getGroupsLinks_ReturnsGroupsLinks() throws Exception {
        List<String> expected = Arrays.asList("https://unsplash.com/t/holidays", "https://unsplash.com/t/blockchain");

        PageParserInterface parser = new RegexPageParser(2, 1);
        List<String> actual = Whitebox.invokeMethod(parser, "getGroupsLinks");

        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getPhotosLinksFromGroups_WrongLinks_ThrowsIllegalArgumentException() throws Exception {
        List<String> links = Arrays.asList("sf", "asd");
        PageParserInterface parser = new RegexPageParser(1, 1);
        Whitebox.invokeMethod(parser, "getPhotosLinksFromGroups", links);
    }

    @Test
    public void getPhotosLinksFromGroups_RightLinks_ReturnsLinks() throws Exception {
        List<List<String>> expected = Arrays.asList(Arrays.asList("https://images.unsplash.com/photo-1639610834110-0de6043492f0?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDF8ZWRRT21paGVVdFl8fGVufDB8fHx8&w=1000&q=80"), Arrays.asList("https://images.unsplash.com/photo-1506555191898-a76bacf004ca?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDF8T3h5bnRKb0JERll8fGVufDB8fHx8&w=1000&q=80"));
        List<String> links = Arrays.asList("https://unsplash.com/t/holidays", "https://unsplash.com/t/blockchain");

        PageParserInterface parser = new RegexPageParser(2, 1);
        List<List<String>> actual = Whitebox.invokeMethod(parser, "getPhotosLinksFromGroups", links);

        assertEquals(expected, actual);
    }

    @Test
    public void findPatternMatchingLinesInString_NotMatchingPattern_ReturnsEmptyList() throws Exception {
        String string = "bbbb";
        Pattern pattern = Pattern.compile("(a+)");

        PageParserInterface parser = new RegexPageParser(1, 1);
        List<String> actual = Whitebox.invokeMethod(parser, "findPatternMatchingLinesInString", pattern, string, 2);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void findPatternMatchingLinesInString_TakeLessLinesThanFound_ReturnsListOfSomeMatchedLines() throws Exception {
        String string = "aaaa\naa\nabb";
        Pattern pattern = Pattern.compile("(?m)^(a+)$");
        List<String> expected = Arrays.asList("aaaa");

        PageParserInterface parser = new RegexPageParser(1, 1);
        List<String> actual = Whitebox.invokeMethod(parser, "findPatternMatchingLinesInString", pattern, string, 1);

        assertEquals(expected, actual);
    }

    @Test
    public void findPatternMatchingLinesInString_TakeMoreLinesThanFound_ReturnsListOfAllMatchedLines() throws Exception {
        String string = "aaaa\naa\nabb";
        Pattern pattern = Pattern.compile("(?m)^(a+)$");
        List<String> expected = Arrays.asList("aaaa", "aa");

        PageParserInterface parser = new RegexPageParser(1, 1);
        List<String> actual = Whitebox.invokeMethod(parser, "findPatternMatchingLinesInString", pattern, string, 10);

        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getHTMLFromPage_WrongPath_ThrowsIllegalArgumentException() throws Exception {
        PageParserInterface parser = new RegexPageParser(1, 1);
        Whitebox.invokeMethod(parser, "getHTMLFromPage", "asdad");
    }

}
