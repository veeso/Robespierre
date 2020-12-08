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

package it.deskichup.robespierre.core;

import org.apache.log4j.Logger;

import it.deskichup.robespierre.article.Article;
import it.deskichup.robespierre.feed.Feed;
import it.deskichup.robespierre.meta.MetadataReceiver;
import it.deskichup.robespierre.meta.exceptions.MetadataReceiverException;

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
