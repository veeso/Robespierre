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

package it.deskichup.robespierre.database.query;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

import it.deskichup.robespierre.database.query.syntax.Clause;
import it.deskichup.robespierre.database.query.syntax.ClauseOperator;

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

  @Test
  public void shouldBuildDeleteQueryWithColumnsAndNullValues() {
    HashMap<String, String> columns = new HashMap<String, String>();
    columns.put("role", "\"administrator\"");
    columns.put("username", null);
    Clause clause = new Clause("username", "\"pippo\"", ClauseOperator.EQUAL);
    UpdateQuery query = new UpdateQuery("user", columns, clause);
    assertEquals("UPDATE user SET role = \"administrator\" WHERE username = \"pippo\";", query.toSQL());
  }

}
