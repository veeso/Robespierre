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

package it.deskichup.robespierre.meta.wikidata.imageinfo;

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
