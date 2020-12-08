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
