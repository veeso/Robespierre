/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) 2020 Christian Visintin - christian.visintin1997@gmail.com
 *
 * This file is part of "Robespierre"
 *
 * Robespierre is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Robespierre is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Robespierre.  If not, see <http://www.gnu.org/licenses/>.
 *
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