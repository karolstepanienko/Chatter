package com.chatter.model.Constants;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public final class Roles {

  /**
   * USER user role.
   */
  private static String userRole = "USER";

  /**
   * ADMIN user role.
   */
  private static String adminRole = "ADMIN";

  private Roles() { }

  /**
   * USER role getter.
   * @return USER role.
   */
  public static String getUserRole() {
    return userRole;
  }

  /**
   * ADMIN role getter.
   * @return ADMIN role.
   */
  public static String getAdminRole() {
    return adminRole;
  }

  /**
   * All roles getter.
   * @return All roles as HashMap.
   */
  public static Map<String, String> getAllRoles() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("userRole", userRole);
    map.put("adminRole", adminRole);
    return map;
  }
}
