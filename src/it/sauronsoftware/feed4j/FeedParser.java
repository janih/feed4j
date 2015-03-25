package it.sauronsoftware.feed4j;

import it.sauronsoftware.feed4j.bean.Feed;
import java.net.URL;
import java.net.URLConnection;
import java.io.InputStream;
import java.io.IOException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;


/**
 * The feed parser. It can parse RSS 1.0, RSS 2.0, Atom 0.3 and Atom 1.0.
 * 
 * @author Carlo Pelliccia
 */
public class FeedParser {

	/**
	 * Gets the feed from an URL and parses it.
	 * 
	 * @param url
	 *            The feed URL.
	 * @return A Feed object containing the information extracted from the feed.
	 * @throws IOException
	 *             I/O error during contents retrieving.
	 * @throws FeedIOException
	 *             I/O error during contents retrieving.
	 * @throws FeedXMLParseException
	 *             The document retrieved is not valid XML.
	 * @throws UnsupportedFeedException
	 *             The XML retrieved does not represents a feed whose kind is
	 *             known by the parser.
	 */
	public static Feed parse(URL url) throws IOException, FeedIOException,
			FeedXMLParseException, UnsupportedFeedException {
		return parse(url, null);
	}

	/**
	 * Gets the feed from an URL and parses it.
	 * 
	 * @param url
	 *            The feed URL.
	 * @param userAgent
	 *            User agent string.
	 * @return A Feed object containing the information extracted from the feed.
	 * @throws IOException
	 *             I/O error during contents retrieving.
	 * @throws FeedIOException 
	 *             I/O error during contents retrieving.
	 * @throws FeedXMLParseException
	 *             The document retrieved is not valid XML.
	 * @throws UnsupportedFeedException
	 *             The XML retrieved does not represents a feed whose kind is
	 *             known by the parser.
	 */	
	public static Feed parse(URL url, String userAgent) throws IOException, FeedIOException, FeedXMLParseException, UnsupportedFeedException {
		InputStream is = null;
		try {
			URLConnection con = url.openConnection();
			if (userAgent != null) {
				con.addRequestProperty("User-Agent", userAgent);
			}
			con.connect();
			is = con.getInputStream();
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(is);
			int code = FeedRecognizer.recognizeFeed(document);
			switch (code) {
			case FeedRecognizer.RSS_1_0:
				return TypeRSS_1_0.feed(url, document);
			case FeedRecognizer.RSS_2_0:
				return TypeRSS_2_0.feed(url, document);
			case FeedRecognizer.ATOM_0_3:
				return TypeAtom_0_3.feed(url, document);
			case FeedRecognizer.ATOM_1_0:
				return TypeAtom_1_0.feed(url, document);
			default:
				throw new UnsupportedFeedException();
			}
		} catch (DocumentException e) {
			throw new FeedXMLParseException(e);
		}
		finally {
			if (is != null) { 
				is.close();
			}
		}
	}
}
