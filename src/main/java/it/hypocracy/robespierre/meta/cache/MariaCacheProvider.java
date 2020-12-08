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

package it.hypocracy.robespierre.meta.cache;

import java.sql.SQLException;

import it.hypocracy.robespierre.article.Subject;
import it.hypocracy.robespierre.article.Topic;
import it.hypocracy.robespierre.core.FeedDatabase;
import it.hypocracy.robespierre.database.driver.MariaFacade;
import it.hypocracy.robespierre.meta.exceptions.CacheException;

/**
 * MariaCacheProvider is the implementation of a CacheProvider using the MariaDB façade
 */

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
   * @param withBlacklist
   */
  public MariaCacheProvider(String uri, String user, String password, int expiration, boolean withBlacklist) {
    this.database = new FeedDatabase(new MariaFacade(uri, user, password));
    this.cacheExpiration = expiration;
    this.withBlacklist = withBlacklist;
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

  @Override
  public boolean isWordBlacklisted(String word, String language) throws CacheException {
    try {
      return this.database.searchBlacklist(word.toLowerCase(), this.cacheExpiration, language);
    } catch (IllegalArgumentException | SQLException e) {
      throw new CacheException(e.getMessage());
    }
  }

  @Override
  public void blacklistWord(String word, String language) throws CacheException {
    try {
      this.database.commitBlacklistWord(word.toLowerCase(), language);
    } catch (IllegalArgumentException | SQLException e) {
      throw new CacheException(e.getMessage());
    }
  }

}
