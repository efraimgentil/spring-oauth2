package br.com.efraimgentil.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 08/06/16.
 */
@Service
public class MyClientDetailService implements ClientDetailsService {

  @Autowired
  DataSource dataSource;

  private static HashMap<String , BaseClientDetails> clientsMap = new HashMap<>();

  @Override
  public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    BaseClientDetails result = clientsMap.get(clientId);
    if(result == null) {
      try {
        result = jdbcTemplate.queryForObject(
                "SELECT client_id , resource_ids , client_secret, scope , authorized_grant_types, authorities , autoapprove" +
                        " FROM public.oauth_client_details where client_id = ?",
                new Object[]{clientId}, new RowMapper<BaseClientDetails>() {
                  public BaseClientDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                    BaseClientDetails result = new BaseClientDetails();
                    result.setClientId(rs.getString("client_id"));
                    result.setClientSecret(rs.getString("client_secret"));
                    String resourceids = rs.getString("resource_ids");
                    result.setResourceIds(Arrays.asList(resourceids.split(",")));
                    String scope = rs.getString("scope");
                    result.setScope(Arrays.asList(scope.split(",")));
                    String granttypes = rs.getString("authorized_grant_types");
                    result.setAuthorizedGrantTypes(Arrays.asList(granttypes.split(",")));
                    String autoapprovescopes = rs.getString("autoapprove");
                    if ("true".equalsIgnoreCase(autoapprovescopes)) {
                      result.setAutoApproveScopes(result.getScope());
                    }
                    return result;
                  }
                });
        clientsMap.put(clientId , result );
      } catch (Exception e) {
        throw new ClientRegistrationException("Não localizou o client");
      }
    }
    return result;
  }

}
