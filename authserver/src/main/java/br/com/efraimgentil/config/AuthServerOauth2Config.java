package br.com.efraimgentil.config;

import br.com.efraimgentil.service.MyClientDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.util.Arrays;

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
    TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
    tokenEnhancerChain.setTokenEnhancers(
            Arrays.asList( new MyTokenEnhancer() , accessTokenConverter()));


    endpoints.tokenStore(tokenStore)
            .accessTokenConverter(accessTokenConverter())
            .authenticationManager(authenticationManager);
  }

  /*
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
      endpoints.authenticationManager(authenticationManager);
      super.configure(endpoints);
    }*/

  @Bean
  public TokenStore tokenStore(DataSource ds ) {
    JwtTokenStore jwtTokenStore = new JwtTokenStore( accessTokenConverter() );
    return jwtTokenStore;
    /*RedisTokenStore redisTokenStore = new RedisTokenStore(redis);
    return redisTokenStore; */
    //return new JdbcTokenStore(ds);
    /*return new InMemoryTokenStore();*/
  }

  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setAccessTokenConverter(new MyAccessTokenConverter() );
    converter.setSigningKey("123456");
    return converter;
  }

  @Bean
  @Primary
  public DefaultTokenServices tokenServices(TokenStore tokenStore) {
    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(tokenStore);
    return defaultTokenServices;
  }


  /*@Bean
  public RedisConnectionFactory redisFactory(){
      return new JedisConnectionFactory();
  }*/


}
