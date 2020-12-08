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

package it.hypocracy.robespierre.core;

import org.apache.log4j.Logger;

import it.hypocracy.robespierre.article.Article;
import it.hypocracy.robespierre.feed.Feed;
import it.hypocracy.robespierre.meta.MetadataReceiver;
import it.hypocracy.robespierre.meta.exceptions.MetadataReceiverException;

/**
 * The ArticleAssembler is the entity which takes care of Wrapping an 
 * article starting from a received feed and to get the metadata for it
 */

public class ArticleAssembler {

  private final static Logger logger = Logger.getLogger(ArticleAssembler.class.getName());

  private MetadataReceiver metadataReceiver;

  /**
   * 
   * @param metadataReceiver
   */

  public ArticleAssembler(MetadataReceiver metadataReceiver) {
    this.metadataReceiver = metadataReceiver;
  }

  /**
   * <p>
   * Assemble article starting from feed
   * </p>
   * 
   * @param feed
   * @return Article
   * @throws ParserException
   */

  public Article assemble(Feed feed) {
    // Create article
    Article article = new Article(feed.getTitle(), feed.getBrief(), feed.getLink(), feed.getPublicationDatetime(),
        feed.getCountry());
    // Use metadata receiver to fetch for subjects and topics
    logger.debug("Fetiching metadata");
    try {
      metadataReceiver.fetchMetadata(article);
    } catch (MetadataReceiverException e) {
      logger.error("Metadata Receiver error: " + e.getMessage());
      logger.error("Article will be saved anyway, but without metadata...");
      logger.trace(e);
    }
    logger.debug("Metadata fetched");
    return article;
  }

}
