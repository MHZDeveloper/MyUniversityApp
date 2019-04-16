package com.example.mhz.myuniversityapp.authentication;


public class AuthenticationResponse {

    private String access_token;

    private String expires_in;

    private String token_type;

    private String scope ;

    private String refresh_token;


    public AuthenticationResponse(String access_token, String expires_in, String token_type, String scope, String refresh_token) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.token_type = token_type;
        this.scope = scope;
        this.refresh_token = refresh_token;

    }

    public String getAccess_token() {
        return access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public String getToken_type() {
        return token_type;
    }

    public String getScope() {
        return scope;
    }

    public String getRefresh_token() {
        return refresh_token;
    }
}
