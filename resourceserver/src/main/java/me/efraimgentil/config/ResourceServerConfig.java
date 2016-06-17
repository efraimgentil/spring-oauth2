package me.efraimgentil.config;

import me.efraimgentil.filter.CORSFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 16/02/16.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true , securedEnabled = false, proxyTargetClass = true)
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {



  @Autowired
  TokenStore tokenStore;

  private TokenExtractor tokenExtractor = new BearerTokenExtractor();

  @Override
  public void configure(final HttpSecurity http) throws Exception {
    http.addFilterBefore( new CORSFilter() , AbstractPreAuthenticatedProcessingFilter.class  )
            .addFilterAfter(new OncePerRequestFilter() {
              @Override
              protected void doFilterInternal(HttpServletRequest request,
                                              HttpServletResponse response, FilterChain filterChain)
                      throws ServletException, IOException {
                // We don't want to allow access to a resource with no token so clear
                // the security context in case it is actually an OAuth2Authentication
                /*if (tokenExtractor.extract(request) == null) {
                  SecurityContextHolder.clearContext();
                }*/
                filterChain.doFilter(request, response);
              }
            }, AbstractPreAuthenticatedProcessingFilter.class);
    http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.NEVER)
            .and().authorizeRequests()
            .anyRequest().authenticated();
//            .requestMatchers().antMatchers("/foos/**","/bars/**")
//            .and()
//            .authorizeRequests()
//                .antMatchers(HttpMethod.GET,"/foos/**").access("#oauth2.hasScope('foo') and #oauth2.hasScope('read')")
//                .antMatchers(HttpMethod.POST,"/foos/**").access("#oauth2.hasScope('foo') and #oauth2.hasScope('write')")
//                .antMatchers(HttpMethod.GET,"/bars/**").access("#oauth2.hasScope('bar') and #oauth2.hasScope('read')")
//                .antMatchers(HttpMethod.POST,"/bars/**").access("#oauth2.hasScope('bar') and #oauth2.hasScope('write') and hasRole('ROLE_ADMIN')")
    // @formatter:on
  }

  /*@Bean
  public AuthenticationManager authenticationManager(){
    DefaultTokenServices defaultTokenServices = tokenServices();
    OAuth2AuthenticationManager oAuth2AuthenticationManager = new OAuth2AuthenticationManager();
    oAuth2AuthenticationManager.setTokenServices( defaultTokenServices );
    return oAuth2AuthenticationManager;
  }*/

  @Override
  public void configure(final ResourceServerSecurityConfigurer config) {
    config.tokenServices(tokenServices());
    config.resourceId("resource1");
  }

  @Bean
  @Primary
  public DefaultTokenServices tokenServices() {
    final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(tokenStore);
    return defaultTokenServices;
  }

  @Bean
  public TokenStore tokenStore( DataSource ds){
    return new JdbcTokenStore( ds );
  }

}
