package com.example.mhz.myuniversityapp.util;


import com.example.mhz.myuniversityapp.authentication.AuthenticationRequest;
import com.example.mhz.myuniversityapp.authentication.AuthenticationResponse;
import com.example.mhz.myuniversityapp.dynaforms.DynaForm;
import com.example.mhz.myuniversityapp.dynaforms.Step;
import com.example.mhz.myuniversityapp.processes.Processes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceHolderAPI {

    @POST("isi/oauth2/token")
    Call<AuthenticationResponse> getToken(@Body AuthenticationRequest tokenRequest);


    @GET("http://process.isiforge.tn/api/1.0/isi/case/start-cases")
    Call<List<Processes.Case>> getUser(@Header("Authorization") String authHeader);

    @GET("{pro-uid}/activity/{tas-uid}/steps")
    Call<List<Step>> getStep(@Path("pro-uid") String pro_uid, @Path("tas-uid") String tas_uid, @Header("Authorization") String authHeader);


    @GET("{pro-uid}/dynaform/{step-uid-obj}")

    @Headers({
            "Content-Type:application/json"
    })
    Call<DynaForm> getDynaform(@Path("pro-uid") String pro_uid, @Path("step-uid-obj") String step_uid_obj, @Header("Authorization") String authHeader);

}
