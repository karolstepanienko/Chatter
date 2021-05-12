package com.chatter.model.Constants;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public final class Roles {
  private static String userRole = "USER";
  private static String adminRole = "ADMIN";

  public static String getUserRole() {
    return userRole;
  }

  public static String getAdminRole() {
    return adminRole;
  }

  public static Map<String, String> getAllRoles() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("userRole", userRole);
    map.put("adminRole", adminRole);
    return map;
  }
}
