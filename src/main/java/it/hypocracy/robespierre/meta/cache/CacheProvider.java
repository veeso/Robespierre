/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.meta.cache;

import java.util.Iterator;

import it.hypocracy.robespierre.article.Article;
import it.hypocracy.robespierre.article.Subject;
import it.hypocracy.robespierre.article.Topic;
import it.hypocracy.robespierre.meta.exceptions.CacheException;
import it.hypocracy.robespierre.meta.search.SearchEntity;

/**
 * <p>
 * Cache Provider for metadata (e.g. a database)
 * </p>
 */

public abstract class CacheProvider {

  protected int cacheExpiration;

  /**
   * <p>
   * Fetch cached values and fill article if possible
   * </p>
   * 
   * @param article
   * @return true if what was matched
   * @throws CacheException
   */
  public boolean fetchCachedValues(Article article, SearchEntity what) throws CacheException {
    final String language = article.getCountry().toISO639().toString();
    switch (what.getTarget()) {
      case SUBJECT:
        // Search subjects
        Subject[] matchingSubjects = null;
        matchingSubjects = this.searchSubjects(what.getSearch().toLowerCase(), language);
        // Add subjects to article
        for (Subject s : matchingSubjects) {
          if (!isSubjectDuped(article.iterSubjects(), s)) {
            article.addSubject(s);
          } // Else continue
        }
        // Return true if matchingSubjects length is > 0
        return matchingSubjects.length > 0;

      case TOPIC:
        // Search topics
        Topic[] topics = null;
        topics = this.searchTopics(what.getSearch().toLowerCase(), language);
        // Add topics to article
        for (Topic t : topics) {
          if (!isTopicDuped(article.iterTopics(), t)) {
            article.addTopic(t);
          } // Else continue
        }
        // Return true if topics length is > 0
        return topics.length > 0;
    }
    return false;
  }

  /**
   * <p>
   * Search subject by name
   * </p>
   * 
   * @param match
   * @param language
   * @return Subjects
   * @throws CacheException
   */

  public abstract Subject[] searchSubjects(String match, String language)
      throws CacheException;

  /**
   * <p>
   * Search topic by name
   * </p>
   * 
   * @param match
   * @param language
   * @return Topics
   * @throws CacheException
   */

  public abstract Topic[] searchTopics(String match, String language) throws CacheException;

  /**
   * <p>
   * Check if subject is duped
   * </p>
   * 
   * @param subjects
   * @param check
   * @return boolean
   */

  protected boolean isSubjectDuped(Iterator<Subject> subjects, Subject check) {
    while (subjects.hasNext()) {
      Subject lval = subjects.next();
      if (lval.getName().equals(check.getName()) && lval.getBirthdate() == check.getBirthdate()) {
        return true;
      }
    }
    return false;
  }

  /**
   * <p>
   * Check if topic is duped
   * </p>
   * 
   * @param topics
   * @param check
   * @return boolean
   */

  protected boolean isTopicDuped(Iterator<Topic> topics, Topic check) {
    while (topics.hasNext()) {
      Topic lval = topics.next();
      if (lval.getName().equals(check.getName())) {
        return true;
      }
    }
    return false;
  }

}
