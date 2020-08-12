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

import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

  /**
   * <p>
   * Write content to file and close it
   * </p>
   * 
   * @param file
   * @param what
   * @throws IOException
   */

  public static void writeFile(String file, String what) throws IOException {
    FileWriter fileHnd = new FileWriter(file);
    fileHnd.write(what);
    fileHnd.close();
  }

}
