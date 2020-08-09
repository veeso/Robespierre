/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.feedreceiver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;

import com.rometools.rome.io.FeedException;

import org.junit.Test;

import it.hypocracy.robespierre.feed.Feed;
import it.hypocracy.robespierre.feed.RSSFeedSource;
import it.hypocracy.robespierre.utils.ISO3166;

public class RSSFeedReceiverTest {

  private static String testRssSource = "https://lorem-rss.herokuapp.com/feed?length=10";

  @Test
  public void shouldReceiveAndParseFeed() throws IOException, FeedException {
    RSSFeedReceiver receiver = new RSSFeedReceiver();
    RSSFeedSource testSource = new RSSFeedSource(URI.create(testRssSource), new ISO3166("IT"));
    // Receive
    Feed[] feeds = receiver.fetchFeed(testSource);
    // Should have 10 entries
    assertEquals(10, feeds.length);
    // Iterate over feeds
    LocalDateTime now = LocalDateTime.now();
    for (Feed feed : feeds) {
      // Verify that title begins with 'Lorem ipsum'
      assertTrue(feed.getTitle().startsWith("Lorem ipsum"));
      assertTrue(feed.getBrief().length() > 0);
      assertEquals(now.getMonth(), feed.getPublicationDatetime().getMonth());
      assertTrue(feed.getLink().toString().startsWith("http://example.com"));
    }
  }

}
