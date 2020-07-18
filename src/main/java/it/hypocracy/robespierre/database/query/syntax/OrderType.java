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

public enum OrderType {
  ASC, DESC;

  /**
   * <p>
   * Convert order to String
   * </p>
   * 
   * @return String
   */

  public String toString() {
    switch (this) {
      case ASC:
        return "ASC";
      case DESC:
        return "DESC";
    }
    return null;
  }

}
