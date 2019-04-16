package com.example.mhz.myuniversityapp.authentication;

public class AuthenticationRequest {

    private String grant_type = "password";

    private String scope = "*";

    private String client_id = "SJGZDWXOPLJZLBDQGACCAGAVWSHORHJK";

    private String client_secret = "6734914665b5258c7a2eb01077382585";

    private String username;

    private String password;

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }



}
