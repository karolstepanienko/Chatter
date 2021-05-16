package com.chatter.model.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public final class AccountPrivacies {

  /**
   * Public account privacy.
   */
  private static String publicAccess = "PUBLIC";

  /**
   * Friend only account privacy.
   */
  private static String friendOnlyAccess = "FRIEND_ONLY";

  /**
   * Private account privacy.
   */
  private static String privateAccess = "PRIVATE";

  private AccountPrivacies() { }

  /**
   * Public account privacy getter.
   * @return Public account privacy.
   */
  public static String getPublicAccess() {
    return publicAccess;
  }

  /**
   * Friend only account privacy getter.
   * @return Friend only account privacy.
   */
  public static String getFriendOnlyAccess() {
    return friendOnlyAccess;
  }

  /**
   * Private account privacy getter.
   * @return Private account privacy.
   */
  public static String getPrivateAccess() {
    return privateAccess;
  }

  /**
   * All privacies getter.
   * @return All privacies as HashMap.
   */
  public static Map<String, String> getAllAccess() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("publicAccess", publicAccess);
    map.put("friendOnlyAccess", friendOnlyAccess);
    map.put("privateAccess", privateAccess);
    return map;
  }

  /**
   * All privacies getter.
   * @return All privacies as List.
   */
  public static List<String> getAllList() {
    List<String> list = new ArrayList<String>();
    list.add(publicAccess);
    list.add(friendOnlyAccess);
    list.add(privateAccess);
    return list;
  }
}
