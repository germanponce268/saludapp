package com.saludapp.constantes;

public class SecurityConstant{
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String AUTHORITIES = "authorities";
    public static final String OPTION_HTTP_METHOD = "OPTIONS";
    public static final String[] PUBLIC_URLS = {"/usuarios/saveUsuario", "/usuarios/login","/usuarios/createToken"};
    public static final String FORBIDDEN_NESSAGE = "Necesitas estar logueado para acceder a esta pagina";
    public static final String ACCESS_DENIED_MESSAGE = "No tienes persmisos para acceder a esta pagina";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token no puede ser verificado";
    public static final String SALUDAPP = "SaludApp";
    public static final String SALUDAPP_ADMINISTRATION = "SaludApp Administracion";
    public static final String EXPIRATION_TIME = "3600000";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
}