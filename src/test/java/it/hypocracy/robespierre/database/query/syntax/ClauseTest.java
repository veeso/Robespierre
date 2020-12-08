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
