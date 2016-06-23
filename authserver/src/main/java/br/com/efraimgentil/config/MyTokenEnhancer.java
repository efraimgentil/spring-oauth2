package br.com.efraimgentil.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 23/06/16.
 */
public class MyTokenEnhancer implements TokenEnhancer {
  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
    Map<String, Object> additionalInfo = new HashMap<>();
    OAuth2Request oAuth2Request = authentication.getOAuth2Request();
    Collection<? extends GrantedAuthority> authorities = oAuth2Request.getAuthorities();
    additionalInfo.put("client_authorities",  authorities );
    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

    return accessToken;
  }

}
