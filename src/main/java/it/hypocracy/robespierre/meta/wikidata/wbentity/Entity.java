/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.meta.wikidata.wbentity;

import java.util.ArrayList;
import java.util.Map;

public class Entity {
  
  public int pageid;
  public String title; // It's wikidata ID (QXXXXXX)
  public Map<String, Label> labels;
  public Map<String, Description> descriptions;
  public Map<String, ArrayList<WbProperty>> claims;

}
