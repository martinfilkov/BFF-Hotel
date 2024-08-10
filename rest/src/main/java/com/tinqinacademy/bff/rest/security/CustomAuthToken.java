package com.tinqinacademy.bff.rest.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class CustomAuthToken extends AbstractAuthenticationToken {
    private final CustomUser user;
    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     */
    public CustomAuthToken(CustomUser user) {
        super(user.getAuthorities());
        this.user = user;
        super.setAuthenticated(true);
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
