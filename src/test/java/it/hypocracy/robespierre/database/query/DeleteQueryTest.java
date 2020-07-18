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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import it.hypocracy.robespierre.database.query.syntax.Clause;
import it.hypocracy.robespierre.database.query.syntax.ClauseOperator;
import it.hypocracy.robespierre.database.query.syntax.ClauseRelation;

public class DeleteQueryTest {

  @Test
  public void shouldBuildSimpleDeleteQuery() {
    DeleteQuery query = new DeleteQuery("user");
    assertEquals(query.toSQL(), "DELETE FROM user;");
  }

  @Test
  public void shouldBuildDeleteQueryWithClause() {
    Clause clause = new Clause("role", "\"banned\"", ClauseOperator.EQUAL);
    assertEquals(clause.getNext(), null);
    clause.setNext(new Clause("username", "\"pizza88\"", ClauseOperator.EQUAL), ClauseRelation.OR);
    DeleteQuery query = new DeleteQuery("user", clause);
    assertEquals(query.toSQL(), "DELETE FROM user WHERE role = \"banned\" OR username = \"pizza88\";");
  }

}
