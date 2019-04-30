package com.example.mhz.myuniversityapp.authentication;


import com.example.mhz.myuniversityapp.util.ProcessMakerAPI;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthenticationSupervisor {

    private static String token;
    private static String username;
    private static boolean authenticated = false;



    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://process.isiforge.tn")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        AuthenticationSupervisor.token = token;
    }

    public static boolean isAuthenticated() {
        return authenticated;
    }

    public static void setAuthenticated(boolean authenticated) {
        AuthenticationSupervisor.authenticated = authenticated;
    }

    public static String getUsername() {
        return username;
    }

    public  AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest      ) throws IOException {

        username = authenticationRequest.getUsername();
        ProcessMakerAPI processMakerAPI_for_auth = retrofit.create(ProcessMakerAPI.class);
        Call<AuthenticationResponse> call = processMakerAPI_for_auth.getToken(authenticationRequest);
        return call.execute().body();

    }
}
