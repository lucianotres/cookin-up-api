package br.dev.ltres.cookin_up_api;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

public class MeuJwtAuthenticationConverter extends JwtAuthenticationConverter {

    public MeuJwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter delegate = new JwtGrantedAuthoritiesConverter();

        delegate.setAuthorityPrefix("ROLE_");
        delegate.setAuthoritiesClaimName("roles");

        this.setJwtGrantedAuthoritiesConverter(delegate);

        this.setPrincipalClaimName("name");
    }
}