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
