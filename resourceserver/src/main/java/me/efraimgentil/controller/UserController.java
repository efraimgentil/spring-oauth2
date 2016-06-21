package me.efraimgentil.controller;

import me.efraimgentil.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 16/06/16.
 */
@RestController
@RequestMapping(value = { "/user" } )
public class UserController {

  @Autowired
  JdbcTokenStore tokenStore;
  @Autowired
  JdbcTemplate jdbcTemplate;


  @PreAuthorize(value = "#oauth2.clientHasRole('PERM_USUARIO_LISTAR')")
  @RequestMapping(value = { "/" , "" } , method = RequestMethod.GET)
  public List<User> usuarios(){
    List<User> usuarios = new ArrayList<>();
    List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from public.tb_usuario");
    for(Map<String,Object> map : maps){
      User u = new User();
      u.setId((Integer) map.get("id"));
      u.setLogin((String) map.get("login") );
      u.setNome((String) map.get("nome") );
      usuarios.add( u );
    }
    return usuarios;
  }

  @PreAuthorize(value = "#oauth2.clientHasRole('PERM_USUARIO_VER_TOKENS')")
  public List<OAuth2AccessToken> tokensDoUsuario(String usuario){
    Collection<OAuth2AccessToken> tokensByUserName = tokenStore.findTokensByUserName(usuario);
    return new ArrayList<OAuth2AccessToken>( tokensByUserName );
  }

}