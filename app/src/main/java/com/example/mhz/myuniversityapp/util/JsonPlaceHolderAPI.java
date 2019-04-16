package com.example.mhz.myuniversityapp.util;


import com.example.mhz.myuniversityapp.authentication.AuthenticationRequest;
import com.example.mhz.myuniversityapp.authentication.AuthenticationResponse;
import com.example.mhz.myuniversityapp.processes.Processes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface JsonPlaceHolderAPI {

    @POST("isi/oauth2/token")
    Call<AuthenticationResponse> getToken(@Body AuthenticationRequest tokenRequest);


    @GET("http://process.isiforge.tn/api/1.0/isi/case/start-cases")
    Call<List<Processes.Case>> getUser(@Header("Authorization") String authHeader);
}
