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

package it.deskichup.robespierre.meta.wikidata.search;

import com.google.gson.Gson;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class SearchTest {

  private static String searchJson = "{\"batchcomplete\":\"\",\"continue\":{\"sroffset\":10,\"continue\":\"-||\"},\"query\":{\"searchinfo\":{\"totalhits\":1003},\"search\":[{\"ns\":0,\"title\":\"Q514695\",\"pageid\":484093,\"size\":null,\"wordcount\":0,\"snippet\":\"Italian paleontologist and scientific popularizer\",\"timestamp\":\"2020-04-23T18:36:48Z\"},{\"ns\":0,\"title\":\"Q3897204\",\"pageid\":3718511,\"size\":null,\"wordcount\":0,\"snippet\":\"\",\"timestamp\":\"2019-12-27T23:14:05Z\"},{\"ns\":0,\"title\":\"Q983948\",\"pageid\":933623,\"size\":null,\"wordcount\":0,\"snippet\":\"\",\"timestamp\":\"2019-12-27T23:12:50Z\"},{\"ns\":0,\"title\":\"Q97655472\",\"pageid\":96039473,\"size\":null,\"wordcount\":0,\"snippet\":\"\",\"timestamp\":\"2020-07-24T20:40:01Z\"},{\"ns\":0,\"title\":\"Q298\",\"pageid\":479,\"size\":null,\"wordcount\":0,\"snippet\":\"sovereign state in South America\",\"timestamp\":\"2020-07-24T20:42:35Z\"},{\"ns\":0,\"title\":\"Q58856080\",\"pageid\":58767863,\"size\":null,\"wordcount\":0,\"snippet\":\"\",\"timestamp\":\"2020-01-06T09:01:01Z\"},{\"ns\":0,\"title\":\"Q46662707\",\"pageid\":47783555,\"size\":null,\"wordcount\":0,\"snippet\":\"scientific article\",\"timestamp\":\"2020-05-25T15:05:28Z\"},{\"ns\":0,\"title\":\"Q28213327\",\"pageid\":29911025,\"size\":null,\"wordcount\":0,\"snippet\":\"scientific article (publication date: 19 June 2007)\",\"timestamp\":\"2019-12-31T23:26:23Z\"},{\"ns\":0,\"title\":\"Q36116060\",\"pageid\":37506027,\"size\":null,\"wordcount\":0,\"snippet\":\"scientific article\",\"timestamp\":\"2020-06-27T12:15:58Z\"},{\"ns\":0,\"title\":\"Q43251393\",\"pageid\":44480195,\"size\":null,\"wordcount\":0,\"snippet\":\"scientific article published on 29 October 2009\",\"timestamp\":\"2019-01-22T08:15:15Z\"}]}}";
  
  @Test
  public void shouldParseWikidataSearch() {
    Gson gson = new Gson();
    Search searchData = gson.fromJson(searchJson, Search.class);
    assertNotNull(searchData.query);
    //There should be 10 query entries
    Query query = searchData.query;
    assertEquals(10, query.search.size());
    //Check first result
    QueryResult entry = query.search.get(0);
    assertNotNull(entry);
    assertEquals("Q514695", entry.title);
    assertEquals(484093, entry.pageid);
    assertEquals("Italian paleontologist and scientific popularizer", entry.snippet);
  }
  
}
