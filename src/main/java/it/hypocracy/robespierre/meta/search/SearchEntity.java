/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.meta.search;

/**
 * SearchEntity describes a single search to make, its target and the search type
 */

public class SearchEntity {

  private String search;
  private SearchTarget target;

  public SearchEntity(String search, SearchTarget target) {
    this.search = search;
    this.target = target;
  }

  /**
   * <p>
   * Return search text
   * </p>
   * 
   * @return string
   */

  public String getSearch() {
    return this.search;
  }

  /**
   * <p>
   * Return search target
   * </p>
   * @return SearchTarget
   */

  public SearchTarget getTarget() {
    return this.target;
  }

}
