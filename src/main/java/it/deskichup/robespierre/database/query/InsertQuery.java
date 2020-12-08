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

package it.deskichup.robespierre.database.query;

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
