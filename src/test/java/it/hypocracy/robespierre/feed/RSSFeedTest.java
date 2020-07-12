/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
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
    assertEquals(feed.getTitle(), title);
    assertEquals(feed.getBrief(), brief);
    assertEquals(feed.getLink(), link);
    assertEquals(feed.getCountry().toString(), "GB");
    assertEquals(feed.getPublicationDatetime().getMonth(), LocalDateTime.now().getMonth());
  }

  @Test
  public void shouldInstantiateFeedWithDate() {
    String title = "Apples are healthy";
    String brief = "Scientists have just discovered that apples are healthy for you";
    URI link = URI.create("https://health24.news");
    ISO3166 country = new ISO3166("GB");
    LocalDateTime tm = LocalDateTime.of(2020, Month.JUNE, 28, 16, 32);
    RSSFeed feed = new RSSFeed(title, brief, link, tm, country);
    assertEquals(feed.getTitle(), title);
    assertEquals(feed.getBrief(), brief);
    assertEquals(feed.getLink(), link);
    assertEquals(feed.getCountry().toString(), "GB");
    assertEquals(feed.getPublicationDatetime(), tm);
  }

}
