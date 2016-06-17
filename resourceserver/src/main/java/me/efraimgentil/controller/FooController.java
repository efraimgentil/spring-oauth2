package me.efraimgentil.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 24/05/16.
 */
@Controller
public class FooController {

  @PreAuthorize("#oauth2.hasScope('openid')")
  @RequestMapping(method = RequestMethod.GET, value = "/")
  @ResponseBody
  public String findById() {
    return "HEY ";
  }


  @PreAuthorize("#oauth2.clientHasRole('PERM_USUARIO_LISTAR')")
  @RequestMapping(method = RequestMethod.GET, value = "/hey")
  @ResponseBody
  public String teste() {
    SecurityContext context = SecurityContextHolder.getContext();
    Authentication authentication = context.getAuthentication();
    User user = (User) authentication.getPrincipal();
    System.out.println("authentication = " + user.getUsername() );
    return "HEY YEEEE HADUKKKEEEEEN";
  }

}