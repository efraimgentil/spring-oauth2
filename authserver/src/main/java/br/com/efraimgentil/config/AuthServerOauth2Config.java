package br.com.efraimgentil.config;

import br.com.efraimgentil.service.MyClientDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 16/02/16.
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerOauth2Config extends AuthorizationServerConfigurerAdapter {

  @Autowired
  AuthenticationManager authenticationManager;
  @Autowired
  TokenStore tokenStore;
  @Autowired
  MyClientDetailService clientDetailService;



  @Override
  public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
    oauthServer
            .tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()");
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients)
          throws Exception {
    clients.withClientDetails( clientDetailService );
    /*clients.inMemory()
            .withClient("user")
            .resourceIds("my")
            .secret("password")
            .authorizedGrantTypes("authorization_code", "refresh_token", "password", "implicit")
            .scopes("openid")
            .and()
            .withClient("myfrontapp")
            .authorizedGrantTypes("implicit")
            .scopes("read")
            .autoApprove(true);*/
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.tokenStore(tokenStore).authenticationManager(authenticationManager);
  }

  /*
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
      endpoints.authenticationManager(authenticationManager);
      super.configure(endpoints);
    }*/
  @Bean
  public TokenStore tokenStore(DataSource ds) {
    return new JdbcTokenStore(ds);
    /*return new InMemoryTokenStore();*/
  }


}
