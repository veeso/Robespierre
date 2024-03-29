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

package it.deskichup.robespierre.article;

import it.deskichup.robespierre.utils.Uuidv4;

/**
 * Occupation describes a subject (human) occupation
 */

public class Occupation {
  
  private String id;
  private String name;

  /**
   * <p>
   * Instantiates a new Category
   * </p>
   * 
   * @param id
   * @param name
   */

  public Occupation(String id, String name) {
    this.id = id;
    this.name = name;
  }

  /**
   * <p>
   * Instantiates a new Category
   * </p>
   * 
   * @param name
   */

  public Occupation(String name) {
    this(null, name);
    // Generate UUID
    Uuidv4 uuid = new Uuidv4();
    this.id = uuid.getUUIDv4();
  }

  /**
   * <p>
   * Returns category name
   * </p>
   * 
   * @return name
   */

  public String getName() {
    return this.name;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

}
