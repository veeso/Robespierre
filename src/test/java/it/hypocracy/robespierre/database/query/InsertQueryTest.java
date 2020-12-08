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

import org.junit.Test;

public class InsertQueryTest {

  @Test
  public void shouldBuildSimpleInsertQuery() {
    String[] values = new String[4];
    values[0] = "\"admin\"";
    values[1] = "\"password1\"";
    values[2] = "3";
    values[3] = "True";
    InsertQuery query = new InsertQuery("user", values);
    assertEquals("INSERT INTO user VALUES (\"admin\",\"password1\",3,True);", query.toSQL());
  }

  @Test
  public void shouldBuildDeleteQueryWithColumns() {
    String[] values = new String[2];
    values[0] = "\"admin\"";
    values[1] = "\"password1\"";
    String[] columns = new String[2];
    columns[0] = "username";
    columns[1] = "password";
    InsertQuery query = new InsertQuery("user", columns, values);
    assertEquals("INSERT INTO user (username,password) VALUES (\"admin\",\"password1\");", query.toSQL());
  }

  @Test
  public void shouldBuildDeleteQueryWithColumnsAndNullValues() {
    String[] values = new String[2];
    values[0] = "\"admin\"";
    values[1] = null;
    String[] columns = new String[2];
    columns[0] = "username";
    columns[1] = "password";
    InsertQuery query = new InsertQuery("user", columns, values);
    assertEquals("INSERT INTO user (username) VALUES (\"admin\");", query.toSQL());
  }

}
