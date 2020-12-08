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
