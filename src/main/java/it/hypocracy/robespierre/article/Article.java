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
