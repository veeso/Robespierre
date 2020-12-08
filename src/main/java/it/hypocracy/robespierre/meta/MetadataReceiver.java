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

package it.hypocracy.robespierre.meta;

import org.apache.log4j.Logger;

import it.hypocracy.robespierre.article.Article;
import it.hypocracy.robespierre.meta.cache.CacheProvider;
import it.hypocracy.robespierre.meta.exceptions.CacheException;
import it.hypocracy.robespierre.meta.exceptions.MetadataReceiverException;
import it.hypocracy.robespierre.meta.search.SearchBuilder;
import it.hypocracy.robespierre.meta.search.SearchEntity;

/**
 * MetadataReceiver is the base class for all metadata receivers. It provides
 * common modules to manage metadata search. The subclasses must implement the
 * methods to fetch the database
 */

public abstract class MetadataReceiver {

  protected final static Logger logger = Logger.getLogger(MetadataReceiver.class.getName());

  protected CacheProvider cache;
  protected SearchBuilder searchBuilder;

  /**
   * <p>
   * Fetch metadata for a certain article querying the Metadata provider
   * </p>
   * 
   * @param article
   * @throws MetadataReceiverException
   */

  public void fetchMetadata(Article article) throws MetadataReceiverException {
    // Keep only letters, all to lowercase and split by spaces
    String[] titleWords = article.getTitle().split("\\s+");
    String[] briefWords = article.getBrief().split("\\s+");
    logger.debug("Getting metadata for article " + article.getId());
    // Fetch words collections
    fetchMetadataFromWords(article, titleWords);
    fetchMetadataFromWords(article, briefWords);
  }

  /**
   * <p>
   * Fetch metadata starting from a collection of words. The algorithms iterates
   * over words and creates a subset of words to search from. Then it calls
   * fetchMetadataFromText
   * </p>
   * 
   * @param article
   * @param words
   * @throws MetadataReceiverException
   */

  private void fetchMetadataFromWords(Article article, String[] words) throws MetadataReceiverException {
    // Build Search Entity
    SearchEntity[] searchEntities = searchBuilder.buildSearchForSubjectsAndTopics(words);
    // Iterate over words
    for (SearchEntity search : searchEntities) {
      if (search.getSearch().length() == 0) { // Ignore empty sets
        continue;
      }
      logger.debug("Getting metadata for search target: '" + search.getSearch() + "'");
      fetchMetadataFromText(article, search);
    }
  }

  /**
   * <p>
   * fetch metadata from text searching it in cache and then if cache returns
   * nothing from wikidata
   * </p>
   * 
   * @param article
   * @param text
   * @return true if text matched
   * @throws MetadataReceiverException
   */

  private boolean fetchMetadataFromText(Article article, SearchEntity search) throws MetadataReceiverException {
    if (this.cache != null) {
      try {
        // Check if search is blacklisted
        if (this.cache.isBlacklistEnabled()) {
          if (this.cache.isWordBlacklisted(search.getSearch(), article.getCountry().toISO639().toString())) {
            logger.debug(search.getSearch() + " is blacklisted (" + article.getCountry().toISO639().toString() + ")");
            return false; // Word is blacklisted
          }
        }
        // Search through cache values
        if (this.cache.fetchCachedValues(article, search)) {
          return true;
        }
      } catch (CacheException e) {
        logger.error("Failed to check cached values: " + e.getMessage());
        logger.trace(e);
      }
    }
    // Search
    return queryMetadataProvider(article, search);
  }

  /**
   * <p>
   * Blacklist search entity
   * </p>
   * 
   * @param search
   * @param language
   */

  final protected void blacklistSearch(SearchEntity search, String language) {
    // Check if search is blacklisted
    if (this.cache != null) {
      if (this.cache.isBlacklistEnabled()) {
        try {
          this.cache.blacklistWord(search.getSearch(), language);
        } catch (CacheException e) {
          logger.error(
              "Could not check if search '" + search.getSearch() + "' is blacklisted: " + e.getLocalizedMessage());
          logger.trace(e);
        }
      }
    }
  }

  // @! Abstract

  protected abstract boolean queryMetadataProvider(Article article, SearchEntity search)
      throws MetadataReceiverException;

}
