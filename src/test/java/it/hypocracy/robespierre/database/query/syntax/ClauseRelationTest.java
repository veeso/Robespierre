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

public class ClauseRelationTest {

  @Test
  public void shouldReturnCorrectString() {
    assertEquals("AND", ClauseRelation.AND.toString());
    assertEquals("OR", ClauseRelation.OR.toString());
  }
  
}
