package com.saludapp.entidades;

public enum Role {
    ROLE_USER(Authorities.USER_AUTHORITIE),
    ROLE_ADMIN(Authorities.ADMIN_AUTHORITIE),

    ROLE_SUPER_USER_ADMIN(Authorities.SUPER_USER_AUTHORITIE);
    String[] authorities;

    Role(String[] authoritie) {

        this.authorities = authoritie;
    }

    public String[] getAuthorities() {
        return authorities;
    }
}
