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

import java.util.Iterator;
import java.util.Map;

import it.hypocracy.robespierre.database.query.syntax.Clause;

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
