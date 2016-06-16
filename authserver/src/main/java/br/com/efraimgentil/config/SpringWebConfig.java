package br.com.efraimgentil.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 16/02/16.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "br.com.efraimgentil.controller" } )
@Import( { SpringConfig.class , DatabaseConfig.class,  SecurityConfig.class , AuthServerOauth2Config.class } )
public class SpringWebConfig extends WebMvcConfigurerAdapter {



}
