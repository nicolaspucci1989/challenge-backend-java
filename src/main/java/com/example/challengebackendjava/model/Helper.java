package com.example.challengebackendjava.model;

import java.util.Locale;

public class Helper {
  public static boolean stringsCoinciden(String str1, String str2) {
    return str1.toLowerCase(Locale.ROOT)
        .contains(str2.toLowerCase(Locale.ROOT));
  }
}
