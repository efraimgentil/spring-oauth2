package br.com.efraimgentil.controller;

import br.com.efraimgentil.service.MyClientDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 14/02/16.
 */
@RestController
public class UserController {

  @Autowired
  MyClientDetailService myClientDetailService;

  @RequestMapping("/user")
  public Principal user(Principal user) {
    return user;
  }

  @RequestMapping(value=" /user-o" ,method = RequestMethod.GET)
  public OAuth2Authentication read(OAuth2Authentication auth) {
    return auth;
  }

  @RequestMapping(value= "/ws/user-permissions" , method = RequestMethod.GET)
  public List<String> userPermissions(String username , String client){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(authentication.getName().equals( username )){
      return myClientDetailService.userPermissions(username, client);
    }else{
      throw new AccessDeniedException("Usuário não tem permissão para acessar esse recurso");
    }
  }

}
