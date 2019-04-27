package com.example.mhz.myuniversityapp.dynaforms;

import com.example.mhz.myuniversityapp.authentication.AuthenticationSupervisor;
import com.example.mhz.myuniversityapp.util.JsonPlaceHolderAPI;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DynaFormSupervisor {

    private Retrofit retrofit_Steps = new Retrofit.Builder()
            .baseUrl("http://process.isiforge.tn/api/1.0/isi/project/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private JsonPlaceHolderAPI jsonPlaceHolderAPI_Steps = retrofit_Steps.create(JsonPlaceHolderAPI.class);

    private Retrofit retrofit_Form = new Retrofit.Builder()
            .baseUrl("http://process.isiforge.tn/api/1.0/isi/project/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    JsonPlaceHolderAPI jsonPlaceHolderAPI_Form = retrofit_Form.create(JsonPlaceHolderAPI.class);

    DynaFormSupervisor(String pro_uid,String tas_uid){
        Call<List<Step>> call = jsonPlaceHolderAPI_Steps.getStep(pro_uid, tas_uid, "Bearer " + AuthenticationSupervisor.getToken());
        List<Step> steps;
        try {
            steps= call.execute().body();
            if (steps!=null){
                //Call<DynaForm> call_DynaForm = jsonPlaceHolderAPI_Form.getDynaform(pro_uid, , "Bearer " + AuthenticationSupervisor.getToken());

            }
            else{

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
