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

package it.hypocracy.robespierre.feed;


import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Test;

import it.hypocracy.robespierre.utils.ISO3166;

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
