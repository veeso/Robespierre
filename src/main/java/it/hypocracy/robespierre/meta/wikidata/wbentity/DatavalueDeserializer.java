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

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class DatavalueDeserializer implements JsonDeserializer<Datavalue> {

  @Override
  public Datavalue deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    JsonObject jsonObject = json.getAsJsonObject();
    String type = jsonObject.get("type").getAsString();
    if (type == null) {
      throw new JsonParseException("Could not find 'type' in Datavalue");
    }
    if (type.equals("string")) {
      DatavalueVal dval = new DatavalueVal();
      dval.value = jsonObject.get("value").getAsString();
      Datavalue datavalue = new Datavalue();
      datavalue.type = type;
      datavalue.value = dval;
      return datavalue;
    } else {
      // Parse datavalue
      DatavalueVal dval = context.deserialize(jsonObject.get("value"), DatavalueVal.class);
      Datavalue datavalue = new Datavalue();
      datavalue.type = type;
      datavalue.value = dval;
      return datavalue;
    }
  }

}