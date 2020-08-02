/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.core;

import it.hypocracy.robespierre.article.Article;
import it.hypocracy.robespierre.feed.Feed;
import it.hypocracy.robespierre.meta.MetadataReceiver;
import it.hypocracy.robespierre.meta.exceptions.CacheException;
import it.hypocracy.robespierre.meta.exceptions.MetadataReceiverException;
import it.hypocracy.robespierre.meta.exceptions.ParserException;

public class ArticleAssembler {

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
   * @throws CacheException
   * @throws MetadataReceiverException
   */

  public Article assemble(Feed feed) throws MetadataReceiverException, CacheException, ParserException {
    // Create article
    Article article = new Article(feed.getTitle(), feed.getBrief(), feed.getLink(), feed.getPublicationDatetime(),
        feed.getCountry());
    // Use metadata receiver to fetch for subjects and topics
    metadataReceiver.fetchMetadata(article);
    return article;
  }

}
