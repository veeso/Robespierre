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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.hypocracy.robespierre.database.query.DeleteQuery;
import it.hypocracy.robespierre.database.query.InsertQuery;
import it.hypocracy.robespierre.database.query.SelectQuery;
import it.hypocracy.robespierre.database.query.UpdateQuery;

public class MariaFacade implements DatabaseFacade {

  // Configuration
  private String url;
  private String username;
  private String password;
  // Runtime
  Connection conn;

  /**
   * 
   * @param url
   * @param username
   * @param password
   */

  public MariaFacade(String url, String username, String password) {
    this.conn = null;
    this.url = url;
    this.username = username;
    this.password = password;
  }

  @Override
  public void connect() throws SQLException {
    if (conn == null) {
      conn = DriverManager.getConnection(url, username, password);
      conn.setAutoCommit(false); // Disable auto commit
    } else {
      throw new SQLException("Connection with database already established");
    }
  }

  @Override
  public void disconnect() throws SQLException {
    if (conn != null) {
      if (!conn.isClosed()) {
        conn.close();
      } else {
        conn = null;
        throw new SQLException("Connection with database already closed");
      }
    } else {
      throw new SQLException("Connection with database already closed");
    }
    conn = null;
  }

  @Override
  public void commit() throws SQLException {
    if (conn == null) {
      throw new SQLException("Could not commit: no statement to commit");
    }
    conn.commit();
  }

  @Override
  public void rollback() throws SQLException {
    if (conn == null) {
      throw new SQLException("Could not rollback: no statement to commit");
    }
    conn.rollback();
  }

  @Override
  public void delete(DeleteQuery query) throws SQLException {
    Statement stmt = createStatement();
    stmt.execute(query.toSQL());
    stmt.close();
  }

  @Override
  public void insert(InsertQuery query) throws SQLException {
    Statement stmt = createStatement();
    stmt.execute(query.toSQL());
    stmt.close();
  }

  @Override
  public ArrayList<Map<String, String>> select(SelectQuery query) throws SQLException {
    Statement stmt = createStatement();
    // Perform select
    ResultSet set = stmt.executeQuery(query.toSQL());
    // Prepare result hashmap
    ArrayList<Map<String, String>> rows = new ArrayList<Map<String, String>>();
    while (set.next()) {
      HashMap<String, String> row = new HashMap<String, String>();
      ResultSetMetaData rsmd = set.getMetaData();
      int columnCount = rsmd.getColumnCount();
      for (int i = 1; i <= columnCount; i++) {
        String column = rsmd.getColumnName(i);
        row.put(column, set.getString(i));
      }
      // Push row to result
      rows.add(row);
    }
    set.close();
    stmt.close();
    return rows;
  }

  @Override
  public void update(UpdateQuery query) throws SQLException {
    Statement stmt = createStatement();
    stmt.execute(query.toSQL());
    stmt.close();
  }

  @Override
  public void performFreeform(String query) throws SQLException {
    Statement stmt = createStatement();
    stmt.execute(query);
    stmt.close();
  }

  /**
   * <p>
   * Create statement
   * </p>
   * 
   * @throws SQLException
   */

  private Statement createStatement() throws SQLException {
    if (conn == null) {
      throw new SQLException("Connection with database not established yet");
    }
    return conn.createStatement();
  }

}
