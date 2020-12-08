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

package it.hypocracy.robespierre.feed;

import java.net.URI;
import java.time.LocalDateTime;

import it.hypocracy.robespierre.utils.ISO3166;

/**
 * Feed is the base class for news feeds (atom, rss)
 */

public class Feed {

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

  public Feed(String title, String brief, URI link, ISO3166 country) {
    this.title = title;
    this.brief = brief;
    this.link = link;
    this.publicationDate = LocalDateTime.now();
    this.country = country;
  }

  /**
   * <p>
   * Instantiate a new Feed. Publication time is provided
   * </p>
   * 
   * @param title
   * @param brief
   * @param link
   * @param publicationTime
   * @param country
   */

  public Feed(String title, String brief, URI link, LocalDateTime pTime, ISO3166 country) {
    this.title = title;
    this.brief = brief;
    this.link = link;
    this.publicationDate = pTime;
    this.country = country;
  }

  /**
   * <p>
   * Returns Feed Title
   * </p>
   * 
   * @return title
   */

  public String getTitle() {
    return this.title;
  }

  /**
   * <p>
   * Returns Feed Brief
   * </p>
   * 
   * @return brief
   */

  public String getBrief() {
    return this.brief;
  }

  /**
   * <p>
   * return feed link
   * </p>
   * 
   * @return link
   */

  public URI getLink() {
    return this.link;
  }

  /**
   * <p>
   * Returns the publication date time
   * </p>
   * 
   * @return LocalDateTime
   */

  public LocalDateTime getPublicationDatetime() {
    return this.publicationDate;
  }

  /**
   * <p>
   * Return Feed country
   * </p>
   * 
   * @return ISO3166
   */

  public ISO3166 getCountry() {
    return this.country;
  }

}
