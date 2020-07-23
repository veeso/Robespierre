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

import java.io.IOException;

import com.google.gson.JsonSyntaxException;

import it.hypocracy.robespierre.article.Article;
import it.hypocracy.robespierre.http.HTTPFacade;
import it.hypocracy.robespierre.meta.MetadataReceiver;

public class WikiDataReceiver implements MetadataReceiver {

  private HTTPFacade httpcli;

  /**
   * <p>
   * WikiDataReceiver constructor
   * </p>
   */
  
  public WikiDataReceiver() {
    this.httpcli = new HTTPFacade();
  }

  @Override
  public void fetchMetadata(Article article) throws IOException, JsonSyntaxException {
    // TODO Auto-generated method stub

  }
  
}
