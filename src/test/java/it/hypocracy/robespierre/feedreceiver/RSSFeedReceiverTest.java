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
