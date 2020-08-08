/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.database.driver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import it.hypocracy.robespierre.database.query.DeleteQuery;
import it.hypocracy.robespierre.database.query.InsertQuery;
import it.hypocracy.robespierre.database.query.SelectQuery;
import it.hypocracy.robespierre.database.query.UpdateQuery;
import it.hypocracy.robespierre.database.query.syntax.Clause;
import it.hypocracy.robespierre.database.query.syntax.ClauseOperator;

public class MariaFacadeTest {

  // Queries
  private static String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS maria_test;";
  private static String useDatabaseQuery = "USE maria_test;";
  private static String createTableQuery = "CREATE TABLE IF NOT EXISTS user (id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,email VARCHAR(64) NOT NULL,username VARCHAR(32) NOT NULL,password CHAR(64) NOT NULL);";
  private static String dropTableQuery = "DROP TABLE user;";
  private static String dropDatabaseQuery = "DROP DATABASE maria_test;";

  private static String baseUrl = "jdbc:mariadb://localhost:3306/";
  private static String databaseName = "maria_test";
  private static String dbUrl = baseUrl + databaseName;

  // @! Run first

  @BeforeClass
  public static void shouldInitializeDatabase() throws SQLException {
    MariaFacade db = new MariaFacade(baseUrl, "root", null);
    // Open database
    db.connect();
    // Create database
    db.performFreeform(createDatabaseQuery);
    db.performFreeform(useDatabaseQuery);
    db.performFreeform(createTableQuery);
    // Commit
    db.commit();
    db.disconnect();
  }

  @Test
  public void shouldInsertRows() throws SQLException {
    MariaFacade db = new MariaFacade(dbUrl, "root", null);
    // Open database
    db.connect();
    // Prepare query 1
    String columns[] = new String[3];
    columns[0] = "email";
    columns[1] = "username";
    columns[2] = "password";
    String values[] = new String[3];
    values[0] = "\"foobar@gmail.com\"";
    values[1] = "\"foobar\"";
    values[2] = "\"c6803282ef40a056b6d9240158c5d8bf4c3054139d47bcd8d3be4d1ab4679b22\"";
    InsertQuery query = new InsertQuery("user", columns, values);
    db.insert(query);
    // Prepare query 2
    String values2[] = new String[3];
    values2[0] = "\"omar@gmail.com\"";
    values2[1] = "\"omar\"";
    values2[2] = "\"52a38b1bdfbf93a345d206cc701b94ce0ed1b1e683978aaa2c4e63676d868970\"";
    InsertQuery query2 = new InsertQuery("user", columns, values2);
    db.insert(query2);
    // Commit
    db.commit();
    db.disconnect();
  }

  @Test
  public void shouldSelectRows() throws SQLException {
    MariaFacade db = new MariaFacade(dbUrl, "root", null);
    // Open database
    db.connect();
    // Select rows
    SelectQuery query = new SelectQuery("user");
    ArrayList<Map<String, String>> rows = db.select(query);
    // There should be 2 rows
    assertEquals(rows.size(), 2);
    // Iterate over rows
    Iterator<Map<String, String>> rowsIt = rows.iterator();
    int rowIndex = 0;
    while (rowsIt.hasNext()) {
      Map<String, String> row = rowsIt.next();
      // Verify columns
      if (rowIndex == 0) {
        assertEquals("foobar@gmail.com", row.get("email"));
        assertEquals("foobar", row.get("username"));
        assertEquals("c6803282ef40a056b6d9240158c5d8bf4c3054139d47bcd8d3be4d1ab4679b22", row.get("password"));
      } else if (rowIndex == 1) {
        assertEquals("omar@gmail.com", row.get("email"));
        assertEquals("omar", row.get("username"));
        assertEquals("52a38b1bdfbf93a345d206cc701b94ce0ed1b1e683978aaa2c4e63676d868970", row.get("password"));
      }
      rowIndex++;
    }
    db.disconnect();
  }

  @Test
  public void shouldUpdateRow() throws SQLException {
    MariaFacade db = new MariaFacade(dbUrl, "root", null);
    // Open database
    db.connect();
    // Update user
    Clause where = new Clause("username", "\"omar\"", ClauseOperator.EQUAL);
    HashMap<String, String> fields = new HashMap<>();
    fields.put("password", "\"deca22eaaf1951e955023b1ca34c8776db3e63022685fc5e0498417413ecaec7\"");
    UpdateQuery query = new UpdateQuery("user", fields, where);
    db.update(query);
    // Commit
    db.commit();
    db.disconnect();
  }

  @Test
  public void shouldDeleteRow() throws SQLException {
    MariaFacade db = new MariaFacade(dbUrl, "root", null);
    // Open database
    db.connect();
    Clause where = new Clause("username", "\"omar\"", ClauseOperator.EQUAL);
    DeleteQuery query = new DeleteQuery("user", where);
    db.delete(query);
    // Commit
    db.commit();
    db.disconnect();
  }

  @Test
  public void shouldRollbackStatement() throws SQLException {
    MariaFacade db = new MariaFacade(dbUrl, "root", null);
    // Open database
    db.connect();
    String columns[] = new String[3];
    columns[0] = "email";
    columns[1] = "username";
    columns[2] = "password";
    String values[] = new String[3];
    values[0] = "\"foobar@gmail.com\"";
    values[1] = "\"pippo\"";
    values[2] = "\"c6803282ef40a056b6d9240158c5d8bf4c3054139d47bcd8d3be4d1ab4679b22\"";
    InsertQuery query = new InsertQuery("user", columns, values);
    db.insert(query);
    // Rollback
    db.rollback();
    // Select and verify user still exists
    SelectQuery sQuery = new SelectQuery("user");
    ArrayList<Map<String, String>> rows = db.select(sQuery);
    // Disconnect
    db.disconnect();
    // There should be max 2 rows (depends on execution)
    assertTrue(rows.size() <= 2);
  }

  // @! Run as final!

  @AfterClass
  public static void shouldDropTable() throws SQLException {
    MariaFacade db = new MariaFacade(dbUrl, "root", null);
    // Open database
    db.connect();
    // Drop
    db.performFreeform(dropTableQuery);
    // Commit
    db.commit();
    db.disconnect();
    // Drop database
    db = new MariaFacade(baseUrl, "root", null);
    // Open database
    db.connect();
    // Drop
    db.performFreeform(dropDatabaseQuery);
    // Commit
    db.commit();
    db.disconnect();
  }

}
