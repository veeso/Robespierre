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

import java.sql.SQLException;
import java.time.LocalDateTime;

import it.hypocracy.robespierre.article.Article;
import it.hypocracy.robespierre.article.Subject;
import it.hypocracy.robespierre.article.Topic;
import it.hypocracy.robespierre.core.FeedDatabase;
import it.hypocracy.robespierre.database.driver.MariaFacade;
import it.hypocracy.robespierre.meta.exceptions.CacheException;
import it.hypocracy.robespierre.meta.search.SearchEntity;

public class MariaCacheProvider implements CacheProvider {

  private FeedDatabase database;
  private int cacheExpiration;

  /**
   * <p>
   * MariaCacheProvider constructor
   * </p>
   * 
   * @param uri
   * @param user
   * @param password
   * @param expiration (days)
   */
  public MariaCacheProvider(String uri, String user, String password, int expiration) {
    this.database = new FeedDatabase(new MariaFacade(uri, user, password));
    this.cacheExpiration = expiration;
  }

  /**
   * <p>
   * Fetch cached values and fill article if possible
   * </p>
   * 
   * @param article
   * @return true if what was matched
   * @throws CacheException
   */
  @Override
  public boolean fetchCachedValues(Article article, SearchEntity what) throws CacheException {
    final String language = article.getCountry().toISO639().toString();
    switch (what.getTarget()) {
      case SUBJECT:
        // Calculate expiration date
        LocalDateTime expirationDate = LocalDateTime.now().plusDays(cacheExpiration);
        // Search subjects
        Subject[] matchingSubjects = null;
        try {
          matchingSubjects = this.database.searchSubject(what.getSearch().toLowerCase(), expirationDate, language);
        } catch (IllegalArgumentException | SQLException e) {
          throw new CacheException(e.getMessage());
        }
        // Add subjects to article
        for (Subject s : matchingSubjects) {
          article.addSubject(s);
        }
        // Return true if matchingSubjects length is > 0
        return matchingSubjects.length > 0;

      case TOPIC:
        // Search topics
        Topic[] topics = null;
        try {
          topics = this.database.searchTopic(what.getSearch().toLowerCase(), language);
        } catch (IllegalArgumentException | SQLException e) {
          throw new CacheException(e.getMessage());
        }
        // Add topics to article
        for (Topic t : topics) {
          article.addTopic(t);
        }
        // Return true if topics length is > 0
        return topics.length > 0;
    }
    return false;
  }

}
