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

import org.junit.Test;

import it.hypocracy.robespierre.utils.ISO3166;

public class RSSFeedSourceTest {

  @Test
  public void shouldInstantiateRSSFeedSource() {
    ISO3166 country = new ISO3166("IT");
    URI uri = URI.create("http://mynews.com/rss");
    RSSFeedSource source = new RSSFeedSource(uri, country);
    assertEquals(source.getURI(), uri);
    assertEquals(country.toString(), source.getCountry().toString());
  }
  
}
