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

/**
 * This class describes an Order statement for select
 */

public class Order {
  
  private String[] what;
  private OrderType type;

  /**
   * @param what
   * @param type
   */

  public Order(String[] what, OrderType type) {
    this.what = what;
    this.type = type;
  }

  //@! getters

  public String[] getWhat() {
    return what;
  }

  public OrderType getType() {
    return type;
  }

}
