package br.com.efraimgentil.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 08/06/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DatabaseConfig.class })
public class MyClientDetailServiceIT {

  @Autowired
  DataSource dataSource;


  @Test
  public void teste(){
    System.out.println( dataSource );
    String clientId = "rh";

    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    BaseClientDetails result = jdbcTemplate.queryForObject(
            "SELECT client_id , resource_ids , client_secret, scope , authorized_grant_types, authorities , autoapprove" +
                    " FROM public.oauth_client_details where client_id = ?",
            new Object[]{clientId}, new RowMapper<BaseClientDetails>() {
              public BaseClientDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                BaseClientDetails result = new BaseClientDetails();
                result.setClientId( rs.getString("client_id") );
                result.setClientSecret( rs.getString("client_secret") );
                String resourceids = rs.getString("resource_ids");
                result.setResourceIds(Arrays.asList( resourceids.split(",") ) );
                String scope = rs.getString("scope");
                result.setScope( Arrays.asList( scope.split(",") ) );
                String granttypes = rs.getString("authorized_grant_types");
                result.setAuthorizedGrantTypes( Arrays.asList( granttypes.split(",") ) );
                String autoapprovescopes = rs.getString("autoapprove");
                if("true".equalsIgnoreCase(autoapprovescopes)){
                  result.setAutoApproveScopes( result.getScope() );
                }
                return result;
              }
            });
    System.out.println("result = " + result);

  }
}
