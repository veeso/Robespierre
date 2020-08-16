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

import org.apache.log4j.Logger;

import it.hypocracy.robespierre.article.Article;
import it.hypocracy.robespierre.article.Subject;
import it.hypocracy.robespierre.article.Topic;
import it.hypocracy.robespierre.meta.exceptions.CacheException;
import it.hypocracy.robespierre.meta.search.SearchEntity;

/**
 * Cache Provider is an entity which takes care of caching metadata and to
 * blacklist garbage search in order to speed up searches. This class provides
 * common routines for providers to provide cache, but "platform" specifics must
 * be implemented in subclasses
 */

public abstract class CacheProvider {

  protected final static Logger logger = Logger.getLogger(CacheProvider.class.getName());

  protected int cacheExpiration;
  protected boolean withBlacklist;

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
        logger.debug("Searching for subject: " + what.getSearch());
        Subject[] matchingSubjects = null;
        matchingSubjects = this.searchSubjects(what.getSearch().toLowerCase(), language);
        // Add subjects to article
        for (Subject s : matchingSubjects) {
          logger.debug("Found a subject in cache: " + s.getName());
          if (!isSubjectDuped(article.iterSubjects(), s)) {
            article.addSubject(s);
          } else {
            logger.debug(s.getName() + " is duped for this article");
          }
        }
        if (matchingSubjects.length == 0) {
          logger.debug("No subject matches in cache.");
        }
        // Return true if matchingSubjects length is > 0
        return matchingSubjects.length > 0;

      case TOPIC:
        // Search topics
        logger.debug("Searching for topic: " + what.getSearch());
        Topic[] topics = null;
        topics = this.searchTopics(what.getSearch().toLowerCase(), language);
        // Add topics to article
        for (Topic t : topics) {
          logger.debug("Found topic " + t.getName() + " in cache");
          if (!isTopicDuped(article.iterTopics(), t)) {
            article.addTopic(t);
          } // Else continue
        }
        if (topics.length == 0) {
          logger.debug("No topic matches in cache.");
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

  public abstract Subject[] searchSubjects(String match, String language) throws CacheException;

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

  // Blacklist

  public boolean isBlacklistEnabled() {
    return this.withBlacklist;
  }

  /**
   * <p>
   * Checks if provided word is meta blacklisted
   * </p>
   * 
   * @param word
   * @param language
   * @return boolean
   * @throws CacheException
   */

  public abstract boolean isWordBlacklisted(String word, String language) throws CacheException;

  /**
   * <p>
   * Blacklist a word
   * </p>
   * 
   * @param word
   * @param language
   * @throws CacheException
   */

  public abstract void blacklistWord(String word, String language) throws CacheException;

  // @! Privates

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
      if (lval.getName().equals(check.getName())) {
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
