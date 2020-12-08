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

package it.hypocracy.robespierre.meta.wikidata;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import it.hypocracy.robespierre.http.HTTPFacade;
import it.hypocracy.robespierre.http.HTTPResponse;
import it.hypocracy.robespierre.meta.exceptions.MetadataReceiverException;
import it.hypocracy.robespierre.meta.wikidata.imageinfo.Imageinfo;
import it.hypocracy.robespierre.meta.wikidata.search.Search;
import it.hypocracy.robespierre.meta.wikidata.wbentity.Datavalue;
import it.hypocracy.robespierre.meta.wikidata.wbentity.DatavalueDeserializer;
import it.hypocracy.robespierre.meta.wikidata.wbentity.WbEntity;
import it.hypocracy.robespierre.utils.ISO3166;

/**
 * WikiDataApiClient is the class which takes care of 
 * querying and receiving data through wikidata API
 */

public class WikiDataApiClient {

  private HTTPFacade httpcli;

  private static String apiUri = "https://www.wikidata.org/w/api.php";
  private static String searchUri = apiUri + "?format=json&action=query&list=search";
  private static String wbentityUri = apiUri + "?format=json&action=wbgetentities";
  private static String imageinfoUri = apiUri + "?format=json&action=query&prop=imageinfo&iiprop=url";

  /**
   * <p>
   * WikiDataApiClient constructor
   * </p>
   * 
   */

  public WikiDataApiClient() {
    this.httpcli = new HTTPFacade();
  }

  /**
   * <p>
   * Search in WikiData database and return the Search result
   * </p>
   * 
   * @param what
   * @param maxResults
   * @return Search
   * @throws MetadataReceiverException
   */

  public Search search(String what, int maxResults) throws MetadataReceiverException {
    StringBuilder uriBuilder = new StringBuilder();
    uriBuilder.append(searchUri);
    // Max results
    uriBuilder.append("&srlimit=");
    uriBuilder.append(maxResults);
    // Action query and URL Encode query
    uriBuilder.append("&srsearch=");
    uriBuilder.append(URLEncoder.encode(what, StandardCharsets.UTF_8));
    // Send GET request
    URI uri = URI.create(uriBuilder.toString());
    try {
      HTTPResponse response = this.httpcli.get(uri);
      String strResponse = response.getStringBody();
      // Parse JSON
      Gson jsonParser = new Gson();
      return jsonParser.fromJson(strResponse, Search.class);
    } catch (IOException ex) {
      throw new MetadataReceiverException(ex.getMessage());
    } catch (JsonSyntaxException ex) {
      throw new MetadataReceiverException(ex.getMessage());
    }
  }

  /**
   * <p>
   * Get web entity through WikiData API from ID
   * </p>
   * 
   * @param wikidataId
   * @param country
   * @return WbEntity
   * @throws MetadataReceiverException
   */

  public WbEntity getWebEntity(String wikidataId, ISO3166 country) throws MetadataReceiverException {
    final String language = country.toISO639().toString();
    StringBuilder uriBuilder = new StringBuilder();
    uriBuilder.append(wbentityUri);
    // Set id
    uriBuilder.append("&ids=");
    uriBuilder.append(wikidataId);
    // Set language
    uriBuilder.append("&languages=");
    uriBuilder.append(language);
    // Send GET request
    URI uri = URI.create(uriBuilder.toString());
    try {
      HTTPResponse response = this.httpcli.get(uri);
      String strResponse = response.getStringBody();
      // Parse JSON
      Gson jsonParser = new GsonBuilder().registerTypeAdapter(Datavalue.class, new DatavalueDeserializer()).create();
      return jsonParser.fromJson(strResponse, WbEntity.class);
    } catch (IOException ex) {
      throw new MetadataReceiverException(ex.getMessage());
    } catch (JsonSyntaxException ex) {
      throw new MetadataReceiverException(ex.getMessage());
    }
  }

  /**
   * <p>
   * Retrieve from WikiData API image info
   * </p>
   * 
   * @param file
   * @return Imageinfo
   * @throws MetadataReceiverException
   */

  public Imageinfo getImageInfo(String file) throws MetadataReceiverException {
    StringBuilder uriBuilder = new StringBuilder();
    uriBuilder.append(imageinfoUri);
    // Set file
    uriBuilder.append("&titles=File:");
    uriBuilder.append(URLEncoder.encode(file, StandardCharsets.UTF_8));
    // Send GET request
    URI uri = URI.create(uriBuilder.toString());
    try {
      HTTPResponse response = this.httpcli.get(uri);
      String strResponse = response.getStringBody();
      // Parse JSON
      Gson jsonParser = new Gson();
      return jsonParser.fromJson(strResponse, Imageinfo.class);
    } catch (IOException ex) {
      throw new MetadataReceiverException(ex.getMessage());
    } catch (JsonSyntaxException ex) {
      throw new MetadataReceiverException(ex.getMessage());
    }
  }

}
