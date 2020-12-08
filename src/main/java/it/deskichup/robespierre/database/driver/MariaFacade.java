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

package it.deskichup.robespierre.database.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import it.deskichup.robespierre.database.query.DeleteQuery;
import it.deskichup.robespierre.database.query.InsertQuery;
import it.deskichup.robespierre.database.query.SelectQuery;
import it.deskichup.robespierre.database.query.UpdateQuery;

public class MariaFacade implements DatabaseFacade {

  private final static Logger logger = Logger.getLogger(MariaFacade.class.getName());

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
      logger.info("Established connection with database " + username + ":" + url);
    } else {
      throw new SQLException("Connection with database already established");
    }
  }

  @Override
  public void disconnect() throws SQLException {
    if (conn != null) {
      if (!conn.isClosed()) {
        conn.close();
        logger.info("Closed connection with database");
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
    logger.info("Changes commited");
  }

  @Override
  public void rollback() throws SQLException {
    if (conn == null) {
      throw new SQLException("Could not rollback: no statement to commit");
    }
    conn.rollback();
    logger.warn("Changes rolled back");
  }

  @Override
  public void delete(DeleteQuery query) throws SQLException {
    Statement stmt = createStatement();
    logger.debug(query.toSQL());
    stmt.execute(query.toSQL());
    stmt.close();
  }

  @Override
  public void insert(InsertQuery query) throws SQLException {
    Statement stmt = createStatement();
    logger.debug(query.toSQL());
    stmt.execute(query.toSQL());
    stmt.close();
  }

  @Override
  public ArrayList<Map<String, String>> select(SelectQuery query) throws SQLException {
    Statement stmt = createStatement();
    // Perform select
    logger.debug(query.toSQL());
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
    logger.debug(query.toSQL());
    stmt.execute(query.toSQL());
    stmt.close();
  }

  @Override
  public void performFreeform(String query) throws SQLException {
    Statement stmt = createStatement();
    logger.debug(query);
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
