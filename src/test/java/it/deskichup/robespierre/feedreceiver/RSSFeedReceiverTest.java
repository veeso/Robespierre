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

package it.deskichup.robespierre.feedreceiver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;

import com.rometools.rome.io.FeedException;

import org.junit.Test;

import it.deskichup.robespierre.feed.Feed;
import it.deskichup.robespierre.feed.RSSFeedSource;
import it.deskichup.robespierre.utils.ISO3166;

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
