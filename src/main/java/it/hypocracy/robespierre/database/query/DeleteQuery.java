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

import it.hypocracy.robespierre.database.query.syntax.Clause;

/**
 * This class describes the object representing a Delete Query
 */

public class DeleteQuery implements Query {

  private String table;
  private Clause conditions;

  /**
   * 
   * @param table
   */

  public DeleteQuery(String table) {
    this.table = table;
    this.conditions = null;
  }

  /**
   * 
   * @param table
   * @param conditions
   */

  public DeleteQuery(String table, Clause where) {
    this(table);
    this.conditions = where;
  }

  @Override
  public String toSQL() {
    StringBuilder queryStream = new StringBuilder();
    // Delete from
    queryStream.append("DELETE FROM ");
    // Append table name
    queryStream.append(table);
    // Add where if required
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
