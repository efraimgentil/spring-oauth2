package me.efraimgentil.controller;

import me.efraimgentil.config.DatabaseConfig;
import me.efraimgentil.config.ResourceServerConfig;
import me.efraimgentil.config.SpringWebConfig;
import me.efraimgentil.config.WebSecurityConfig;
import me.efraimgentil.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 16/06/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SpringWebConfig.class , DatabaseConfig.class , ResourceServerConfig.class , WebSecurityConfig.class})
public class UsuarioControllerIT {

  @Autowired
  UserController usuarioController;
  OAuth2Authentication authentication;
  OAuth2Request oAuth2Request;

  @Autowired
  JdbcTemplate jdbcTemplate;


  @Before
  public void setUp(){
    authentication =  Mockito.mock(OAuth2Authentication.class);
    oAuth2Request = Mockito.mock(OAuth2Request.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when( securityContext.getAuthentication() ).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
  }

  @Test
  public void teste(){
    List<User> usuarios = jdbcTemplate.queryForList("SELECT * FROM public.tb_usuario", User.class);
    System.out.println("usuarios = " + usuarios);
  }

  @Test
  public void deveRetornarUsuarioCadastrados(){
    Mockito.when(authentication.isAuthenticated()).thenReturn(true);
    Collection<GrantedAuthority> perm_usuario_listar = Arrays.asList(new SimpleGrantedAuthority("PERM_USUARIO_LISTAR"));
    Mockito.doReturn( perm_usuario_listar ).when(authentication ).getAuthorities();

    List<User> usuarios = usuarioController.usuarios();

    System.out.println("usuarios = " + usuarios);
  }

  @Test
  public void deveLancarErroCasoUsuarioNaoTenhaPermissao(){
    Mockito.when(authentication.isAuthenticated()).thenReturn(true);
    Collection<GrantedAuthority> perm_usuario_listar = Arrays.asList(new SimpleGrantedAuthority("PERM_OTHER_PERM"));
    Mockito.doReturn( perm_usuario_listar ).when(authentication ).getAuthorities();

    List<User> usuarios = usuarioController.usuarios();

    System.out.println("usuarios = " + usuarios);
  }


}

