/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.utils;

import java.util.UUID;

public final class Uuidv4 {

  private UUID uuid;

  public Uuidv4() {
    this.uuid = UUID.randomUUID();
  }

  /**
   * <p>
   * Returns the generated uuidv4
   * </p>
   * 
   * @return the generated uuidv4
   */

  public String getUUIDv4() {
    return this.uuid.toString();
  }

}
