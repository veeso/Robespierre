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

import it.hypocracy.robespierre.article.Subject;
import it.hypocracy.robespierre.article.Topic;
import it.hypocracy.robespierre.core.FeedDatabase;
import it.hypocracy.robespierre.database.driver.MariaFacade;
import it.hypocracy.robespierre.meta.exceptions.CacheException;

public class MariaCacheProvider extends CacheProvider {

  private FeedDatabase database;

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

  @Override
  public Subject[] searchSubjects(String match, String language) throws CacheException {
    try {
      return this.database.searchSubject(match, this.cacheExpiration, language);
    } catch (IllegalArgumentException | SQLException e) {
      throw new CacheException(e.getMessage());
    }
  }

  @Override
  public Topic[] searchTopics(String match, String language) throws CacheException {
    try {
      return this.database.searchTopic(match, language);
    } catch (IllegalArgumentException | SQLException e) {
      throw new CacheException(e.getMessage());
    }
  }

}
