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

package it.deskichup.robespierre.feed;


import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Test;

import it.deskichup.robespierre.utils.ISO3166;

public class RSSFeedTest {
  
  @Test
  public void shouldInstantiateFeedNoDate() {
    String title = "Apples are healthy";
    String brief = "Scientists have just discovered that apples are healthy for you";
    URI link = URI.create("https://health24.news");
    ISO3166 country = new ISO3166("GB");
    RSSFeed feed = new RSSFeed(title, brief, link, country);
    assertEquals(title, feed.getTitle());
    assertEquals(brief, feed.getBrief());
    assertEquals(link, feed.getLink());
    assertEquals("GB", feed.getCountry().toString());
    assertEquals(LocalDateTime.now().getMonth(), feed.getPublicationDatetime().getMonth());
  }

  @Test
  public void shouldInstantiateFeedWithDate() {
    String title = "Apples are healthy";
    String brief = "Scientists have just discovered that apples are healthy for you";
    URI link = URI.create("https://health24.news");
    ISO3166 country = new ISO3166("GB");
    LocalDateTime tm = LocalDateTime.of(2020, Month.JUNE, 28, 16, 32);
    RSSFeed feed = new RSSFeed(title, brief, link, tm, country);
    assertEquals(title, feed.getTitle());
    assertEquals(brief, feed.getBrief());
    assertEquals(link, feed.getLink());
    assertEquals("GB", feed.getCountry().toString());
    assertEquals(tm, feed.getPublicationDatetime());
  }

}
