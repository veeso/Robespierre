/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.database.query.syntax;

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
