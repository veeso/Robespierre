/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * MIT License
 * 
 * Copyright (c) 2020 Christian Visintin
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
