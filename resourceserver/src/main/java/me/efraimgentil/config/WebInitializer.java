package me.efraimgentil.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 14/02/16.
 */
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{
  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class<?>[]{  SpringWebConfig.class, DatabaseConfig.class , WebSecurityConfig.class ,  ResourceServerConfig.class , MethodSecurityConfig.class  };
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[]{ };
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] { "/" };
  }
}
