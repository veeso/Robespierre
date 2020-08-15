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

import it.hypocracy.robespierre.utils.Uuidv4;

/**
 * Occupation describes a subject (human) occupation
 */

public class Occupation {
  
  private String id;
  private String name;

  /**
   * <p>
   * Instantiates a new Category
   * </p>
   * 
   * @param id
   * @param name
   */

  public Occupation(String id, String name) {
    this.id = id;
    this.name = name;
  }

  /**
   * <p>
   * Instantiates a new Category
   * </p>
   * 
   * @param name
   */

  public Occupation(String name) {
    this(null, name);
    // Generate UUID
    Uuidv4 uuid = new Uuidv4();
    this.id = uuid.getUUIDv4();
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

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

}
