/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.utils;

import java.util.HashMap;
import java.util.Locale;

public class ISO3166 {

  private static String[] codes = null;
  private static HashMap<String, String> languages;

  private String code;

  /**
   * </p>
   * Instantiate a new ISO3166
   * </p>
   * 
   * @param code
   * @throws IllegalArgumentException
   */

  public ISO3166(String code) throws IllegalArgumentException {
    // If codes is null, gather codes
    if (codes == null) {
      ISO3166.fillCodes();
    }
    if (checkCode(code)) {
      this.code = code.toUpperCase();
    } else {
      String err = "Invalid code: " + code;
      throw new IllegalArgumentException(err);
    }
  }

  /**
   * <p>
   * Convert ISO3166 to string
   * </p>
   * 
   * @return String
   */
  @Override
  public String toString() {
    return this.code;
  }

  /**
   * <p>
   * Get primary language for country
   * </p>
   * 
   * @return ISO639
   */

  public ISO639 toISO639() {
    try {
      return new ISO639(languages.get(this.toString()));
    } catch (IllegalArgumentException ex) {
      return new ISO639();
    }
  }

  /**
   * <p>
   * Check if provided code is a valid ISO3166
   * </p>
   * 
   * @param code
   * @return true if is valid
   */

  private boolean checkCode(String code) {
    String check = code.toUpperCase();
    for (String c : codes) {
      if (check.equals(c)) {
        return true;
      }
    }
    return false;
  }

  /**
   * <p>
   * Fill codes array
   * </p>
   */

  private static void fillCodes() {
    codes = Locale.getISOCountries();
    languages = new HashMap<>();
    // Fill languages
    for (String code : codes) {
      if (code.equals("AD")) {
        languages.put(code, "ca");
      } else if (code.equals("AE")) {
        languages.put(code, "ar");
      } else if (code.equals("AF")) {
        languages.put(code, "fa");
      } else if (code.equals("AG")) {
        languages.put(code, "en");
      } else if (code.equals("AI")) {
        languages.put(code, "en");
      } else if (code.equals("AL")) {
        languages.put(code, "sq");
      } else if (code.equals("AM")) {
        languages.put(code, "hy");
      } else if (code.equals("AO")) {
        languages.put(code, "pt");
      } else if (code.equals("AQ")) {
        languages.put(code, "");
      } else if (code.equals("AR")) {
        languages.put(code, "es");
      } else if (code.equals("AS")) {
        languages.put(code, "en");
      } else if (code.equals("AT")) {
        languages.put(code, "de");
      } else if (code.equals("AU")) {
        languages.put(code, "en");
      } else if (code.equals("AW")) {
        languages.put(code, "nl");
      } else if (code.equals("AX")) {
        languages.put(code, "sv");
      } else if (code.equals("AZ")) {
        languages.put(code, "az");
      } else if (code.equals("BA")) {
        languages.put(code, "bs");
      } else if (code.equals("BB")) {
        languages.put(code, "en");
      } else if (code.equals("BD")) {
        languages.put(code, "bn");
      } else if (code.equals("BE")) {
        languages.put(code, "nl");
      } else if (code.equals("BF")) {
        languages.put(code, "fr");
      } else if (code.equals("BG")) {
        languages.put(code, "bg");
      } else if (code.equals("BH")) {
        languages.put(code, "ar");
      } else if (code.equals("BI")) {
        languages.put(code, "fr");
      } else if (code.equals("BJ")) {
        languages.put(code, "fr");
      } else if (code.equals("BL")) {
        languages.put(code, "fr");
      } else if (code.equals("BM")) {
        languages.put(code, "en");
      } else if (code.equals("BN")) {
        languages.put(code, "ms");
      } else if (code.equals("BO")) {
        languages.put(code, "es");
      } else if (code.equals("BQ")) {
        languages.put(code, "nl");
      } else if (code.equals("BR")) {
        languages.put(code, "pt");
      } else if (code.equals("BS")) {
        languages.put(code, "en");
      } else if (code.equals("BT")) {
        languages.put(code, "dz");
      } else if (code.equals("BV")) {
        languages.put(code, "");
      } else if (code.equals("BW")) {
        languages.put(code, "en");
      } else if (code.equals("BY")) {
        languages.put(code, "be");
      } else if (code.equals("BZ")) {
        languages.put(code, "en");
      } else if (code.equals("CA")) {
        languages.put(code, "en");
      } else if (code.equals("CC")) {
        languages.put(code, "ms");
      } else if (code.equals("CD")) {
        languages.put(code, "fr");
      } else if (code.equals("CF")) {
        languages.put(code, "fr");
      } else if (code.equals("CG")) {
        languages.put(code, "fr");
      } else if (code.equals("CH")) {
        languages.put(code, "de");
      } else if (code.equals("CI")) {
        languages.put(code, "fr");
      } else if (code.equals("CK")) {
        languages.put(code, "en");
      } else if (code.equals("CL")) {
        languages.put(code, "es");
      } else if (code.equals("CM")) {
        languages.put(code, "en");
      } else if (code.equals("CN")) {
        languages.put(code, "zh");
      } else if (code.equals("CO")) {
        languages.put(code, "es");
      } else if (code.equals("CR")) {
        languages.put(code, "es");
      } else if (code.equals("CU")) {
        languages.put(code, "es");
      } else if (code.equals("CV")) {
        languages.put(code, "pt");
      } else if (code.equals("CW")) {
        languages.put(code, "nl");
      } else if (code.equals("CX")) {
        languages.put(code, "en");
      } else if (code.equals("CY")) {
        languages.put(code, "el");
      } else if (code.equals("CZ")) {
        languages.put(code, "cs");
      } else if (code.equals("DE")) {
        languages.put(code, "de");
      } else if (code.equals("DJ")) {
        languages.put(code, "fr");
      } else if (code.equals("DK")) {
        languages.put(code, "da");
      } else if (code.equals("DM")) {
        languages.put(code, "en");
      } else if (code.equals("DO")) {
        languages.put(code, "es");
      } else if (code.equals("DZ")) {
        languages.put(code, "ar");
      } else if (code.equals("EC")) {
        languages.put(code, "es");
      } else if (code.equals("EE")) {
        languages.put(code, "et");
      } else if (code.equals("EG")) {
        languages.put(code, "ar");
      } else if (code.equals("EH")) {
        languages.put(code, "ar");
      } else if (code.equals("ER")) {
        languages.put(code, "aa");
      } else if (code.equals("ES")) {
        languages.put(code, "es");
      } else if (code.equals("ET")) {
        languages.put(code, "am");
      } else if (code.equals("FI")) {
        languages.put(code, "fi");
      } else if (code.equals("FJ")) {
        languages.put(code, "en");
      } else if (code.equals("FK")) {
        languages.put(code, "en");
      } else if (code.equals("FM")) {
        languages.put(code, "en");
      } else if (code.equals("FO")) {
        languages.put(code, "fo");
      } else if (code.equals("FR")) {
        languages.put(code, "fr");
      } else if (code.equals("GA")) {
        languages.put(code, "fr");
      } else if (code.equals("GB")) {
        languages.put(code, "en");
      } else if (code.equals("GD")) {
        languages.put(code, "en");
      } else if (code.equals("GE")) {
        languages.put(code, "ka");
      } else if (code.equals("GF")) {
        languages.put(code, "fr");
      } else if (code.equals("GG")) {
        languages.put(code, "en");
      } else if (code.equals("GH")) {
        languages.put(code, "en");
      } else if (code.equals("GI")) {
        languages.put(code, "en");
      } else if (code.equals("GL")) {
        languages.put(code, "kl");
      } else if (code.equals("GM")) {
        languages.put(code, "en");
      } else if (code.equals("GN")) {
        languages.put(code, "fr");
      } else if (code.equals("GP")) {
        languages.put(code, "fr");
      } else if (code.equals("GQ")) {
        languages.put(code, "es");
      } else if (code.equals("GR")) {
        languages.put(code, "el");
      } else if (code.equals("GS")) {
        languages.put(code, "en");
      } else if (code.equals("GT")) {
        languages.put(code, "es");
      } else if (code.equals("GU")) {
        languages.put(code, "en");
      } else if (code.equals("GW")) {
        languages.put(code, "pt");
      } else if (code.equals("GY")) {
        languages.put(code, "en");
      } else if (code.equals("HK")) {
        languages.put(code, "zh");
      } else if (code.equals("HM")) {
        languages.put(code, "");
      } else if (code.equals("HN")) {
        languages.put(code, "es");
      } else if (code.equals("HR")) {
        languages.put(code, "hr");
      } else if (code.equals("HT")) {
        languages.put(code, "ht");
      } else if (code.equals("HU")) {
        languages.put(code, "hu");
      } else if (code.equals("ID")) {
        languages.put(code, "id");
      } else if (code.equals("IE")) {
        languages.put(code, "en");
      } else if (code.equals("IL")) {
        languages.put(code, "he");
      } else if (code.equals("IM")) {
        languages.put(code, "en");
      } else if (code.equals("IN")) {
        languages.put(code, "en");
      } else if (code.equals("IO")) {
        languages.put(code, "en");
      } else if (code.equals("IQ")) {
        languages.put(code, "ar");
      } else if (code.equals("IR")) {
        languages.put(code, "fa");
      } else if (code.equals("IS")) {
        languages.put(code, "is");
      } else if (code.equals("IT")) {
        languages.put(code, "it");
      } else if (code.equals("JE")) {
        languages.put(code, "en");
      } else if (code.equals("JM")) {
        languages.put(code, "en");
      } else if (code.equals("JO")) {
        languages.put(code, "ar");
      } else if (code.equals("JP")) {
        languages.put(code, "ja");
      } else if (code.equals("KE")) {
        languages.put(code, "en");
      } else if (code.equals("KG")) {
        languages.put(code, "ky");
      } else if (code.equals("KH")) {
        languages.put(code, "km");
      } else if (code.equals("KI")) {
        languages.put(code, "en");
      } else if (code.equals("KM")) {
        languages.put(code, "ar");
      } else if (code.equals("KN")) {
        languages.put(code, "en");
      } else if (code.equals("KP")) {
        languages.put(code, "ko");
      } else if (code.equals("KR")) {
        languages.put(code, "ko");
      } else if (code.equals("XK")) {
        languages.put(code, "sq");
      } else if (code.equals("KW")) {
        languages.put(code, "ar");
      } else if (code.equals("KY")) {
        languages.put(code, "en");
      } else if (code.equals("KZ")) {
        languages.put(code, "kk");
      } else if (code.equals("LA")) {
        languages.put(code, "lo");
      } else if (code.equals("LB")) {
        languages.put(code, "ar");
      } else if (code.equals("LC")) {
        languages.put(code, "en");
      } else if (code.equals("LI")) {
        languages.put(code, "de");
      } else if (code.equals("LK")) {
        languages.put(code, "si");
      } else if (code.equals("LR")) {
        languages.put(code, "en");
      } else if (code.equals("LS")) {
        languages.put(code, "en");
      } else if (code.equals("LT")) {
        languages.put(code, "lt");
      } else if (code.equals("LU")) {
        languages.put(code, "lb");
      } else if (code.equals("LV")) {
        languages.put(code, "lv");
      } else if (code.equals("LY")) {
        languages.put(code, "ar");
      } else if (code.equals("MA")) {
        languages.put(code, "ar");
      } else if (code.equals("MC")) {
        languages.put(code, "fr");
      } else if (code.equals("MD")) {
        languages.put(code, "ro");
      } else if (code.equals("ME")) {
        languages.put(code, "sr");
      } else if (code.equals("MF")) {
        languages.put(code, "fr");
      } else if (code.equals("MG")) {
        languages.put(code, "fr");
      } else if (code.equals("MH")) {
        languages.put(code, "mh");
      } else if (code.equals("MK")) {
        languages.put(code, "mk");
      } else if (code.equals("ML")) {
        languages.put(code, "fr");
      } else if (code.equals("MM")) {
        languages.put(code, "my");
      } else if (code.equals("MN")) {
        languages.put(code, "mn");
      } else if (code.equals("MO")) {
        languages.put(code, "zh");
      } else if (code.equals("MP")) {
        languages.put(code, "fil");
      } else if (code.equals("MQ")) {
        languages.put(code, "fr");
      } else if (code.equals("MR")) {
        languages.put(code, "ar");
      } else if (code.equals("MS")) {
        languages.put(code, "en");
      } else if (code.equals("MT")) {
        languages.put(code, "mt");
      } else if (code.equals("MU")) {
        languages.put(code, "en");
      } else if (code.equals("MV")) {
        languages.put(code, "dv");
      } else if (code.equals("MW")) {
        languages.put(code, "ny");
      } else if (code.equals("MX")) {
        languages.put(code, "es");
      } else if (code.equals("MY")) {
        languages.put(code, "ms");
      } else if (code.equals("MZ")) {
        languages.put(code, "pt");
      } else if (code.equals("NA")) {
        languages.put(code, "en");
      } else if (code.equals("NC")) {
        languages.put(code, "fr");
      } else if (code.equals("NE")) {
        languages.put(code, "fr");
      } else if (code.equals("NF")) {
        languages.put(code, "en");
      } else if (code.equals("NG")) {
        languages.put(code, "en");
      } else if (code.equals("NI")) {
        languages.put(code, "es");
      } else if (code.equals("NL")) {
        languages.put(code, "nl");
      } else if (code.equals("NO")) {
        languages.put(code, "no");
      } else if (code.equals("NP")) {
        languages.put(code, "ne");
      } else if (code.equals("NR")) {
        languages.put(code, "na");
      } else if (code.equals("NU")) {
        languages.put(code, "niu");
      } else if (code.equals("NZ")) {
        languages.put(code, "en");
      } else if (code.equals("OM")) {
        languages.put(code, "ar");
      } else if (code.equals("PA")) {
        languages.put(code, "es");
      } else if (code.equals("PE")) {
        languages.put(code, "es");
      } else if (code.equals("PF")) {
        languages.put(code, "fr");
      } else if (code.equals("PG")) {
        languages.put(code, "en");
      } else if (code.equals("PH")) {
        languages.put(code, "tl");
      } else if (code.equals("PK")) {
        languages.put(code, "ur");
      } else if (code.equals("PL")) {
        languages.put(code, "pl");
      } else if (code.equals("PM")) {
        languages.put(code, "fr");
      } else if (code.equals("PN")) {
        languages.put(code, "en");
      } else if (code.equals("PR")) {
        languages.put(code, "en");
      } else if (code.equals("PS")) {
        languages.put(code, "ar");
      } else if (code.equals("PT")) {
        languages.put(code, "pt");
      } else if (code.equals("PW")) {
        languages.put(code, "pau");
      } else if (code.equals("PY")) {
        languages.put(code, "es");
      } else if (code.equals("QA")) {
        languages.put(code, "ar");
      } else if (code.equals("RE")) {
        languages.put(code, "fr");
      } else if (code.equals("RO")) {
        languages.put(code, "ro");
      } else if (code.equals("RS")) {
        languages.put(code, "sr");
      } else if (code.equals("RU")) {
        languages.put(code, "ru");
      } else if (code.equals("RW")) {
        languages.put(code, "rw");
      } else if (code.equals("SA")) {
        languages.put(code, "ar");
      } else if (code.equals("SB")) {
        languages.put(code, "en");
      } else if (code.equals("SC")) {
        languages.put(code, "en");
      } else if (code.equals("SD")) {
        languages.put(code, "ar");
      } else if (code.equals("SS")) {
        languages.put(code, "en");
      } else if (code.equals("SE")) {
        languages.put(code, "sv");
      } else if (code.equals("SG")) {
        languages.put(code, "cmn");
      } else if (code.equals("SH")) {
        languages.put(code, "en");
      } else if (code.equals("SI")) {
        languages.put(code, "sl");
      } else if (code.equals("SJ")) {
        languages.put(code, "no");
      } else if (code.equals("SK")) {
        languages.put(code, "sk");
      } else if (code.equals("SL")) {
        languages.put(code, "en");
      } else if (code.equals("SM")) {
        languages.put(code, "it");
      } else if (code.equals("SN")) {
        languages.put(code, "fr");
      } else if (code.equals("SO")) {
        languages.put(code, "so");
      } else if (code.equals("SR")) {
        languages.put(code, "nl");
      } else if (code.equals("ST")) {
        languages.put(code, "pt");
      } else if (code.equals("SV")) {
        languages.put(code, "es");
      } else if (code.equals("SX")) {
        languages.put(code, "nl");
      } else if (code.equals("SY")) {
        languages.put(code, "ar");
      } else if (code.equals("SZ")) {
        languages.put(code, "en");
      } else if (code.equals("TC")) {
        languages.put(code, "en");
      } else if (code.equals("TD")) {
        languages.put(code, "fr");
      } else if (code.equals("TF")) {
        languages.put(code, "fr");
      } else if (code.equals("TG")) {
        languages.put(code, "fr");
      } else if (code.equals("TH")) {
        languages.put(code, "th");
      } else if (code.equals("TJ")) {
        languages.put(code, "tg");
      } else if (code.equals("TK")) {
        languages.put(code, "tkl");
      } else if (code.equals("TL")) {
        languages.put(code, "tet");
      } else if (code.equals("TM")) {
        languages.put(code, "tk");
      } else if (code.equals("TN")) {
        languages.put(code, "ar");
      } else if (code.equals("TO")) {
        languages.put(code, "to");
      } else if (code.equals("TR")) {
        languages.put(code, "tr");
      } else if (code.equals("TT")) {
        languages.put(code, "en");
      } else if (code.equals("TV")) {
        languages.put(code, "tvl");
      } else if (code.equals("TW")) {
        languages.put(code, "zh");
      } else if (code.equals("TZ")) {
        languages.put(code, "sw");
      } else if (code.equals("UA")) {
        languages.put(code, "uk");
      } else if (code.equals("UG")) {
        languages.put(code, "en");
      } else if (code.equals("UM")) {
        languages.put(code, "en");
      } else if (code.equals("US")) {
        languages.put(code, "en");
      } else if (code.equals("UY")) {
        languages.put(code, "es");
      } else if (code.equals("UZ")) {
        languages.put(code, "uz");
      } else if (code.equals("VA")) {
        languages.put(code, "la");
      } else if (code.equals("VC")) {
        languages.put(code, "en");
      } else if (code.equals("VE")) {
        languages.put(code, "es");
      } else if (code.equals("VG")) {
        languages.put(code, "en");
      } else if (code.equals("VI")) {
        languages.put(code, "en");
      } else if (code.equals("VN")) {
        languages.put(code, "vi");
      } else if (code.equals("VU")) {
        languages.put(code, "bi");
      } else if (code.equals("WF")) {
        languages.put(code, "wls");
      } else if (code.equals("WS")) {
        languages.put(code, "sm");
      } else if (code.equals("YE")) {
        languages.put(code, "ar");
      } else if (code.equals("YT")) {
        languages.put(code, "fr");
      } else if (code.equals("ZA")) {
        languages.put(code, "zu");
      } else if (code.equals("ZM")) {
        languages.put(code, "en");
      } else if (code.equals("ZW")) {
        languages.put(code, "en");
      } else if (code.equals("CS")) {
        languages.put(code, "cu");
      } else if (code.equals("AN")) {
        languages.put(code, "nl");
      }
    }
  }

}
