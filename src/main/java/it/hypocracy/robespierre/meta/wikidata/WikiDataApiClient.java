/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.meta.wikidata;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import it.hypocracy.robespierre.http.HTTPFacade;
import it.hypocracy.robespierre.http.HTTPResponse;
import it.hypocracy.robespierre.meta.exceptions.MetadataReceiverException;
import it.hypocracy.robespierre.meta.wikidata.imageinfo.Imageinfo;
import it.hypocracy.robespierre.meta.wikidata.search.Search;
import it.hypocracy.robespierre.meta.wikidata.wbentity.WbEntity;
import it.hypocracy.robespierre.utils.ISO3166;

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
   * @return Search
   * @throws MetadataReceiverException
   */

  public Search search(String what) throws MetadataReceiverException {
    StringBuilder uriBuilder = new StringBuilder();
    uriBuilder.append(searchUri);
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
    final String language = country.toString().toLowerCase();
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
      Gson jsonParser = new Gson();
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
