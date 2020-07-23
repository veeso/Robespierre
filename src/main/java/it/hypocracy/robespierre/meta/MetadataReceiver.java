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

public interface MetadataReceiver {
  
  /**
   * <p>
   * Fetch metadata for a certain article querying the Metadata provider
   * </p>
   * @param article
   */

  public void fetchMetadata(Article article);

}
