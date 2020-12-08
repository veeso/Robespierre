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
