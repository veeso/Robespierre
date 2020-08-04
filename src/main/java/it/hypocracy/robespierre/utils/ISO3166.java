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
      switch (code) {
        case "AD":
          languages.put(code, "ca");
          break;
        case "AE":
          languages.put(code, "ar");
          break;
        case "AF":
          languages.put(code, "fa");
          break;
        case "AG":
          languages.put(code, "en");
          break;
        case "AI":
          languages.put(code, "en");
          break;
        case "AL":
          languages.put(code, "sq");
          break;
        case "AM":
          languages.put(code, "hy");
          break;
        case "AO":
          languages.put(code, "pt");
          break;
        case "AQ":
          languages.put(code, "");
          break;
        case "AR":
          languages.put(code, "es");
          break;
        case "AS":
          languages.put(code, "en");
          break;
        case "AT":
          languages.put(code, "de");
          break;
        case "AU":
          languages.put(code, "en");
          break;
        case "AW":
          languages.put(code, "nl");
          break;
        case "AX":
          languages.put(code, "sv");
          break;
        case "AZ":
          languages.put(code, "az");
          break;
        case "BA":
          languages.put(code, "bs");
          break;
        case "BB":
          languages.put(code, "en");
          break;
        case "BD":
          languages.put(code, "bn");
          break;
        case "BE":
          languages.put(code, "nl");
          break;
        case "BF":
          languages.put(code, "fr");
          break;
        case "BG":
          languages.put(code, "bg");
          break;
        case "BH":
          languages.put(code, "ar");
          break;
        case "BI":
          languages.put(code, "fr");
          break;
        case "BJ":
          languages.put(code, "fr");
          break;
        case "BL":
          languages.put(code, "fr");
          break;
        case "BM":
          languages.put(code, "en");
          break;
        case "BN":
          languages.put(code, "ms");
          break;
        case "BO":
          languages.put(code, "es");
          break;
        case "BQ":
          languages.put(code, "nl");
          break;
        case "BR":
          languages.put(code, "pt");
          break;
        case "BS":
          languages.put(code, "en");
          break;
        case "BT":
          languages.put(code, "dz");
          break;
        case "BV":
          languages.put(code, "");
          break;
        case "BW":
          languages.put(code, "en");
          break;
        case "BY":
          languages.put(code, "be");
          break;
        case "BZ":
          languages.put(code, "en");
          break;
        case "CA":
          languages.put(code, "en");
          break;
        case "CC":
          languages.put(code, "ms");
          break;
        case "CD":
          languages.put(code, "fr");
          break;
        case "CF":
          languages.put(code, "fr");
          break;
        case "CG":
          languages.put(code, "fr");
          break;
        case "CH":
          languages.put(code, "de");
          break;
        case "CI":
          languages.put(code, "fr");
          break;
        case "CK":
          languages.put(code, "en");
          break;
        case "CL":
          languages.put(code, "es");
          break;
        case "CM":
          languages.put(code, "en");
          break;
        case "CN":
          languages.put(code, "zh");
          break;
        case "CO":
          languages.put(code, "es");
          break;
        case "CR":
          languages.put(code, "es");
          break;
        case "CU":
          languages.put(code, "es");
          break;
        case "CV":
          languages.put(code, "pt");
          break;
        case "CW":
          languages.put(code, "nl");
          break;
        case "CX":
          languages.put(code, "en");
          break;
        case "CY":
          languages.put(code, "el");
          break;
        case "CZ":
          languages.put(code, "cs");
          break;
        case "DE":
          languages.put(code, "de");
          break;
        case "DJ":
          languages.put(code, "fr");
          break;
        case "DK":
          languages.put(code, "da");
          break;
        case "DM":
          languages.put(code, "en");
          break;
        case "DO":
          languages.put(code, "es");
          break;
        case "DZ":
          languages.put(code, "ar");
          break;
        case "EC":
          languages.put(code, "es");
          break;
        case "EE":
          languages.put(code, "et");
          break;
        case "EG":
          languages.put(code, "ar");
          break;
        case "EH":
          languages.put(code, "ar");
          break;
        case "ER":
          languages.put(code, "aa");
          break;
        case "ES":
          languages.put(code, "es");
          break;
        case "ET":
          languages.put(code, "am");
          break;
        case "FI":
          languages.put(code, "fi");
          break;
        case "FJ":
          languages.put(code, "en");
          break;
        case "FK":
          languages.put(code, "en");
          break;
        case "FM":
          languages.put(code, "en");
          break;
        case "FO":
          languages.put(code, "fo");
          break;
        case "FR":
          languages.put(code, "fr");
          break;
        case "GA":
          languages.put(code, "fr");
          break;
        case "GB":
          languages.put(code, "en");
          break;
        case "GD":
          languages.put(code, "en");
          break;
        case "GE":
          languages.put(code, "ka");
          break;
        case "GF":
          languages.put(code, "fr");
          break;
        case "GG":
          languages.put(code, "en");
          break;
        case "GH":
          languages.put(code, "en");
          break;
        case "GI":
          languages.put(code, "en");
          break;
        case "GL":
          languages.put(code, "kl");
          break;
        case "GM":
          languages.put(code, "en");
          break;
        case "GN":
          languages.put(code, "fr");
          break;
        case "GP":
          languages.put(code, "fr");
          break;
        case "GQ":
          languages.put(code, "es");
          break;
        case "GR":
          languages.put(code, "el");
          break;
        case "GS":
          languages.put(code, "en");
          break;
        case "GT":
          languages.put(code, "es");
          break;
        case "GU":
          languages.put(code, "en");
          break;
        case "GW":
          languages.put(code, "pt");
          break;
        case "GY":
          languages.put(code, "en");
          break;
        case "HK":
          languages.put(code, "zh");
          break;
        case "HM":
          languages.put(code, "");
          break;
        case "HN":
          languages.put(code, "es");
          break;
        case "HR":
          languages.put(code, "hr");
          break;
        case "HT":
          languages.put(code, "ht");
          break;
        case "HU":
          languages.put(code, "hu");
          break;
        case "ID":
          languages.put(code, "id");
          break;
        case "IE":
          languages.put(code, "en");
          break;
        case "IL":
          languages.put(code, "he");
          break;
        case "IM":
          languages.put(code, "en");
          break;
        case "IN":
          languages.put(code, "en");
          break;
        case "IO":
          languages.put(code, "en");
          break;
        case "IQ":
          languages.put(code, "ar");
          break;
        case "IR":
          languages.put(code, "fa");
          break;
        case "IS":
          languages.put(code, "is");
          break;
        case "IT":
          languages.put(code, "it");
          break;
        case "JE":
          languages.put(code, "en");
          break;
        case "JM":
          languages.put(code, "en");
          break;
        case "JO":
          languages.put(code, "ar");
          break;
        case "JP":
          languages.put(code, "ja");
          break;
        case "KE":
          languages.put(code, "en");
          break;
        case "KG":
          languages.put(code, "ky");
          break;
        case "KH":
          languages.put(code, "km");
          break;
        case "KI":
          languages.put(code, "en");
          break;
        case "KM":
          languages.put(code, "ar");
          break;
        case "KN":
          languages.put(code, "en");
          break;
        case "KP":
          languages.put(code, "ko");
          break;
        case "KR":
          languages.put(code, "ko");
          break;
        case "XK":
          languages.put(code, "sq");
          break;
        case "KW":
          languages.put(code, "ar");
          break;
        case "KY":
          languages.put(code, "en");
          break;
        case "KZ":
          languages.put(code, "kk");
          break;
        case "LA":
          languages.put(code, "lo");
          break;
        case "LB":
          languages.put(code, "ar");
          break;
        case "LC":
          languages.put(code, "en");
          break;
        case "LI":
          languages.put(code, "de");
          break;
        case "LK":
          languages.put(code, "si");
          break;
        case "LR":
          languages.put(code, "en");
          break;
        case "LS":
          languages.put(code, "en");
          break;
        case "LT":
          languages.put(code, "lt");
          break;
        case "LU":
          languages.put(code, "lb");
          break;
        case "LV":
          languages.put(code, "lv");
          break;
        case "LY":
          languages.put(code, "ar");
          break;
        case "MA":
          languages.put(code, "ar");
          break;
        case "MC":
          languages.put(code, "fr");
          break;
        case "MD":
          languages.put(code, "ro");
          break;
        case "ME":
          languages.put(code, "sr");
          break;
        case "MF":
          languages.put(code, "fr");
          break;
        case "MG":
          languages.put(code, "fr");
          break;
        case "MH":
          languages.put(code, "mh");
          break;
        case "MK":
          languages.put(code, "mk");
          break;
        case "ML":
          languages.put(code, "fr");
          break;
        case "MM":
          languages.put(code, "my");
          break;
        case "MN":
          languages.put(code, "mn");
          break;
        case "MO":
          languages.put(code, "zh");
          break;
        case "MP":
          languages.put(code, "fil");
          break;
        case "MQ":
          languages.put(code, "fr");
          break;
        case "MR":
          languages.put(code, "ar");
          break;
        case "MS":
          languages.put(code, "en");
          break;
        case "MT":
          languages.put(code, "mt");
          break;
        case "MU":
          languages.put(code, "en");
          break;
        case "MV":
          languages.put(code, "dv");
          break;
        case "MW":
          languages.put(code, "ny");
          break;
        case "MX":
          languages.put(code, "es");
          break;
        case "MY":
          languages.put(code, "ms");
          break;
        case "MZ":
          languages.put(code, "pt");
          break;
        case "NA":
          languages.put(code, "en");
          break;
        case "NC":
          languages.put(code, "fr");
          break;
        case "NE":
          languages.put(code, "fr");
          break;
        case "NF":
          languages.put(code, "en");
          break;
        case "NG":
          languages.put(code, "en");
          break;
        case "NI":
          languages.put(code, "es");
          break;
        case "NL":
          languages.put(code, "nl");
          break;
        case "NO":
          languages.put(code, "no");
          break;
        case "NP":
          languages.put(code, "ne");
          break;
        case "NR":
          languages.put(code, "na");
          break;
        case "NU":
          languages.put(code, "niu");
          break;
        case "NZ":
          languages.put(code, "en");
          break;
        case "OM":
          languages.put(code, "ar");
          break;
        case "PA":
          languages.put(code, "es");
          break;
        case "PE":
          languages.put(code, "es");
          break;
        case "PF":
          languages.put(code, "fr");
          break;
        case "PG":
          languages.put(code, "en");
          break;
        case "PH":
          languages.put(code, "tl");
          break;
        case "PK":
          languages.put(code, "ur");
          break;
        case "PL":
          languages.put(code, "pl");
          break;
        case "PM":
          languages.put(code, "fr");
          break;
        case "PN":
          languages.put(code, "en");
          break;
        case "PR":
          languages.put(code, "en");
          break;
        case "PS":
          languages.put(code, "ar");
          break;
        case "PT":
          languages.put(code, "pt");
          break;
        case "PW":
          languages.put(code, "pau");
          break;
        case "PY":
          languages.put(code, "es");
          break;
        case "QA":
          languages.put(code, "ar");
          break;
        case "RE":
          languages.put(code, "fr");
          break;
        case "RO":
          languages.put(code, "ro");
          break;
        case "RS":
          languages.put(code, "sr");
          break;
        case "RU":
          languages.put(code, "ru");
          break;
        case "RW":
          languages.put(code, "rw");
          break;
        case "SA":
          languages.put(code, "ar");
          break;
        case "SB":
          languages.put(code, "en");
          break;
        case "SC":
          languages.put(code, "en");
          break;
        case "SD":
          languages.put(code, "ar");
          break;
        case "SS":
          languages.put(code, "en");
          break;
        case "SE":
          languages.put(code, "sv");
          break;
        case "SG":
          languages.put(code, "cmn");
          break;
        case "SH":
          languages.put(code, "en");
          break;
        case "SI":
          languages.put(code, "sl");
          break;
        case "SJ":
          languages.put(code, "no");
          break;
        case "SK":
          languages.put(code, "sk");
          break;
        case "SL":
          languages.put(code, "en");
          break;
        case "SM":
          languages.put(code, "it");
          break;
        case "SN":
          languages.put(code, "fr");
          break;
        case "SO":
          languages.put(code, "so");
          break;
        case "SR":
          languages.put(code, "nl");
          break;
        case "ST":
          languages.put(code, "pt");
          break;
        case "SV":
          languages.put(code, "es");
          break;
        case "SX":
          languages.put(code, "nl");
          break;
        case "SY":
          languages.put(code, "ar");
          break;
        case "SZ":
          languages.put(code, "en");
          break;
        case "TC":
          languages.put(code, "en");
          break;
        case "TD":
          languages.put(code, "fr");
          break;
        case "TF":
          languages.put(code, "fr");
          break;
        case "TG":
          languages.put(code, "fr");
          break;
        case "TH":
          languages.put(code, "th");
          break;
        case "TJ":
          languages.put(code, "tg");
          break;
        case "TK":
          languages.put(code, "tkl");
          break;
        case "TL":
          languages.put(code, "tet");
          break;
        case "TM":
          languages.put(code, "tk");
          break;
        case "TN":
          languages.put(code, "ar");
          break;
        case "TO":
          languages.put(code, "to");
          break;
        case "TR":
          languages.put(code, "tr");
          break;
        case "TT":
          languages.put(code, "en");
          break;
        case "TV":
          languages.put(code, "tvl");
          break;
        case "TW":
          languages.put(code, "zh");
          break;
        case "TZ":
          languages.put(code, "sw");
          break;
        case "UA":
          languages.put(code, "uk");
          break;
        case "UG":
          languages.put(code, "en");
          break;
        case "UM":
          languages.put(code, "en");
          break;
        case "US":
          languages.put(code, "en");
          break;
        case "UY":
          languages.put(code, "es");
          break;
        case "UZ":
          languages.put(code, "uz");
          break;
        case "VA":
          languages.put(code, "la");
          break;
        case "VC":
          languages.put(code, "en");
          break;
        case "VE":
          languages.put(code, "es");
          break;
        case "VG":
          languages.put(code, "en");
          break;
        case "VI":
          languages.put(code, "en");
          break;
        case "VN":
          languages.put(code, "vi");
          break;
        case "VU":
          languages.put(code, "bi");
          break;
        case "WF":
          languages.put(code, "wls");
          break;
        case "WS":
          languages.put(code, "sm");
          break;
        case "YE":
          languages.put(code, "ar");
          break;
        case "YT":
          languages.put(code, "fr");
          break;
        case "ZA":
          languages.put(code, "zu");
          break;
        case "ZM":
          languages.put(code, "en");
          break;
        case "ZW":
          languages.put(code, "en");
          break;
        case "CS":
          languages.put(code, "cu");
          break;
        case "AN":
          languages.put(code, "nl");
          break;
      }
    }
  }

}
