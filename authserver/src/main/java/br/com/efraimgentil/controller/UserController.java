package br.com.efraimgentil.controller;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 14/02/16.
 */
@RestController
public class UserController {

  @RequestMapping("/user")
  public Principal user(Principal user) {
    return user;
  }

  @RequestMapping(value=" /user-o" ,method = RequestMethod.GET)
  public OAuth2Authentication read(OAuth2Authentication auth) {
    return auth;
  }

}
