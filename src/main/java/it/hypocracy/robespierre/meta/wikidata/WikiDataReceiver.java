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

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

import it.hypocracy.robespierre.article.Article;
import it.hypocracy.robespierre.meta.CacheProvider;
import it.hypocracy.robespierre.meta.MetadataReceiver;
import it.hypocracy.robespierre.meta.exceptions.CacheException;
import it.hypocracy.robespierre.meta.exceptions.MetadataReceiverException;
import it.hypocracy.robespierre.meta.exceptions.ParserException;
import it.hypocracy.robespierre.meta.wikidata.search.QueryResult;
import it.hypocracy.robespierre.meta.wikidata.search.Search;
import it.hypocracy.robespierre.meta.wikidata.wbentity.WbEntity;

public class WikiDataReceiver implements MetadataReceiver {

  private CacheProvider cache;

  /**
   * <p>
   * WikiDataReceiver constructor
   * </p>
   * 
   * @param cache
   */

  public WikiDataReceiver(CacheProvider cache) {
    this.cache = cache;
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
    String[] titleWords = article.getTitle().replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
    String[] briefWords = article.getBrief().replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
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
    // Iterate over words
    Queue<String> doubleQueue = new ArrayDeque<>();
    Queue<String> tripleQueue = new ArrayDeque<>();
    for (String word : words) {
      if (doubleQueue.size() >= 2) {
        doubleQueue.poll();
      }
      if (tripleQueue.size() >= 3) {
        tripleQueue.poll();
      }
      doubleQueue.add(word);
      tripleQueue.add(word);
      // Search starting from double, then go to single and then to triple
      if (doubleQueue.size() == 2) {
        if (fetchMetadataFromText(article, String.join(" ", doubleQueue))) {
          continue; // Ok
        }
      }
      // Try single word
      if (fetchMetadataFromText(article, word)) {
        continue; // Ok
      }
      // Try with triple...
      if (tripleQueue.size() == 3) {
        if (fetchMetadataFromText(article, String.join(" ", tripleQueue))) {
          continue; // Ok
        }
      }
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

  private boolean fetchMetadataFromText(Article article, String text) throws MetadataReceiverException, CacheException, ParserException {
    if (this.cache != null) {
      // Search through cache
      if (this.cache.fetchCachedValues(article, text)) {
        return true;
      }
    }
    // Search
    return processWikiDataQuery(article, text);
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
   */

  private boolean processWikiDataQuery(Article article, String query) throws MetadataReceiverException, ParserException {
    // First search query argument
    WikiDataApiClient apiClient = new WikiDataApiClient();
    Search searchResult = apiClient.search(query);
    if (searchResult.query == null) {
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
      WbEntity wbEntity = apiClient.getWebEntity(wikimediaId, article.getLanguage());
      if (parser.parseWbEntity(wbEntity, wikimediaId, article)) {
        return true;
      } else {
        continue;
      }
    }
    return false;
  }

}
