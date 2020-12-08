/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * MIT License
 * 
 * Copyright (c) 2020 Christian Visintin
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package it.deskichup.robespierre.meta.wikidata;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import it.deskichup.robespierre.http.HTTPFacade;
import it.deskichup.robespierre.http.HTTPResponse;
import it.deskichup.robespierre.meta.exceptions.MetadataReceiverException;
import it.deskichup.robespierre.meta.wikidata.imageinfo.Imageinfo;
import it.deskichup.robespierre.meta.wikidata.search.Search;
import it.deskichup.robespierre.meta.wikidata.wbentity.Datavalue;
import it.deskichup.robespierre.meta.wikidata.wbentity.DatavalueDeserializer;
import it.deskichup.robespierre.meta.wikidata.wbentity.WbEntity;
import it.deskichup.robespierre.utils.ISO3166;

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
