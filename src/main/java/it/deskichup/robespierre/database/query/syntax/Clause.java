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

package it.deskichup.robespierre.database.query.syntax;

/**
 * This class describes a clause (where statement) for select and update operation
 */

public class Clause {
  
  private String lvalue; //Left value
  private String rvalue; //Right value
  private ClauseOperator operator; //Operator between
  private ClauseRelation nextRelation; //Relation with next
  private Clause next; //Pointer to next clause

  /**
   * @param lvalue
   * @param rvalue
   * @param operator
   */

  public Clause(String lvalue, String rvalue, ClauseOperator operator) {
    this.lvalue = lvalue;
    this.rvalue = rvalue;
    this.operator = operator;
    this.next = null;
    this.nextRelation = null;
  }

  /**
   * <p>
   * Set next member in clause
   * </p>
   * @param next
   * @param relation
   */

  public void setNext(Clause next, ClauseRelation relation) {
    this.next = next;
    this.nextRelation = relation;
  }

  //@! Getters

  public String getLvalue() {
    return lvalue;
  }

  public String getRvalue() {
    return rvalue;
  }

  public ClauseOperator getOperator() {
    return operator;
  }

  public Clause getNext() {
    return next;
  }

  public ClauseRelation getNextRelation() {
    return nextRelation;
  }

}
