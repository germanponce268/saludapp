package com.saludapp.entidades;

public class Authorities {

    public static final String[] USER_AUTHORITIE = {"user:read"};
    public static final String[] ADMIN_AUTHORITIE = {"user:read", "user:create", "user:update"};
    public static final String[] SUPER_USER_AUTHORITIE = {"user:read", "user:create","user:update","user:delete"};

}
