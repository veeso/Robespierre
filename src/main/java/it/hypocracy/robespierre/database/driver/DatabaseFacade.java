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
