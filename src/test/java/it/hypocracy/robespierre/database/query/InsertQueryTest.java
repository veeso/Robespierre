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

}
