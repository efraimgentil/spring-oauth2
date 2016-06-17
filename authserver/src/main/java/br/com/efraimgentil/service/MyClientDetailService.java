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
import java.util.List;

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
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String clientFullName = clientId + authentication.getName();
    System.out.println("authentication = " + authentication);
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    BaseClientDetails result = clientsMap.get( clientFullName );
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
        result = loadAuthorities( result , clientId , authentication  , jdbcTemplate);
        clientsMap.put( clientFullName , result );
      } catch (Exception e) {
        throw new ClientRegistrationException("Não localizou o client");
      }
    }
    return result;
  }

  private BaseClientDetails loadAuthorities(BaseClientDetails result , String clientId , Authentication authentication , JdbcTemplate jdbcTemplate) {
    List<String> strings = jdbcTemplate.queryForList("SELECT permissao FROM public.tb_usuario_permissao_client WHERE client_id = ? and login = ?"
            , new Object[]{clientId, authentication.getName() }
            , String.class);
    List<GrantedAuthority> authorities = new ArrayList<>();
    for(String permissao : strings ){
      authorities.add( new SimpleGrantedAuthority( "PERM_" + permissao ) );
    }
    result.setAuthorities( authorities );
    return result;
  }

}
