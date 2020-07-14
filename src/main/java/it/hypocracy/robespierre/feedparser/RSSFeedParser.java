/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
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

import it.hypocracy.robespierre.feed.RSSFeed;
import it.hypocracy.robespierre.utils.DateUtils;
import it.hypocracy.robespierre.utils.ISO3166;

public class RSSFeedParser implements FeedParser<RSSFeed> {

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

}
