package com.chatter.controllers.ConstantsController;

import java.util.Map;

import javax.management.relation.Role;

import com.chatter.model.Constants.Roles;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/constants")
public class ConstantsController {

  @CrossOrigin
  @GetMapping("/roles")
  public @ResponseBody Map<String, String> getAllRoles() {
    return Roles.getAllRoles();
  }  
}
