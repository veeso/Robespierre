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

import java.util.HashMap;

import org.junit.Test;

import it.hypocracy.robespierre.database.query.syntax.Clause;
import it.hypocracy.robespierre.database.query.syntax.ClauseOperator;

public class UpdateQueryTest {

  @Test
  public void shouldBuildSimpleUpdateQuery() {
    HashMap<String, String> columns = new HashMap<String, String>();
    columns.put("role", "\"administrator\"");
    columns.put("username", "\"omar\"");
    UpdateQuery query = new UpdateQuery("user", columns);
    assertEquals("UPDATE user SET role = \"administrator\",username = \"omar\";", query.toSQL());
  }

  @Test
  public void shouldBuildDeleteQueryWithColumns() {
    HashMap<String, String> columns = new HashMap<String, String>();
    columns.put("role", "\"administrator\"");
    columns.put("username", "\"omar\"");
    Clause clause = new Clause("username", "\"pippo\"", ClauseOperator.EQUAL);
    UpdateQuery query = new UpdateQuery("user", columns, clause);
    assertEquals("UPDATE user SET role = \"administrator\",username = \"omar\" WHERE username = \"pippo\";", query.toSQL());
  }

}
