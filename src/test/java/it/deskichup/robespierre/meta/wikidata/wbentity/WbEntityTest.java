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

package it.deskichup.robespierre.meta.wikidata.wbentity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class WbEntityTest {
  
  private static String wbJson = "{\"entities\":{\"Q1055449\":{\"pageid\":1004348,\"ns\":0,\"title\":\"Q1055449\",\"lastrevid\":1233184257,\"modified\":\"2020-07-17T17:00:24Z\",\"type\":\"item\",\"id\":\"Q1055449\",\"labels\":{\"it\":{\"language\":\"it\",\"value\":\"Matteo Salvini\"}},\"descriptions\":{\"it\":{\"language\":\"it\",\"value\":\"Politico italiano\"}},\"aliases\":{},\"claims\":{\"P69\":[{\"mainsnak\":{\"snaktype\":\"value\",\"property\":\"P69\",\"hash\":\"b990ca1601e1aa66ab1c766f3eb1927d5c3303db\",\"datavalue\":{\"value\":{\"entity-type\":\"item\",\"numeric-id\":3831942,\"id\":\"Q3831942\"},\"type\":\"wikibase-entityid\"},\"datatype\":\"wikibase-item\"},\"type\":\"statement\",\"qualifiers\":{\"P580\":[{\"snaktype\":\"value\",\"property\":\"P580\",\"hash\":\"cde4b091f7e46247f112afae1f93de201e51deb9\",\"datavalue\":{\"value\":{\"time\":\"+1987-01-01T00:00:00Z\",\"timezone\":0,\"before\":0,\"after\":0,\"precision\":9,\"calendarmodel\":\"http://www.wikidata.org/entity/Q1985727\"},\"type\":\"time\"},\"datatype\":\"time\"}],\"P582\":[{\"snaktype\":\"value\",\"property\":\"P582\",\"hash\":\"0b47570bbb7ed8cf00ed8aa1023eb71676bff663\",\"datavalue\":{\"value\":{\"time\":\"+1992-01-01T00:00:00Z\",\"timezone\":0,\"before\":0,\"after\":0,\"precision\":9,\"calendarmodel\":\"http://www.wikidata.org/entity/Q1985727\"},\"type\":\"time\"},\"datatype\":\"time\"}]},\"qualifiers-order\":[\"P580\",\"P582\"],\"id\":\"Q1055449$57b21fde-4763-f2ed-6379-49c21f571bb4\",\"rank\":\"normal\",\"references\":[{\"hash\":\"07d6b6d67b4a7574e36ddf90a192e3cc98329042\",\"snaks\":{\"P854\":[{\"snaktype\":\"value\",\"property\":\"P854\",\"hash\":\"99af91f7dd662e559a87183fc74ad5996a813639\",\"datavalue\":{\"value\":\"http://www.matteosalvini.eu/chi-sono\",\"type\":\"string\"},\"datatype\":\"url\"}],\"P1476\":[{\"snaktype\":\"value\",\"property\":\"P1476\",\"hash\":\"b912f5eb7976f9c93024456014369e2757826b86\",\"datavalue\":{\"value\":{\"text\":\"Matteo Salvini si presenta\",\"language\":\"it\"},\"type\":\"monolingualtext\"},\"datatype\":\"monolingualtext\"}],\"P1065\":[{\"snaktype\":\"value\",\"property\":\"P1065\",\"hash\":\"409dbd2f1a5c0e120180396bfa22a1ff1b96e20c\",\"datavalue\":{\"value\":\"https://archive.is/20131218224118/http://www.matteosalvini.eu/chi-sono\",\"type\":\"string\"},\"datatype\":\"url\"}],\"P2960\":[{\"snaktype\":\"value\",\"property\":\"P2960\",\"hash\":\"a525bd5f9735f57008bb63e70d819e74e1192f7b\",\"datavalue\":{\"value\":{\"time\":\"+2013-12-18T00:00:00Z\",\"timezone\":0,\"before\":0,\"after\":0,\"precision\":11,\"calendarmodel\":\"http://www.wikidata.org/entity/Q1985727\"},\"type\":\"time\"},\"datatype\":\"time\"}]},\"snaks-order\":[\"P854\",\"P1476\",\"P1065\",\"P2960\"]}]}],\"P569\":[{\"mainsnak\":{\"snaktype\":\"value\",\"property\":\"P569\",\"hash\":\"b8e13523459d6237222cc1b282e45c49654c9e3f\",\"datavalue\":{\"value\":{\"time\":\"+1973-03-09T00:00:00Z\",\"timezone\":0,\"before\":0,\"after\":0,\"precision\":11,\"calendarmodel\":\"http://www.wikidata.org/entity/Q1985727\"},\"type\":\"time\"},\"datatype\":\"time\"},\"type\":\"statement\",\"id\":\"Q1055449$fa16694b-4ac7-8636-c046-7a988587ab58\",\"rank\":\"normal\",\"references\":[{\"hash\":\"55a658694c76ea15aaa3a307eddd2115cb2f9b60\",\"snaks\":{\"P854\":[{\"snaktype\":\"value\",\"property\":\"P854\",\"hash\":\"141fed9aea9583cae985f2c78747af68051df791\",\"datavalue\":{\"value\":\"http://www.europarl.europa.eu/meps/en/28404/MATTEO_SALVINI_home.html;jsessionid=EFC76809008C02DB4AE81DF5FF47B2F4.node2\",\"type\":\"string\"},\"datatype\":\"url\"}],\"P1065\":[{\"snaktype\":\"value\",\"property\":\"P1065\",\"hash\":\"7be726792a57adfd653ceb4011342babe804d6d1\",\"datavalue\":{\"value\":\"https://web.archive.org/web/20140426154946/http://www.europarl.europa.eu/meps/en/28404/MATTEO_SALVINI_home.html;jsessionid=EFC76809008C02DB4AE81DF5FF47B2F4.node2\",\"type\":\"string\"},\"datatype\":\"url\"}],\"P2960\":[{\"snaktype\":\"value\",\"property\":\"P2960\",\"hash\":\"e7414907e42e85d5ebd8e5747ae4a31706bd3417\",\"datavalue\":{\"value\":{\"time\":\"+2014-04-26T00:00:00Z\",\"timezone\":0,\"before\":0,\"after\":0,\"precision\":11,\"calendarmodel\":\"http://www.wikidata.org/entity/Q1985727\"},\"type\":\"time\"},\"datatype\":\"time\"}]},\"snaks-order\":[\"P854\",\"P1065\",\"P2960\"]},{\"hash\":\"2939d03b7a4341b2c127f92081cdc9173c2aeb23\",\"snaks\":{\"P248\":[{\"snaktype\":\"value\",\"property\":\"P248\",\"hash\":\"955922c4315ac036b19e6af494c9a897193971b0\",\"datavalue\":{\"value\":{\"entity-type\":\"item\",\"numeric-id\":974352,\"id\":\"Q974352\"},\"type\":\"wikibase-entityid\"},\"datatype\":\"wikibase-item\"}],\"P1284\":[{\"snaktype\":\"value\",\"property\":\"P1284\",\"hash\":\"48383687aa932e6c1ba8c54abe384c8e9e11e165\",\"datavalue\":{\"value\":\"00000031013\",\"type\":\"string\"},\"datatype\":\"external-id\"}],\"P1810\":[{\"snaktype\":\"value\",\"property\":\"P1810\",\"hash\":\"c91199490b93773a93ac26d43e796c92a6b4c0b8\",\"datavalue\":{\"value\":\"Matteo Salvini\",\"type\":\"string\"},\"datatype\":\"string\"}],\"P813\":[{\"snaktype\":\"value\",\"property\":\"P813\",\"hash\":\"e5f60ab0b03700bb883efce38f8022d023bc49fb\",\"datavalue\":{\"value\":{\"time\":\"+2017-10-09T00:00:00Z\",\"timezone\":0,\"before\":0,\"after\":0,\"precision\":11,\"calendarmodel\":\"http://www.wikidata.org/entity/Q1985727\"},\"type\":\"time\"},\"datatype\":\"time\"}]},\"snaks-order\":[\"P248\",\"P1284\",\"P1810\",\"P813\"]}]}]}}}}";
  private static String particularWbJson = "{\"entities\":{\"Q1055449\":{\"pageid\":1004348,\"ns\":0,\"title\":\"Q1055449\",\"lastrevid\":1233184257,\"modified\":\"2020-07-17T17:00:24Z\",\"type\":\"item\",\"id\":\"Q1055449\",\"labels\":{\"it\":{\"language\":\"it\",\"value\":\"Matteo Salvini\"}},\"descriptions\":{\"it\":{\"language\":\"it\",\"value\":\"Politico italiano\"}},\"aliases\":{},\"claims\":{\"P297\":[{\"mainsnak\":{\"snaktype\":\"value\",\"property\":\"P297\",\"hash\":\"678b323f6f495dfd2802a33415aad1070b20e94d\",\"datavalue\":{\"value\":\"IT\",\"type\":\"string\"},\"datatype\":\"external-id\"},\"type\":\"statement\",\"id\":\"q38$B0202A62-4B99-4CAE-B3E7-637DB7DDE0C4\",\"rank\":\"normal\"}],\"P18\":[{\"mainsnak\":{\"snaktype\":\"value\",\"property\":\"P18\",\"hash\":\"a468b97ad43056653fbf177159e1eb5d585c841e\",\"datavalue\":{\"value\":\"Matteo Salvini Viminale.jpg\",\"type\":\"string\"},\"datatype\":\"commonsMedia\"}}]}}}}";

  @Test
  public void shouldParseWbEntity() {
    Gson gson = new GsonBuilder().registerTypeAdapter(Datavalue.class, new DatavalueDeserializer()).create();
    WbEntity searchData = gson.fromJson(wbJson, WbEntity.class);
    //Should have entity 'Q1055449'
    assertNotNull(searchData.entities.get("Q1055449"));
    Entity entity = searchData.entities.get("Q1055449");
    //Verify parameters
    assertEquals(1004348, entity.pageid);
    assertEquals("Q1055449", entity.title);
    //Check labels
    assertNotNull(entity.labels.get("it"));
    Label label = entity.labels.get("it");
    assertEquals("it", label.language);
    assertEquals("Matteo Salvini", label.value);
    //Check descriptions
    assertNotNull(entity.descriptions.get("it"));
    Description desc = entity.descriptions.get("it");
    assertEquals("it", desc.language);
    assertEquals("Politico italiano", desc.value);
    //Check claims (we should have P69 and P569)
    assertNotNull(entity.claims.get("P69"));
    assertNotNull(entity.claims.get("P569"));
    //P69
    WbProperty p69 = entity.claims.get("P69").get(0);
    assertNotNull(p69);
    assertNotNull(p69.mainsnak);
    Mainsnak mainsnak = p69.mainsnak;
    assertEquals("P69", mainsnak.property);
    Datavalue datavalue = mainsnak.datavalue;
    assertNotNull(datavalue);
    assertEquals("wikibase-entityid", datavalue.type);
    DatavalueVal datavalueVal = datavalue.value;
    assertNotNull(datavalueVal);
    assertEquals("Q3831942", datavalueVal.id);
    //P596
    WbProperty p569 = entity.claims.get("P569").get(0);
    assertNotNull(p569);
    assertNotNull(p569.mainsnak);
    mainsnak = p569.mainsnak;
    assertEquals("P569", mainsnak.property);
    datavalue = mainsnak.datavalue;
    assertNotNull(datavalue);
    assertEquals("time", datavalue.type);
    datavalueVal = datavalue.value;
    assertNotNull(datavalueVal);
    assertEquals("+1973-03-09T00:00:00Z", datavalueVal.time);
  }

  @Test
  public void shouldParseParticularCase() {
    Gson gson = new GsonBuilder().registerTypeAdapter(Datavalue.class, new DatavalueDeserializer()).create();
    WbEntity searchData = gson.fromJson(particularWbJson, WbEntity.class);
    Entity entity = searchData.entities.get("Q1055449");
    //Check claims (we should have P297 and P18)
    assertNotNull(entity.claims.get("P297"));
    assertNotNull(entity.claims.get("P18"));
    //P69
    WbProperty P297 = entity.claims.get("P297").get(0);
    assertNotNull(P297);
    assertNotNull(P297.mainsnak);
    Mainsnak mainsnak = P297.mainsnak;
    assertEquals("P297", mainsnak.property);
    Datavalue datavalue = mainsnak.datavalue;
    assertNotNull(datavalue);
    assertEquals("string", datavalue.type);
    String datavalueVal = datavalue.value.value;
    assertNotNull(datavalueVal);
    assertEquals("IT", datavalueVal);
    //P596
    WbProperty P18 = entity.claims.get("P18").get(0);
    assertNotNull(P18);
    assertNotNull(P18.mainsnak);
    mainsnak = P18.mainsnak;
    assertEquals("P18", mainsnak.property);
    datavalue = mainsnak.datavalue;
    assertNotNull(datavalue);
    assertEquals("string", datavalue.type);
    datavalueVal = datavalue.value.value;
    assertNotNull(datavalueVal);
    assertEquals("Matteo Salvini Viminale.jpg", datavalueVal);
  }

}
