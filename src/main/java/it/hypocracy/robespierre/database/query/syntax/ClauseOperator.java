/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
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
