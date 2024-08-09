package com.tinqinacademy.bff.rest.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomToken extends AbstractAuthenticationToken {
    private final CustomUser user;
    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     */
    public CustomToken(CustomUser user) {
        super(user.getAuthorities());
        this.user = user;
    }

    @Override
    public Object getCredentials() {
        return user.getAuthorities();
    }

    @Override
    public Object getPrincipal() {
        return user.getUsername();
    }
}
