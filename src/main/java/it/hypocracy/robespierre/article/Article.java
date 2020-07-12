/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.article;

import it.hypocracy.robespierre.utils.ISO3166;
import it.hypocracy.robespierre.utils.Uuidv4;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

public class Article {

  private String id; // UUIDv4
  protected String title;
  protected String brief;
  protected URI link;
  protected LocalDateTime date;
  protected ArticleState state;
  protected ISO3166 language;

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
   */

  public Article(String title, String brief, URI link, LocalDateTime date, ISO3166 lang) {
    this.id = new Uuidv4().getUUIDv4();
    this.title = title;
    this.brief = brief;
    this.link = link;
    this.date = date;
    this.subjects = new ArrayList<Subject>();
    this.topics = new ArrayList<Topic>();
    this.language = lang;
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

  public String getLanguage() {
    return this.language.toString();
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
