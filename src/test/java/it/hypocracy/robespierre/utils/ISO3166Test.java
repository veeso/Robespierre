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

package it.hypocracy.robespierre.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class ISO3166Test {
  
  @Test
  public void shouldInstantiateISO3166() {
    String code = "IT";
    ISO3166 iso = new ISO3166(code);
    assertEquals(code, iso.toString());
  }

  @Test
  public void shouldInstantiateISO3166LowerCase() {
    String code = "it";
    ISO3166 iso = new ISO3166(code);
    assertEquals(code.toUpperCase(), iso.toString());
  }

  @Test
  public void shouldConvertToIso639() {
    String code = "AD";
    ISO3166 iso = new ISO3166(code);
    assertEquals("ca", iso.toISO639().toString());
  }

  @Test
  public void shouldConvertToIso639Default() {
    String code = "BV"; // <https://it.wikipedia.org/wiki/Isola_Bouvet>
    ISO3166 iso = new ISO3166(code);
    assertEquals("en", iso.toISO639().toString());
  }

  @Test
  public void shouldFailISO3166() {
    String code = "XX";
    assertThrows(IllegalArgumentException.class, () -> new ISO3166(code));
  }

}
