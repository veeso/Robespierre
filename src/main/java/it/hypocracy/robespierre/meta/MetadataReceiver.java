/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.meta;

import it.hypocracy.robespierre.article.Article;
import it.hypocracy.robespierre.meta.cache.CacheProvider;
import it.hypocracy.robespierre.meta.exceptions.CacheException;
import it.hypocracy.robespierre.meta.exceptions.MetadataReceiverException;
import it.hypocracy.robespierre.meta.exceptions.ParserException;
import it.hypocracy.robespierre.meta.search.SearchBuilder;
import it.hypocracy.robespierre.meta.search.SearchEntity;

/**
 * MetadataReceiver is the base class for all metadata receivers. It provides
 * common modules to manage metadata search. The subclasses must implement the
 * methods to fetch the database
 */

public abstract class MetadataReceiver {

  protected CacheProvider cache;
  protected SearchBuilder searchBuilder;

  /**
   * <p>
   * Fetch metadata for a certain article querying the Metadata provider
   * </p>
   * 
   * @param article
   * @throws MetadataReceiverException
   * @throws CacheException
   * @throws ParserException
   */

  public void fetchMetadata(Article article) throws MetadataReceiverException, CacheException, ParserException {
    // Keep only letters, all to lowercase and split by spaces
    String[] titleWords = article.getTitle().split("\\s+");
    String[] briefWords = article.getBrief().split("\\s+");
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
   * @throws CacheException
   * @throws ParserException
   */

  private void fetchMetadataFromWords(Article article, String[] words)
      throws MetadataReceiverException, CacheException, ParserException {
    // Build Search Entity
    SearchEntity[] searchEntities = searchBuilder.buildSearchForSubjectsAndTopics(words);
    // Iterate over words
    for (SearchEntity search : searchEntities) {
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
   * @throws CacheException
   * @throws ParserException
   */

  private boolean fetchMetadataFromText(Article article, SearchEntity search)
      throws MetadataReceiverException, CacheException, ParserException {
    if (this.cache != null) {
      // Check if search is blacklisted
      if (this.cache.isBlacklistEnabled()) {
        if (this.cache.isWordBlacklisted(search.getSearch(), article.getCountry().toISO639().toString())) {
          return false; // Word is blacklisted
        }
      }
      // Search through cache values
      if (this.cache.fetchCachedValues(article, search)) {
        return true;
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
   * @throws CacheException
   */

  final protected void blacklistSearch(SearchEntity search, String language) throws CacheException {
    // Check if search is blacklisted
    if (this.cache != null) {
      if (this.cache.isBlacklistEnabled()) {
        this.cache.blacklistWord(search.getSearch(), language);
      }
    }
  }

  // @! Abstract

  protected abstract boolean queryMetadataProvider(Article article, SearchEntity search)
      throws MetadataReceiverException, ParserException, CacheException;

}
