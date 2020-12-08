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

package it.hypocracy.robespierre.meta.wikidata;

import java.util.Iterator;

import it.hypocracy.robespierre.article.Article;
import it.hypocracy.robespierre.meta.cache.CacheProvider;
import it.hypocracy.robespierre.meta.MetadataReceiver;
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
   */

  @Override
  protected boolean queryMetadataProvider(Article article, SearchEntity search) throws MetadataReceiverException {
    // First search query argument
    WikiDataApiClient apiClient = new WikiDataApiClient();
    final String language = article.getCountry().toISO639().toString();
    // Get max resulte from target
    final int maxResults = search.getTarget() == SearchTarget.SUBJECT ? 4 : 1;
    logger.debug("Search '" + search.getSearch() + "' has a max results " + maxResults);
    Search searchResult = apiClient.search(search.getSearch(), maxResults);
    if (searchResult.query == null) {
      logger.info("Search '" + search.getSearch() + "' didn't return any search result. Blacklisting it");
      blacklistSearch(search, language);
      return false;
    }
    // Iterate over query result
    WikiDataParser parser = new WikiDataParser();
    logger.debug("Search '" + search.getSearch() + "' returned " + searchResult.query.search.size() + " results");
    Iterator<QueryResult> queryResultIterator = searchResult.query.search.iterator();
    while (queryResultIterator.hasNext()) {
      QueryResult queryResultEntry = queryResultIterator.next();
      // Get web entity for that Id
      if (queryResultEntry.title == null) {
        continue;
      }
      String wikimediaId = queryResultEntry.title;
      WbEntity wbEntity = apiClient.getWebEntity(wikimediaId, article.getCountry());
      try {
        logger.debug("Parsing entity " + wikimediaId);
        SearchTarget[] targets = new SearchTarget[] { search.getTarget() };
        if (parser.parseWbEntity(wbEntity, wikimediaId, article, targets)) {
          logger.debug("Entity " + wikimediaId + " returned a valid metadata");
          return true;
        } else {
          logger.debug("Entity " + wikimediaId + " is not interesting (didn't return any metadata)");
          continue;
        }
      } catch (ParserException e) { // Don't abort due to parser exception
        logger.error("Failed to parse entity " + wikimediaId + ": " + e.getMessage());
        continue;
      }
    }
    // Doesn't return anything important; blacklist search
    logger.info("Search '" + search.getSearch() + "' doesn't return anything interesting. Blacklisting it");
    blacklistSearch(search, language);
    return false;
  }

}
