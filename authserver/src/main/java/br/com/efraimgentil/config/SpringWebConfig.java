package br.com.efraimgentil.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 16/02/16.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "br.com.efraimgentil.controller" } )
@Import( { SpringConfig.class , DatabaseConfig.class,  SecurityConfig.class , AuthServerOauth2Config.class , ResourceServerConfig.class } )
public class SpringWebConfig extends WebMvcConfigurerAdapter {

  @Bean
  public ViewResolver configureUrlBasedViewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/views/");
    resolver.setSuffix(".jsp");
    return resolver;
  }

}

