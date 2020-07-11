/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.article;

public class Category {
  
  private String name;

  /**
   * <p>
   * Instantiates a new Category
   * </p>
   * 
   * @param name
   */

  public Category(String name) {
    this.name = name;
  }

  /**
   * <p>
   * Returns category name
   * </p>
   * 
   * @return name
   */

  public String getName() {
    return this.name;
  }

}
