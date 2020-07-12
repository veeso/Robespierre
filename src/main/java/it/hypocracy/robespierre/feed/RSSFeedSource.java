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

import java.net.URI;

import it.hypocracy.robespierre.utils.ISO3166;

public class RSSFeedSource implements FeedSource {
  
  private URI uri;
  private ISO3166 country;

  public RSSFeedSource(URI uri, ISO3166 country) {
    this.uri = uri;
    this.country = country;
  }

  @Override
  public URI getURI() {
    return this.uri;
  }

  @Override
  public ISO3166 getCountry() {
    return this.country;
  }

}
