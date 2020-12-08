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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import it.hypocracy.robespierre.database.query.syntax.Clause;
import it.hypocracy.robespierre.database.query.syntax.ClauseOperator;
import it.hypocracy.robespierre.database.query.syntax.ClauseRelation;

public class DeleteQueryTest {

  @Test
  public void shouldBuildSimpleDeleteQuery() {
    DeleteQuery query = new DeleteQuery("user");
    assertEquals("DELETE FROM user;", query.toSQL());
  }

  @Test
  public void shouldBuildDeleteQueryWithClause() {
    Clause clause = new Clause("role", "\"banned\"", ClauseOperator.EQUAL);
    assertNull(clause.getNext());
    clause.setNext(new Clause("username", "\"pizza88\"", ClauseOperator.EQUAL), ClauseRelation.OR);
    DeleteQuery query = new DeleteQuery("user", clause);
    assertEquals("DELETE FROM user WHERE role = \"banned\" OR username = \"pizza88\";", query.toSQL());
  }

}
