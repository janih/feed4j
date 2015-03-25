package feed4j;

import it.sauronsoftware.feed4j.FeedParser;
import it.sauronsoftware.feed4j.bean.Feed;
import it.sauronsoftware.feed4j.bean.FeedHeader;
import it.sauronsoftware.feed4j.bean.FeedItem;

import java.net.URL;
import org.junit.Test;

public class SimpleTest {

    @Test
    public void testUserAgent() throws Exception {
		URL url = new URL("http://feeds.bbci.co.uk/news/rss.xml");
		Feed feed = FeedParser.parse(url, "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");

		System.out.println("** HEADER **");
		FeedHeader header = feed.getHeader();
		System.out.println("Title: " + header.getTitle());
		System.out.println("Link: " + header.getLink());
		System.out.println("Description: " + header.getDescription());
		System.out.println("Language: " + header.getLanguage());
		System.out.println("PubDate: " + header.getPubDate());

		System.out.println("** ITEMS **");
		int items = feed.getItemCount();
		for (int i = 0; i < items; i++) {
			FeedItem item = feed.getItem(i);
			System.out.println("Title: " + item.getTitle());
			System.out.println("Link: " + item.getLink());
			System.out.println("Plain text description: " + item.getDescriptionAsText());
			System.out.println("HTML description: " + item.getDescriptionAsHTML());
			System.out.println("PubDate: " + item.getPubDate());
		}        
    }
}
