package com.example.mhz.myuniversityapp.authentication;


import com.example.mhz.myuniversityapp.util.JsonPlaceHolderAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthenticationSupervisor {

    private static String token;



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

    public  void authenticate(AuthenticationRequest authenticationRequest,Callback<AuthenticationResponse> authenticationResponseCallback) {

        JsonPlaceHolderAPI jsonPlaceHolderAPI_for_auth = retrofit.create(JsonPlaceHolderAPI.class);
        Call<AuthenticationResponse> call = jsonPlaceHolderAPI_for_auth.getToken(authenticationRequest);
        call.enqueue(authenticationResponseCallback);
    }
}
