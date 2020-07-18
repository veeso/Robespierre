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

public class ClauseOperatorTest {
  
  @Test
  public void shouldBeCorrectStringValue() {
    assertEquals(ClauseOperator.BIGGER_EQUAL.toString(), ">=");
    assertEquals(ClauseOperator.BIGGER_THAN.toString(), ">");
    assertEquals(ClauseOperator.EQUAL.toString(), "=");
    assertEquals(ClauseOperator.LESS_EQUAL.toString(), "<=");
    assertEquals(ClauseOperator.LESS_THAN.toString(), "<");
    assertEquals(ClauseOperator.LIKE.toString(), "LIKE");
    assertEquals(ClauseOperator.NOT_EQUAL.toString(), "!=");
  }

}
