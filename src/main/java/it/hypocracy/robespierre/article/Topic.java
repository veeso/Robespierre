/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) 2020 Christian Visintin - christian.visintin1997@gmail.com
 *
 * This file is part of "Robespierre"
 *
 * Robespierre is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Robespierre is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Robespierre.  If not, see <http://www.gnu.org/licenses/>.
 *
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
  private String descriptionId;

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
    this(id, name, description, new Uuidv4().getUUIDv4());
  }

  /**
   * <p>
   * Instantiates a new Topic
   * </p>
   * 
   * @param id          topic id
   * @param name        topic name
   * @param description topic description
   * @param descriptionId
   */

  public Topic(String id, String name, String description, String descriptionId) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.descriptionId = descriptionId;
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

  public void setDescriptionId(String id) {
    this.descriptionId = id;
  }

  public String getDescriptionId() {
    return this.descriptionId;
  }

}
