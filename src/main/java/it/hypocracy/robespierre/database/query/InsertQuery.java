/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 *
 */

package it.hypocracy.robespierre.database.query;

/**
 * InsertQuery describes a Insert query in SQL database
 */

public class InsertQuery implements Query {

  private String table;
  private String[] columns;
  private String[] values;

  /**
   * 
   * @param table
   * @param values
   */

  public InsertQuery(String table, String[] values) {
    this.table = table;
    this.values = values;
    this.columns = null;
  }

  /**
   * 
   * @param table
   * @param columns
   * @param values
   */

  public InsertQuery(String table, String[] columns, String[] values) {
    this(table, values);
    this.columns = columns;
  }

  @Override
  public String toSQL() {
    StringBuilder queryStream = new StringBuilder();
    // Insert into
    queryStream.append("INSERT INTO ");
    // Table
    queryStream.append(table);
    // If columns is set, set columns
    int colIdx = 0;
    if (columns != null) {
      // Open columns
      queryStream.append(" (");
      // Iterate over columns
      for (String col : columns) {
        if (values[colIdx++] != null) { // Ignore null values
          queryStream.append(col);
          queryStream.append(",");
        }
      }
      // Remove last comma
      queryStream.deleteCharAt(queryStream.length() - 1);
      // Close columns
      queryStream.append(")");
    }
    // Values
    queryStream.append(" VALUES (");
    for (String value : values) {
      if (value != null) { // Ignore null values
        queryStream.append(value);
        queryStream.append(",");
      }
    }
    // Remove last comma
    queryStream.deleteCharAt(queryStream.length() - 1);
    // Close values
    queryStream.append(")");
    // Terminate query and return
    queryStream.append(";");
    return queryStream.toString();
  }

}
