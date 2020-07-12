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

public class RSSFeed implements Feed {

  protected String title;
  protected String brief;
  protected URI link;
  protected LocalDateTime publicationDate;
  protected ISO3166 country;

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
    this.title = title;
    this.brief = brief;
    this.link = link;
    this.publicationDate = LocalDateTime.now();
    this.country = country;
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
    this.title = title;
    this.brief = brief;
    this.link = link;
    this.publicationDate = pTime;
    this.country = country;
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

  @Override
  public ISO3166 getCountry() {
    return this.country;
  }
  
}
