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

package it.hypocracy.robespierre.database.query.syntax;

/**
 * This enum describes the operators for where statements
 */

public enum ClauseOperator {

  EQUAL, NOT_EQUAL, LIKE, BIGGER_THAN, BIGGER_EQUAL, LESS_THAN, LESS_EQUAL;

  /**
   * <p>
   * Convert operator to String
   * </p>
   * 
   * @return String
   */

  public String toString() {
    switch (this) {
      case EQUAL:
        return "=";
      case NOT_EQUAL:
        return "!=";
      case LIKE:
        return "LIKE";
      case BIGGER_EQUAL:
        return ">=";
      case BIGGER_THAN:
        return ">";
      case LESS_EQUAL:
        return "<=";
      case LESS_THAN:
        return "<";
    }
    return null;
  }

}
