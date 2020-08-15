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

/**
 * WikiDataReceiver is the implementation of a MetadataReceiver to collect
 * metadata using WikiData as database
 */

public class WikiDataReceiver extends MetadataReceiver {

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

  @Override
  protected boolean queryMetadataProvider(Article article, SearchEntity search)
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

}
