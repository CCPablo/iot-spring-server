package internal.webserver.authentication;

public class SecurityConstants {

    public static final String AUTH_LOGIN_URL = "/api/v1/login";
    public static final String AUTH_LOGOUT_URL = "/api/logout";

    // Signing key for HS512 algorithm
    // You can use the page http://www.allkeysgenerator.com/ to generate all kinds of keys
    public static final String JWT_SECRET = "n2r5u8x/A4D*G-KmPdSgVkYp226v9y$B&E(H+MbQeThWmZq4t7w!z%C*F=J@NcRf";
    public static final long EXPIRATION_TIME = 30_000_000; // 10 days

    // JWT token defaults
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "secure-api";
    public static final String TOKEN_AUDIENCE = "secure-app";
}
