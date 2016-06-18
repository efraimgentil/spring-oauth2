package br.com.efraimgentil.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 08/06/16.
 */

public class CORSFilter implements Filter {
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    HttpServletResponse response = (HttpServletResponse) res;
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
    response.setHeader("Access-Control-Allow-Headers", "x-requested-with , authorization, accept");
    response.setHeader("Access-Control-Max-Age", "3600");
    HttpServletRequest request = (HttpServletRequest) req;
    if ( !"OPTIONS".equalsIgnoreCase(  request.getMethod() ) ) {
      chain.doFilter(req, res);
    } else {

    }
  }

  @Override
  public void destroy() {

  }
}
