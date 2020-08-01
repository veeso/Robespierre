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

public class SearchEntity {

  private String search;

  public SearchEntity(String search) {
    this.search = search;
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

}
