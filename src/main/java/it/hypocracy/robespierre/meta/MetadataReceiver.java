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
import it.hypocracy.robespierre.meta.exceptions.CacheException;
import it.hypocracy.robespierre.meta.exceptions.MetadataReceiverException;
import it.hypocracy.robespierre.meta.exceptions.ParserException;

public interface MetadataReceiver {
  
  /**
   * <p>
   * Fetch metadata for a certain article querying the Metadata provider
   * </p>
   * @param article
   * @throws MetadataReceiverException
   * @throws CacheException
   * @throws ParserException
   */

  public void fetchMetadata(Article article) throws MetadataReceiverException, CacheException, ParserException;

}
