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

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.Month;
import java.time.ZoneId;

import com.rometools.rome.io.FeedException;

import org.junit.Test;

import it.deskichup.robespierre.feed.RSSFeed;
import it.deskichup.robespierre.utils.ISO3166;

public class RSSFeedParserTest {

  private static String testFeed1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><rss xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:taxo=\"http://purl.org/rss/1.0/modules/taxonomy/\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" version=\"2.0\"><channel><title>BadNews</title><link>https://www.badnews.cc/disasters</link><description><![CDATA[Latest bad news]]></description><country>en-US</country><copyright>Copyright 2020 - Pippo</copyright><pubDate>Sat, 27 Jun 2020 22:14:26 +0200</pubDate><lastBuildDate>Sat, 27 Jun 2020 22:14:26 +0200</lastBuildDate><ttl>30</ttl><docs>http://blogs.law.harvard.edu/tech/rss</docs><managingEditor>Bad news</managingEditor><webMaster>issues@badnews.cc</webMaster><image><title>Badnews.cc &gt; Politics</title><url>http://www.badnews.cc/images/logo.gif</url><link>http://www.badnews.cc/politics</link><width>128</width><height>128</height></image><item><title><![CDATA[You've met with a terrible fate]]></title><link><![CDATA[https://www.badnews.cc/politics/you-ve-met-with-a-terrible-fate-havent-you]]></link><pubDate>Sat, 27 Jun 2020 18:37:39 +0200</pubDate><enclosure url=\"https://www.repstatic.it/content/nazionale/img/2020/06/27/171101822-bbb0cc3c-b73d-4583-bbf8-6eb4c94bc6e9.jpg\" length=\"35585\" type=\"image/jpeg\" /><description><![CDATA[<p><a href=\"https://www.badnews.cc/you-ve-met-with-a-terrible-fate-havent-you/?rssimage\"><img src=\"https://www.badnews.cc/thumbnails/171101822-bbb0cc3c-b73d-4583-bbf8-6eb4c94bc6e9.jpg\" width=\"140\" align=\"left\" hspace=\"10\"></a>You've met with a terrible fate, haven't you. Nice quote m8.</p>]]></description></item><item><title><![CDATA[Vegetables are healthy!]]></title><link><![CDATA[https://www.badnews.cc/politics/vegetables-are-healthy]]></link><pubDate>Sat, 27 Jun 2020 12:32:12 +0200</pubDate><enclosure url=\"https://www.repstatic.it/content/nazionale/img/2020/06/27/171101822-bbb0cc3c-b73d-4583-bbf8-6eb4c94bc6e9.jpg\" length=\"35585\" type=\"image/jpeg\" /><description><![CDATA[<p><a href=\"https://www.badnews.cc/politics/vegetables-are-healthy?rssimage\"><img src=\"https://www.badnews.cc/thumbnails/171101822-bbb0cc3c-b73d-4583-bbf8-6eb4c94bc6e9.jpg\" width=\"140\" align=\"left\" hspace=\"10\"></a>Scientists are shocked!</p>]]></description></item></channel></rss>";

  @Test
  public void shouldParseFeed1() throws IllegalArgumentException, FeedException {
    RSSFeedParser parser = new RSSFeedParser();
    RSSFeed[] feeds = parser.parse(testFeed1, new ISO3166("IT"));
    // There should be only two items, image should be ignored
    assertEquals(feeds.length, 2);
    RSSFeed firstItem = feeds[0];
    // Verify item parameters
    assertEquals("You've met with a terrible fate", firstItem.getTitle());
    assertEquals("You've met with a terrible fate, haven't you. Nice quote m8.", firstItem.getBrief());
    assertEquals(URI.create("https://www.badnews.cc/politics/you-ve-met-with-a-terrible-fate-havent-you"),
        firstItem.getLink());
    assertEquals(ZonedDateTime.of(LocalDateTime.of(2020, Month.JUNE, 27, 16, 37, 39), ZoneId.of("UTC")), ZonedDateTime.of(firstItem.getPublicationDatetime(), ZoneId.of("UTC")));
    assertEquals("IT", firstItem.getCountry().toString());
    RSSFeed secondItem = feeds[1];
    // Verify item parameters
    assertEquals("Vegetables are healthy!", secondItem.getTitle());
    assertEquals("Scientists are shocked!", secondItem.getBrief());
    assertEquals(URI.create("https://www.badnews.cc/politics/vegetables-are-healthy"), secondItem.getLink());
    assertEquals(ZonedDateTime.of(LocalDateTime.of(2020, Month.JUNE, 27, 10, 32, 12), ZoneId.of("UTC")), ZonedDateTime.of(secondItem.getPublicationDatetime(), ZoneId.of("UTC")));
    assertEquals("IT", secondItem.getCountry().toString());
  }

}
