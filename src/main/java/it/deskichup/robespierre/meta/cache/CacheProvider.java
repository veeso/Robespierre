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

package it.deskichup.robespierre.meta.cache;

import java.util.Iterator;

import org.apache.log4j.Logger;

import it.deskichup.robespierre.article.Article;
import it.deskichup.robespierre.article.Subject;
import it.deskichup.robespierre.article.Topic;
import it.deskichup.robespierre.meta.exceptions.CacheException;
import it.deskichup.robespierre.meta.search.SearchEntity;

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
