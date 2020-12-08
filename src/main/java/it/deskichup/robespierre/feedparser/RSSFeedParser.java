/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * MIT License
 * 
 * Copyright (c) 2020 Christian Visintin
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package it.deskichup.robespierre.feedparser;

import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import it.deskichup.robespierre.feed.RSSFeed;
import it.deskichup.robespierre.utils.DateUtils;
import it.deskichup.robespierre.utils.ISO3166;

public class RSSFeedParser implements FeedParser {

  public RSSFeedParser() {
    super();
  }

  /**
   * <p>
   * Parse RSS feed body
   * </p>
   * 
   * @param body
   * @return a list of RSS Feed
   * @throws FeedException
   */

  public RSSFeed[] parse(String body, ISO3166 country) throws FeedException {
    StringReader documentReader = new StringReader(body);
    SyndFeed feed = new SyndFeedInput().build(documentReader);
    // Iterate over entries
    List<SyndEntry> records = feed.getEntries();
    // Instantiate RSS Feed array
    ArrayList<RSSFeed> feeds = new ArrayList<RSSFeed>(records.size());
    for (SyndEntry record : records) {
      // Get parameters
      String title = record.getTitle();
      String description = record.getDescription().getValue();
      String link = record.getLink();
      // If one of them is NULL, ignore this feed
      if (title == null || description == null || link == null) {
        continue;
      }
      title = stripHTML(title);
      description = stripHTML(description);
      // Remove html tags
      if (record.getPublishedDate() != null) {
        // Create feed with date
        feeds.add(new RSSFeed(title, description, URI.create(link),
            DateUtils.dateToLocalDateTime(record.getPublishedDate()), country));
      } else {
        // Create record without date
        feeds.add(new RSSFeed(title, description, URI.create(link), country));
      }
    }
    RSSFeed[] result = new RSSFeed[feeds.size()];
    result = feeds.toArray(result);
    return result;
  }

  /**
   * <p>
   * Remove HTML from string
   * </p>
   * 
   * @param str
   * @return
   */

  private String stripHTML(String str) {
    Document doc = Jsoup.parse(str);
    return doc.text();
  }

}
