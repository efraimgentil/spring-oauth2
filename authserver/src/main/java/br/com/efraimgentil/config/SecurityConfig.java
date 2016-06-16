package br.com.efraimgentil.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.Md4PasswordEncoder;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import javax.sql.DataSource;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 16/02/16.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  DataSource dataSource;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/login").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
             .permitAll();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery("SELECT login as username , password , true FROM  public.tb_usuario u where u.login = ? ")
            .authoritiesByUsernameQuery("SELECT login as username , permissao as role FROM public.tb_usuario_permissao WHERE login = ?")
            .passwordEncoder(new Md5PasswordEncoder() );
  }

  @Bean
  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

}