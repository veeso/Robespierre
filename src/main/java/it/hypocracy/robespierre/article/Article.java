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

package it.hypocracy.robespierre.article;

import it.hypocracy.robespierre.utils.ISO3166;
import it.hypocracy.robespierre.utils.Uuidv4;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Article represents the Article entity as described in the Hypocracy Database.
 * It is a wrapper for all the properties of a newspaper article (received from flows)
 * in addition includes the associated topics and subjects
 */

public class Article {

  private String id; // UUIDv4
  protected String title;
  protected String brief;
  protected URI link;
  protected LocalDateTime date;
  //protected ArticleState state;
  protected ISO3166 country;

  protected ArrayList<Subject> subjects; // Involved subjects
  protected ArrayList<Topic> topics; // Involved topics

  /**
   * <p>
   * Instantiate a new Article
   * </p>
   * 
   * @param title
   * @param brief
   * @param link
   * @param date
   * @param country
   */

  public Article(String title, String brief, URI link, LocalDateTime date, ISO3166 country) {
    this.id = new Uuidv4().getUUIDv4();
    this.title = title;
    this.brief = brief;
    this.link = link;
    this.date = date;
    this.subjects = new ArrayList<Subject>();
    this.topics = new ArrayList<Topic>();
    this.country = country;
  }

  public String getId() {
    return this.id;
  }

  public String getTitle() {
    return this.title;
  }

  public String getBrief() {
    return this.brief;
  }

  public URI getLink() {
    return this.link;
  }

  public LocalDateTime getDate() {
    return this.date;
  }

  public ISO3166 getCountry() {
    return this.country;
  }

  /**
   * <p>
   * Returns an iterator over subjects
   * </p>
   * 
   * @return subjects iterator
   */

  public Iterator<Subject> iterSubjects() {
    return this.subjects.iterator();
  }

  /**
   * <p>
   * Returns an iterator over topics
   * </p>
   * 
   * @return topics iterator
   */

  public Iterator<Topic> iterTopics() {
    return this.topics.iterator();
  }

  /**
   * <p>
   * Add a subject to the subjects list
   * </p>
   * 
   * @param newSubject
   */

  public void addSubject(Subject newSubject) {
    this.subjects.add(newSubject);
  }

  /**
   * <p>
   * Add a topic to the topics list
   * </p>
   * 
   * @param newTopic
   */

  public void addTopic(Topic newTopic) {
    this.topics.add(newTopic);
  }

}
