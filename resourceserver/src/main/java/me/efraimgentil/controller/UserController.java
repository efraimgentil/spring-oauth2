package me.efraimgentil.controller;

import me.efraimgentil.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
      u.setName((String) map.get("nome") );
      usuarios.add( u );
    }
    return usuarios;
  }

  @RequestMapping(value = { "/" , "" } , method = RequestMethod.POST)
  public User newUser(@RequestBody User user){
    SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("public.tb_usuario")
            .usingColumns("login" , "password" , "nome")
            .usingGeneratedKeyColumns("id");
    Map<String , Object> parameters = new HashMap<>();
    parameters.put("login" , user.getLogin() );
    parameters.put("password", new Md5PasswordEncoder().encodePassword(user.getLogin(), null) );
    parameters.put("nome" , user.getName() );
    Number number = insert.executeAndReturnKey(parameters);
    return jdbcTemplate.queryForObject("SELECT * FROM public.tb_usuario where id = ?", new Object[]{number}, new RowMapper<User>() {
      @Override
      public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User u = new User();
        u.setId( resultSet.getInt("id"));
        u.setLogin( resultSet.getString("login") );
        u.setName( resultSet.getString("nome") );
        return u ;
      }
    });
  }

  @PreAuthorize(value = "#oauth2.clientHasRole('PERM_USUARIO_VER_TOKENS')")
  public List<OAuth2AccessToken> tokensDoUsuario(String usuario){
    Collection<OAuth2AccessToken> tokensByUserName = tokenStore.findTokensByUserName(usuario);
    return new ArrayList<OAuth2AccessToken>( tokensByUserName );
  }

}