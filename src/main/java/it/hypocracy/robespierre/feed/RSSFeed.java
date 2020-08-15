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
import java.time.LocalDateTime;

import it.hypocracy.robespierre.utils.ISO3166;

/**
 * RSSFeed is the class representing explicitly an RSS feed
 */

public class RSSFeed extends Feed {

  /**
   * <p>
   * Instantiate a new RSS Feed. Date is set with now
   * </p>
   * 
   * @param title
   * @param brief
   * @param link
   * @param country
   */

  public RSSFeed(String title, String brief, URI link, ISO3166 country) {
    super(title, brief, link, country);
  }

  /**
   * <p>
   * Instantiate a new RSS Feed. Publication time is provided
   * </p>
   * 
   * @param title
   * @param brief
   * @param link
   * @param publicationTime
   * @param country
   */

  public RSSFeed(String title, String brief, URI link, LocalDateTime pTime, ISO3166 country) {
    super(title, brief, link, pTime, country);
  }
  
}
