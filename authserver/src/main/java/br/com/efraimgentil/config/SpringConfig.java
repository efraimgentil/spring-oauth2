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
@ComponentScan(basePackages = { "br.com.efraimgentil.service" } )
public class SpringConfig  {



}
