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

import it.hypocracy.robespierre.article.Article;
import it.hypocracy.robespierre.meta.exceptions.CacheException;
import it.hypocracy.robespierre.meta.search.SearchEntity;

/**
 * <p>
 * Cache Provider for metadata (e.g. a database)
 * </p>
 */

public interface CacheProvider {
  
  /**
   * <p>
   * Fetch cached values and fill article if possible
   * </p>
   * @param article
   * @return true if what was matched
   * @throws CacheException
   */
  public boolean fetchCachedValues(Article article, SearchEntity what) throws CacheException;

}
