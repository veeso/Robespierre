/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.database.query.syntax;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ClauseTest {
  
  @Test
  public void shouldInstantiateClause() {
    Clause clause = new Clause("name", "\"omar\"", ClauseOperator.EQUAL);
    assertEquals(clause.getLvalue(), "name");
    assertEquals(clause.getRvalue(), "\"omar\"");
    assertEquals(clause.getOperator().toString(), "=");
    assertEquals(clause.getNext(), null);
    assertEquals(clause.getNextRelation(), null);
  }

  @Test
  public void shouldAddClauseToNext() {
    Clause clause = new Clause("name", "\"omar\"", ClauseOperator.EQUAL);
    assertEquals(clause.getNext(), null);
    Clause secondClause = new Clause("surname", "\"thickchest\"", ClauseOperator.EQUAL);
    clause.setNext(secondClause, ClauseRelation.AND);
    assertEquals(clause.getNext(), secondClause);
    assertEquals(clause.getNextRelation(), ClauseRelation.AND);
    assertEquals(secondClause.getNext(), null);
  }

}