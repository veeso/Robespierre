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
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ClauseTest {

  @Test
  public void shouldInstantiateClause() {
    Clause clause = new Clause("name", "\"omar\"", ClauseOperator.EQUAL);
    assertEquals("name", clause.getLvalue());
    assertEquals("\"omar\"", clause.getRvalue());
    assertEquals("=", clause.getOperator().toString());
    assertNull(clause.getNext());
    assertNull(clause.getNextRelation());
  }

  @Test
  public void shouldAddClauseToNext() {
    Clause clause = new Clause("name", "\"omar\"", ClauseOperator.EQUAL);
    assertNull(clause.getNext());
    Clause secondClause = new Clause("surname", "\"thickchest\"", ClauseOperator.EQUAL);
    clause.setNext(secondClause, ClauseRelation.AND);
    assertEquals(secondClause, clause.getNext());
    assertEquals(ClauseRelation.AND, clause.getNextRelation());
    assertNull(secondClause.getNext());
  }

}
