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

public class RSSFeed implements Feed {

  protected String title;
  protected String brief;
  protected URI link;
  protected LocalDateTime publicationDate;

  /**
   * <p>
   * Instantiate a new RSS Feed. Date is set with now
   * </p>
   * 
   * @param title
   * @param brief
   * @param link
   */

  public RSSFeed(String title, String brief, URI link) {
    this.title = title;
    this.brief = brief;
    this.link = link;
    this.publicationDate = LocalDateTime.now();
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
   */

  public RSSFeed(String title, String brief, URI link, LocalDateTime pTime) {
    this.title = title;
    this.brief = brief;
    this.link = link;
    this.publicationDate = pTime;
  }

  @Override
  public String getTitle() {
    return this.title;
  }

  @Override
  public String getBrief() {
    return this.brief;
  }

  @Override
  public URI getLink() {
    return this.link;
  }

  @Override
  public LocalDateTime getPublicationDatetime() {
    return this.publicationDate;
  }
  
}
