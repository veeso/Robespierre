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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OrderTest {

  @Test
  public void shouldInstantiateOrder() {
    String[] what = new String[2];
    what[0] = "age";
    what[1] = "postal_code";
    Order orderby = new Order(what, OrderType.ASC);
    assertEquals(orderby.getWhat().length, 2);
    assertEquals(orderby.getType(), OrderType.ASC);
  }
  
}