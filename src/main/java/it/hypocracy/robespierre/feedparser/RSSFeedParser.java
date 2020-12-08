/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) 2020 Christian Visintin - christian.visintin1997@gmail.com
 *
 * This file is part of "Robespierre"
 *
 * Robespierre is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Robespierre is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Robespierre.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package it.hypocracy.robespierre.feedparser;

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

import it.hypocracy.robespierre.feed.RSSFeed;
import it.hypocracy.robespierre.utils.DateUtils;
import it.hypocracy.robespierre.utils.ISO3166;

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
