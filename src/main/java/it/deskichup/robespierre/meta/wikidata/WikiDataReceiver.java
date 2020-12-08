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

package it.deskichup.robespierre.meta.wikidata;

import java.util.Iterator;

import it.deskichup.robespierre.article.Article;
import it.deskichup.robespierre.meta.cache.CacheProvider;
import it.deskichup.robespierre.meta.MetadataReceiver;
import it.deskichup.robespierre.meta.exceptions.MetadataReceiverException;
import it.deskichup.robespierre.meta.exceptions.ParserException;
import it.deskichup.robespierre.meta.search.SearchBuilder;
import it.deskichup.robespierre.meta.search.SearchEntity;
import it.deskichup.robespierre.meta.search.SearchTarget;
import it.deskichup.robespierre.meta.wikidata.search.QueryResult;
import it.deskichup.robespierre.meta.wikidata.search.Search;
import it.deskichup.robespierre.meta.wikidata.wbentity.WbEntity;

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
