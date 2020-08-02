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

import it.hypocracy.robespierre.database.query.syntax.Clause;
import it.hypocracy.robespierre.database.query.syntax.ClauseOperator;
import it.hypocracy.robespierre.database.query.syntax.ClauseRelation;
import it.hypocracy.robespierre.database.query.syntax.Order;
import it.hypocracy.robespierre.database.query.syntax.OrderType;

public class SelectQueryTest {
  
  @Test
  public void shouldBuildSelectAllQuery() {
    String[] tables = new String[1];
    tables[0] = "user";
    SelectQuery query = new SelectQuery(tables);
    //Verify string
    assertEquals("SELECT * FROM user;", query.toSQL());
  }

  @Test
  public void shouldBuildSelectAllFromMultipleTablesQuery() {
    String[] tables = new String[2];
    tables[0] = "user";
    tables[1] = "user_group";
    SelectQuery query = new SelectQuery(tables);
    //Verify string
    assertEquals("SELECT * FROM user,user_group;", query.toSQL());
  }

  @Test
  public void shouldBuildSelectWhatQuery() {
    String[] tables = new String[1];
    tables[0] = "user";
    String[] columns = new String[2];
    columns[0] = "username";
    columns[1] = "password";
    SelectQuery query = new SelectQuery(columns, tables);
    assertEquals("SELECT username,password FROM user;", query.toSQL());
  }

  @Test
  public void shouldBuildSelectWhatWhereQuery() {
    //Prepare where clause
    Clause clause = new Clause("role", "\"administrator\"", ClauseOperator.EQUAL);
    assertEquals(clause.getNext(), null);
    clause.setNext(new Clause("disabled", "\"TRUE\"", ClauseOperator.NOT_EQUAL), ClauseRelation.AND);
    String[] tables = new String[1];
    tables[0] = "user";
    String[] columns = new String[2];
    columns[0] = "username";
    columns[1] = "password";
    SelectQuery query = new SelectQuery(columns, tables, clause);
    assertEquals("SELECT username,password FROM user WHERE role = \"administrator\" AND disabled != \"TRUE\";", query.toSQL());
  }

  @Test
  public void shouldBuildSelectWhatWhereOrderQuery() {
    //Prepare where clause
    Clause clause = new Clause("role", "\"administrator\"", ClauseOperator.EQUAL);
    assertEquals(clause.getNext(), null);
    clause.setNext(new Clause("disabled", "\"TRUE\"", ClauseOperator.NOT_EQUAL), ClauseRelation.AND);
    String[] tables = new String[1];
    tables[0] = "user";
    String[] columns = new String[2];
    columns[0] = "username";
    columns[1] = "password";
    String[] orderColumns = new String[2];
    orderColumns[0] = "registration_date";
    orderColumns[1] = "last_activity";
    SelectQuery query = new SelectQuery(columns, tables, clause, new Order(orderColumns, OrderType.DESC));
    assertEquals("SELECT username,password FROM user WHERE role = \"administrator\" AND disabled != \"TRUE\" ORDER BY registration_date,last_activity DESC;", query.toSQL());
  }

  @Test
  public void shouldBuildSelectWhatWhereOrderLimitQuery() {
    //Prepare where clause
    Clause clause = new Clause("role", "\"administrator\"", ClauseOperator.EQUAL);
    assertEquals(clause.getNext(), null);
    clause.setNext(new Clause("disabled", "\"TRUE\"", ClauseOperator.NOT_EQUAL), ClauseRelation.AND);
    String[] tables = new String[1];
    tables[0] = "user";
    String[] columns = new String[2];
    columns[0] = "username";
    columns[1] = "password";
    String[] orderColumns = new String[2];
    orderColumns[0] = "registration_date";
    orderColumns[1] = "last_activity";
    SelectQuery query = new SelectQuery(columns, tables, clause, new Order(orderColumns, OrderType.DESC), 8);
    assertEquals("SELECT username,password FROM user WHERE role = \"administrator\" AND disabled != \"TRUE\" ORDER BY registration_date,last_activity DESC LIMIT 8;", query.toSQL());
  }

  @Test
  public void shoudlBuildSelectWhatOrderLimitQuery() {
    String[] tables = new String[1];
    tables[0] = "user";
    String[] columns = new String[2];
    columns[0] = "username";
    columns[1] = "password";
    String[] orderColumns = new String[2];
    orderColumns[0] = "registration_date";
    orderColumns[1] = "last_activity";
    SelectQuery query = new SelectQuery(columns, tables, null, new Order(orderColumns, OrderType.DESC), 8);
    assertEquals("SELECT username,password FROM user ORDER BY registration_date,last_activity DESC LIMIT 8;", query.toSQL());
  }

}
