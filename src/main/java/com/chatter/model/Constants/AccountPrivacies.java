package com.chatter.model.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class AccountPrivacies {
  private static String publicAccess = "PUBLIC";
  private static String friendOnlyAccess = "FRIEND_ONLY";
  private static String privateAccess = "PRIVATE";

  public static String getPublicAccess() {
    return publicAccess;
  }

  public static String getFriendOnlyAccess() {
    return friendOnlyAccess;
  }
  
  public static String getPrivateAccess() {
    return privateAccess;
  }

  public static Map<String, String> getAllAccess() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("publicAccess", publicAccess);
    map.put("friendOnlyAccess", friendOnlyAccess);
    map.put("privateAccess", privateAccess);
    return map;
  }

  public static List<String> getAllList() {
    List<String> list = new ArrayList<String>();
    list.add(publicAccess);
    list.add(friendOnlyAccess);
    list.add(privateAccess);
    return list;
  }
}
