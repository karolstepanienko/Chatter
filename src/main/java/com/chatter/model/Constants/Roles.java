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
   * MODERATOR user role.
   */
  private static String moderatorRole = "MODERATOR";

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
   * MODERATOR role getter.
   * @return MODERATOR role.
   */
  public static String getModeratorRole() {
    return moderatorRole;
  }

  /**
   * ADMIN role getter.
   * @return ADMIN role.
   */
  public static String getAdminRole() {
    return adminRole;
  }

  public static Boolean roleExists(String role) {
    return getAllRoles().values().contains(role);
  }

  /**
   * All roles getter.
   * @return All roles as HashMap.
   */
  public static Map<String, String> getAllRoles() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("userRole", userRole);
    map.put("moderatorRole", moderatorRole);
    map.put("adminRole", adminRole);
    return map;
  }
}
