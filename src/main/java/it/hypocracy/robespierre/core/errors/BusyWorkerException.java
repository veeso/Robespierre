/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.core.errors;

public class BusyWorkerException extends Exception { 

  private static final long serialVersionUID = 350925654882958972L;

  public BusyWorkerException(String errorMessage) {
      super(errorMessage);
  }

}
