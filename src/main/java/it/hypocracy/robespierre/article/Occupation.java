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
