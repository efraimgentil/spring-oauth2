package br.com.efraimgentil.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionCookieConfig;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 14/02/16.
 */
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    super.onStartup(servletContext);

    SessionCookieConfig sessionConfig = servletContext.getSessionCookieConfig();
    sessionConfig.setPath("/");
    sessionConfig.setName("SID");

  }

  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class<?>[]{ SpringWebConfig.class };
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
