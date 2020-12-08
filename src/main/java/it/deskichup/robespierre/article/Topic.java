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

package it.deskichup.robespierre.article;

import it.deskichup.robespierre.utils.Uuidv4;

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
