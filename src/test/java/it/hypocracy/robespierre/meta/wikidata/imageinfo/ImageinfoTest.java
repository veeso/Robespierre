/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.meta.wikidata.imageinfo;

import com.google.gson.Gson;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class ImageinfoTest {

  private static String imageinfoJson = "{\"batchcomplete\":\"\",\"query\":{\"pages\":{\"-1\":{\"ns\":6,\"title\":\"File:Matteo Salvini Viminale.jpg\",\"missing\":\"\",\"known\":\"\",\"imagerepository\":\"shared\",\"imageinfo\":[{\"url\":\"https://upload.wikimedia.org/wikipedia/commons/3/36/Matteo_Salvini_Viminale.jpg\",\"descriptionurl\":\"https://commons.wikimedia.org/wiki/File:Matteo_Salvini_Viminale.jpg\",\"descriptionshorturl\":\"https://commons.wikimedia.org/w/index.php?curid=69642292\"}]}}}}";
  
  @Test
  public void shouldParseWikidataSearch() {
    Gson gson = new Gson();
    Imageinfo data = gson.fromJson(imageinfoJson, Imageinfo.class);
    assertNotNull(data.query);
    Imagequery query = data.query;
    //There should be one page
    assertEquals(1, query.pages.size());
    //Get page -1
    Image image = query.pages.get("-1");
    assertEquals("File:Matteo Salvini Viminale.jpg", image.title);
    //Get imageInfo
    assertEquals(1, image.imageinfo.size());
    ImageInfoUri iminfo = image.imageinfo.get(0);
    assertNotNull(iminfo);
    assertEquals("https://upload.wikimedia.org/wikipedia/commons/3/36/Matteo_Salvini_Viminale.jpg", iminfo.url);
    assertEquals("https://commons.wikimedia.org/wiki/File:Matteo_Salvini_Viminale.jpg", iminfo.descriptionurl);
    assertEquals("https://commons.wikimedia.org/w/index.php?curid=69642292", iminfo.descriptionshorturl);
  }
  
}
