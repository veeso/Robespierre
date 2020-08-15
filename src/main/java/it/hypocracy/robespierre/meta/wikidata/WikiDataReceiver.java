/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.meta.wikidata;

import java.util.Iterator;

import it.hypocracy.robespierre.article.Article;
import it.hypocracy.robespierre.meta.cache.CacheProvider;
import it.hypocracy.robespierre.meta.MetadataReceiver;
import it.hypocracy.robespierre.meta.exceptions.CacheException;
import it.hypocracy.robespierre.meta.exceptions.MetadataReceiverException;
import it.hypocracy.robespierre.meta.exceptions.ParserException;
import it.hypocracy.robespierre.meta.search.SearchBuilder;
import it.hypocracy.robespierre.meta.search.SearchEntity;
import it.hypocracy.robespierre.meta.search.SearchTarget;
import it.hypocracy.robespierre.meta.wikidata.search.QueryResult;
import it.hypocracy.robespierre.meta.wikidata.search.Search;
import it.hypocracy.robespierre.meta.wikidata.wbentity.WbEntity;

public class WikiDataReceiver implements MetadataReceiver {

  private CacheProvider cache;
  private SearchBuilder searchBuilder;

  /**
   * <p>
   * WikiDataReceiver constructor
   * </p>
   * 
   * @param cache
   */

  public WikiDataReceiver(CacheProvider cache) {
    this.cache = cache;
    this.searchBuilder = new SearchBuilder();
  }

  /**
   * <p>
   * WikiDataReceiver constructor without cache
   * </p>
   * 
   */

  public WikiDataReceiver() {
    this(null);
  }

  @Override
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

  private boolean fetchMetadataFromText(Article article, SearchEntity search) throws MetadataReceiverException, CacheException, ParserException {
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
    return processWikiDataQuery(article, search);
  }

  // @! Processor

  /**
   * <p>
   * Process wiki data query and try to retrieve metadata
   * </p>
   * 
   * @param article
   * @param query
   * @return true if text matched
   * @throws MetadataReceiverException
   * @throws ParserException
   * @throws CacheException
   */

  private boolean processWikiDataQuery(Article article, SearchEntity search)
      throws MetadataReceiverException, ParserException, CacheException {
    // First search query argument
    WikiDataApiClient apiClient = new WikiDataApiClient();
    final String language = article.getCountry().toISO639().toString();
    // Get max resulte from target
    final int maxResults = search.getTarget() == SearchTarget.SUBJECT ? 4 : 1;
    Search searchResult = apiClient.search(search.getSearch(), maxResults);
    if (searchResult.query == null) {
      blacklistSearch(search, language);
      return false;
    }
    // Iterate over query result
    WikiDataParser parser = new WikiDataParser();
    Iterator<QueryResult> queryResultIterator = searchResult.query.search.iterator();
    while (queryResultIterator.hasNext()) {
      QueryResult queryResultEntry = queryResultIterator.next();
      // Get web entity for that Id
      if (queryResultEntry.title == null) {
        continue;
      }
      String wikimediaId = queryResultEntry.title;
      WbEntity wbEntity = apiClient.getWebEntity(wikimediaId, article.getCountry());
      if (parser.parseWbEntity(wbEntity, wikimediaId, article)) {
        return true;
      } else {
        continue;
      }
    }
    // Doesn't return anything important; blacklist search
    blacklistSearch(search, language);
    return false;
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

  private void blacklistSearch(SearchEntity search, String language) throws CacheException {
    // Check if search is blacklisted
    if (this.cache != null) {
      if (this.cache.isBlacklistEnabled()) {
        this.cache.blacklistWord(search.getSearch(), language);
      }
    }
  }

}
