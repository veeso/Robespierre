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
