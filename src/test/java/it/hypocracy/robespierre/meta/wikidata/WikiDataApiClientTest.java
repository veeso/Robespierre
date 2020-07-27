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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

import it.hypocracy.robespierre.meta.exceptions.MetadataReceiverException;
import it.hypocracy.robespierre.meta.wikidata.imageinfo.Image;
import it.hypocracy.robespierre.meta.wikidata.imageinfo.Imageinfo;
import it.hypocracy.robespierre.meta.wikidata.search.QueryResult;
import it.hypocracy.robespierre.meta.wikidata.search.Search;
import it.hypocracy.robespierre.meta.wikidata.wbentity.Description;
import it.hypocracy.robespierre.meta.wikidata.wbentity.Entity;
import it.hypocracy.robespierre.meta.wikidata.wbentity.Label;
import it.hypocracy.robespierre.meta.wikidata.wbentity.WbEntity;
import it.hypocracy.robespierre.utils.ISO3166;

public class WikiDataApiClientTest {

  @Test
  public void shouldQuery() throws MetadataReceiverException {
    WikiDataApiClient cli = new WikiDataApiClient();
    // Make search
    Search search = cli.search("alberto angela");
    // Should have query
    assertNotNull(search.query);
    // Query length should be 4
    assertEquals(4, search.query.search.size());
    // Verify first item
    QueryResult first = search.query.search.get(0);
    assertNotNull(first);
    assertEquals("Q514695", first.title);
    assertEquals(484093, first.pageid);
    assertEquals("Italian paleontologist and scientific popularizer", first.snippet);
  }

  @Test
  public void shouldGetWbEntity() throws MetadataReceiverException {
    WikiDataApiClient cli = new WikiDataApiClient();
    // Get wbentity
    ISO3166 country = new ISO3166("IT");
    WbEntity wbEntity = cli.getWebEntity("Q514695", country);
    assertNotNull(wbEntity.entities.get("Q514695"));
    Entity entity = wbEntity.entities.get("Q514695");
    assertEquals(484093, entity.pageid);
    assertEquals("Q514695", entity.title);
    assertNotNull(entity.labels.get("it"));
    Label label = entity.labels.get("it");
    assertEquals("it", label.language);
    assertEquals("Alberto Angela", label.value);
    assertNotNull(entity.descriptions.get("it"));
    Description desc = entity.descriptions.get("it");
    assertEquals("it", desc.language);
    assertEquals("paleontologo, divulgatore scientifico e giornalista italiano", desc.value);
    // Check claims (citizenship for example should be q38)
    assertEquals("Q38", entity.claims.get("P27").get(0).mainsnak.datavalue.value.id);
    // Check if it's human
    assertEquals("Q5", entity.claims.get("P31").get(0).mainsnak.datavalue.value.id);
  }

  @Test
  public void shouldGetImageInfo() throws MetadataReceiverException {
    WikiDataApiClient cli = new WikiDataApiClient();
    Imageinfo iminfo = cli.getImageInfo("Alberto Angela.jpg");
    assertNotNull(iminfo.query);
    Image img = iminfo.query.pages.get("-1");
    assertNotNull(img);
    assertEquals("File:Alberto Angela.jpg", img.title);
    assertEquals("https://upload.wikimedia.org/wikipedia/commons/1/1e/Alberto_Angela.jpg", img.imageinfo.get(0).url);
  }

}
