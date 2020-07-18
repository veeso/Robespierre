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

import it.hypocracy.robespierre.database.query.syntax.Clause;

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
