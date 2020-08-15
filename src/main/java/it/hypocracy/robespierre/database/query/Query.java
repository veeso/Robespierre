/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.database.query;

/**
 * Query is an interface which must be implemented by all the SQL query classes
 * and provides the method `toSQL` which returns the SQL query from the instance
 */

public interface Query {
  
  /**
   * <p>
   * Convert Query to SQL string query
   * </p>
   * @return String
   */

  public String toSQL();

}
