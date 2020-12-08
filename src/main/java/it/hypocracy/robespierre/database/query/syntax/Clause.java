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
