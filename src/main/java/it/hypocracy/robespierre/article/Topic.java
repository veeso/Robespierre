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
 * Topic represents the topic associated to an Article
 */

public class Topic {

  private String id;
  private String name;
  private String description;

  /**
   * <p>
   * Instantiates a new topic. The id is auto generated
   * </p>
   * 
   * @param name        topic name
   * @param description topic description
   */

  public Topic(String name, String description) {
    this(new Uuidv4().getUUIDv4(), name, description);
  }

  /**
   * <p>
   * Instantiates a new Topic
   * </p>
   * 
   * @param id          topic id
   * @param name        topic name
   * @param description topic description
   */

  public Topic(String id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  /**
   * <p>
   * Set topic id
   * </p>
   * 
   * @param id
   */

  public void setId(String id) {
    this.id = id;
  }

  /**
   * <p>
   * Returns the topic id
   * </p>
   * 
   * @return id
   */

  public String getId() {
    return this.id;
  }

  /**
   * <p>
   * Returns the topic name
   * </p>
   * 
   * @return name
   */

  public String getName() {
    return this.name;
  }

  /**
   * <p>
   * Returns the topic description
   * </p>
   * 
   * @return description
   */

  public String getDescription() {
    return this.description;
  }

}
