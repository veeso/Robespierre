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

import java.util.Iterator;
import java.util.Map;

import it.deskichup.robespierre.database.query.syntax.Clause;

/**
 * UpdateQuery in SQL databases
 */

public class UpdateQuery implements Query {

  private String table;
  private Map<String, String> columns;
  private Clause conditions;

  /**
   * 
   * @param table
   * @param columns (key: column name; value: column value)
   */

  public UpdateQuery(String table, Map<String, String> columns) {
    this.table = table;
    this.columns = columns;
    this.conditions = null;
  }

  /**
   * 
   * @param table
   * @param columns
   * @param where
   */

  public UpdateQuery(String table, Map<String, String> columns, Clause where) {
    this(table, columns);
    this.conditions = where;
  }

  @Override
  public String toSQL() {
    StringBuilder queryStream = new StringBuilder();
    // Initialize query
    queryStream.append("UPDATE ");
    // Table name
    queryStream.append(table);
    // Set
    queryStream.append(" SET ");
    // Iterate over fields
    Iterator<Map.Entry<String, String>> valuesIterator = columns.entrySet().iterator();
    while (valuesIterator.hasNext()) {
      Map.Entry<String, String> entry = valuesIterator.next();
      if (entry.getValue() != null) { // Ignore null values
        queryStream.append(entry.getKey());
        queryStream.append(" = ");
        queryStream.append(entry.getValue());
        queryStream.append(",");
      }
    }
    // Remove last comma
    queryStream.deleteCharAt(queryStream.length() - 1);
    // If WHERE, add conditions
    if (conditions != null) {
      queryStream.append(" WHERE ");
      Clause condition = conditions;
      // Add where clause
      do {
        // lvalue
        queryStream.append(condition.getLvalue());
        // Operator
        queryStream.append(" ");
        queryStream.append(condition.getOperator().toString());
        queryStream.append(" ");
        // rvalue
        queryStream.append(condition.getRvalue());
        if (condition.getNextRelation() != null) {
          queryStream.append(" ");
          queryStream.append(condition.getNextRelation().toString());
          queryStream.append(" ");
          // Go to next relation
        }
        condition = condition.getNext();
      } while (condition != null);
    }
    // Terminate query and return
    queryStream.append(";");
    return queryStream.toString();
  }

}
