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

package it.hypocracy.robespierre.database.query;

import it.hypocracy.robespierre.database.query.syntax.Clause;
import it.hypocracy.robespierre.database.query.syntax.Order;

/**
 * SelectQuery describes the Select Query in SQL databases
 */

public class SelectQuery implements Query {

  private String[] columns;
  private String[] tables;
  private Clause conditions;
  private Order orderBy;
  private Integer limit;

  /**
   * <p>
   * Create a select all from a table
   * </p>
   * @param table
   */

  public SelectQuery(String table) {
    this(new String[]{table});
  }

  /**
   * <p>
   * Create a select all from tables query
   * </p>
   * 
   * @param tables
   */

  public SelectQuery(String[] tables) {
    this.tables = tables;
    this.columns = null;
    this.conditions = null;
    this.orderBy = null;
    this.limit = null;
  }

  /**
   * <p>
   * Create a select some columns from some tables query
   * </p>
   * 
   * @param columns
   * @param tables
   */

  public SelectQuery(String[] columns, String[] tables) {
    this(tables);
    this.columns = columns;
  }

  /**
   * 
   * @param columns
   * @param tables
   * @param conditions
   */

  public SelectQuery(String[] columns, String[] tables, Clause conditions) {
    this(columns, tables);
    this.conditions = conditions;
  }

  /**
   * 
   * @param columns
   * @param tables
   * @param conditions
   * @param orderBy
   */

  public SelectQuery(String[] columns, String[] tables, Clause conditions, Order orderBy) {
    this(columns, tables, conditions);
    this.orderBy = orderBy;
  }

  /**
   * 
   * @param columns
   * @param tables
   * @param conditions
   * @param orderBy
   * @param limit
   */

  public SelectQuery(String[] columns, String[] tables, Clause conditions, Order orderBy, int limit) {
    this(columns, tables, conditions, orderBy);
    this.limit = limit;
  }

  @Override
  public String toSQL() {
    StringBuilder queryStream = new StringBuilder();
    // Initialize query
    queryStream.append("SELECT ");
    // If columns is null, select *
    if (columns == null) {
      queryStream.append("*");
    } else {
      // Iterate over columns
      for (String field : columns) {
        queryStream.append(field);
        queryStream.append(",");
      }
      // Remove last comma
      queryStream.deleteCharAt(queryStream.length() - 1);
    }
    // FROM
    queryStream.append(" FROM ");
    // Iterate over tables
    for (String table : tables) {
      queryStream.append(table);
      queryStream.append(",");
    }
    // Remove last comma
    queryStream.deleteCharAt(queryStream.length() - 1);
    // Check if there is a WHERE clause
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

    // Order
    if (orderBy != null) {
      queryStream.append(" ORDER BY ");
      for (String what : orderBy.getWhat()) {
        queryStream.append(what);
        queryStream.append(",");
      }
      // Remove last comma
      queryStream.deleteCharAt(queryStream.length() - 1);
      // Add order type
      queryStream.append(" ");
      queryStream.append(orderBy.getType().toString());
    }

    // Limit
    if (limit != null) {
      queryStream.append(" LIMIT ");
      queryStream.append(limit);
    }

    // Terminate query and return
    queryStream.append(";");
    return queryStream.toString();
  }

}
