package br.com.efraimgentil.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;

import java.util.*;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 23/06/16.
 */
public class MyAccessTokenConverter extends DefaultAccessTokenConverter {

  public final static String USER_CLIENT_AUTHORITIES = "user_client_authorities";

  private UserAuthenticationConverter userTokenConverter = new DefaultUserAuthenticationConverter();

  public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
    Map<String, Object> response = (Map<String, Object>) super.convertAccessToken(token, authentication);
    if( !response.containsKey(USER_CLIENT_AUTHORITIES) ){
      response.put(USER_CLIENT_AUTHORITIES , AuthorityUtils.authorityListToSet( authentication.getOAuth2Request().getAuthorities() ) );
    }
    return response;
  }

  @Override
  public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
    return super.extractAccessToken(value, map);
  }

  public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
    Map<String, String> parameters = new HashMap<String, String>();
    Set<String> scope = extractScope(map);
    Authentication user = userTokenConverter.extractAuthentication(map);
    String clientId = (String) map.get(CLIENT_ID);
    parameters.put(CLIENT_ID, clientId);
    if ( map.containsKey(GRANT_TYPE) ) {
      parameters.put(GRANT_TYPE, (String) map.get(GRANT_TYPE));
    }
    /*Set<String> resourceIds = new LinkedHashSet<String>(map.containsKey(AUD) ? getAudience(map)
            : Collections.<String>emptySet());*/

    Collection<? extends GrantedAuthority> authorities = null;
    if ( map.containsKey(USER_CLIENT_AUTHORITIES) ) {
      String[] roles = ((Collection<String>)map.get(USER_CLIENT_AUTHORITIES)).toArray(new String[0]);
      authorities = AuthorityUtils.createAuthorityList(roles);
    }
    OAuth2Request request = new OAuth2Request(parameters, clientId, authorities, true, scope, null , null, null,
            null);
    return new OAuth2Authentication(request, user);
  }

  private Collection<String> getAudience(Map<String, ?> map) {
    Object auds = map.get(AUD);
    if (auds instanceof Collection) {
      @SuppressWarnings("unchecked")
      Collection<String> result = (Collection<String>) auds;
      return result;
    }
    return Collections.singleton((String)auds);
  }

  private Set<String> extractScope(Map<String, ?> map) {
    Set<String> scope = Collections.emptySet();
    if (map.containsKey(SCOPE)) {
      Object scopeObj = map.get(SCOPE);
      if (String.class.isInstance(scopeObj)) {
        scope = Collections.singleton(String.class.cast(scopeObj));
      } else if (Collection.class.isAssignableFrom(scopeObj.getClass())) {
        @SuppressWarnings("unchecked")
        Collection<String> scopeColl = (Collection<String>) scopeObj;
        scope = new LinkedHashSet<String>(scopeColl);	// Preserve ordering
      }
    }
    return scope;
  }

}
