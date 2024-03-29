package com.example.mhz.myuniversityapp.dynaforms;

import android.util.Log;

import com.example.mhz.myuniversityapp.authentication.AuthenticationSupervisor;
import com.example.mhz.myuniversityapp.dynaforms.post.PostRequest;
import com.example.mhz.myuniversityapp.dynaforms.post.PostResponse;
import com.example.mhz.myuniversityapp.util.ProcessMakerAPI;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DynaFormSupervisor {

    private static final String TAG = "dynaforms";
    Retrofit retrofitPost = new Retrofit.Builder()
            .baseUrl("http://process.isiforge.tn/api/1.0/isi/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ProcessMakerAPI processMakerAPI;
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://process.isiforge.tn/api/1.0/isi/project/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    DynaFormSupervisor() {

    }

    public JsonArray decryptArray(String pro_uid, String tas_uid) {
        JsonArray dynaform_JSONArray = new JsonArray();
        processMakerAPI = retrofit.create(ProcessMakerAPI.class);
        Call<List<Step>> call = processMakerAPI.getStep(pro_uid, tas_uid, "Bearer " + AuthenticationSupervisor.getToken());
        List<Step> steps;
        try {
            steps = call.execute().body();
            Log.i(TAG, "steps: " + steps);
            if (steps != null) {
                int index = -1;
                for (int j = 0, n = steps.size(); j < n; j++) {
                    if ((steps.get(j).getStep_type_obj().contentEquals("DYNAFORM"))) {
                        index = j;
                        break;
                    }

                }

                Call<DynaForm> call_DynaForm = processMakerAPI.getDynaform(pro_uid, steps.get(index).getStep_uid_obj(), "Bearer " + AuthenticationSupervisor.getToken());
                DynaForm dynaForm = call_DynaForm.execute().body();
                String codedString = dynaForm.getDyn_content();
                Log.i(TAG, codedString);

                    /* Get Dynaforms */

                //**********Fist Stage Items ******************************//

                JsonObject jobject = new JsonParser().parse(codedString).getAsJsonObject();
                JsonArray jarray = jobject.getAsJsonArray("items");
                String result = jarray.get(0).toString();
                Log.i(TAG, "result : " + result);

                //**********Second Stage Items ******************************//

                dynaform_JSONArray = new JsonParser().parse(result).getAsJsonObject().getAsJsonArray("items");


            } else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dynaform_JSONArray;
    }

    public boolean submit(String pro_uid, String tas_uid, String object) {
        JsonElement jelement = new JsonParser().parse(object);
        JsonArray jsonArrayVariables = jelement.getAsJsonArray();

        PostRequest postRequest = new PostRequest(pro_uid, tas_uid, jsonArrayVariables);
        processMakerAPI = retrofitPost.create(ProcessMakerAPI.class);
        Call<PostResponse> call = processMakerAPI.PostCase(postRequest, "Bearer " + AuthenticationSupervisor.getToken());
        PostResponse postResponse;
        try {
            postResponse = call.execute().body();
            if (postResponse != null) {
                System.out.println("Response for POST -->  " + postResponse.toString());
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
//        call.enqueue(new Callback<PostResponse>() {
//            @Override
//            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
//
//                System.out.println("Response for POST -->  " + response.message());
//            }
//
//            @Override
//            public void onFailure(Call<PostResponse> call, Throwable throwable) {
//
//            }
//        });
    }
}
