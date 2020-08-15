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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import it.hypocracy.robespierre.database.query.DeleteQuery;
import it.hypocracy.robespierre.database.query.InsertQuery;
import it.hypocracy.robespierre.database.query.SelectQuery;
import it.hypocracy.robespierre.database.query.UpdateQuery;

/**
 * The DatabaseFacade is an interface which must be implemented to implement a database
 * driver. The DatabaseFacade must implement a method to connect and disconnect to the database,
 * a method to commit data, and finally to select, insert and update data
 */

public interface DatabaseFacade {

  /**
   * <p>
   * Initialize and establish connection with the Database
   * </p>
   * 
   * @throws SQLException
   */

  public void connect() throws SQLException;

  /**
   * <p>
   * Close connection with database
   * </p>
   * 
   * @throws SQLException
   */

  public void disconnect() throws SQLException;

  /**
   * <p>
   * Commit changes to database
   * </p>
   * 
   * @throws SQLException
   */

  public void commit() throws SQLException;

  /**
   * <p>
   * Rollback changes to database
   * </p>
   * 
   * @throws SQLException
   */

  public void rollback() throws SQLException;

  /**
   * <p>
   * Perform delete query
   * </p>
   * 
   * @param query
   * @throws SQLException
   */

  public void delete(DeleteQuery query) throws SQLException;

  /**
   * <p>
   * Perform insert query
   * </p>
   * 
   * @param query
   * @throws SQLException
   */

  public void insert(InsertQuery query) throws SQLException;

  /**
   * <p>
   * Perform select query
   * </p>
   * 
   * @param query
   * @return ArrayList<Map<String, String>>
   * @throws SQLException
   */

  public ArrayList<Map<String, String>> select(SelectQuery query) throws SQLException;

  /**
   * <p>
   * Perform update query
   * </p>
   * 
   * @param query
   * @throws SQLException
   */

  public void update(UpdateQuery query) throws SQLException;

  /**
   * <p>
   * Perform a free form query. This method is unsafe.
   * WARNING: don't use this method in production!
   * </p>
   * 
   * @param query
   * @throws SQLException
   */

  public void performFreeform(String query) throws SQLException;

}
