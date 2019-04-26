package com.example.mhz.myuniversityapp.processes;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.mhz.myuniversityapp.authentication.AuthenticationSupervisor;
import com.example.mhz.myuniversityapp.util.JsonPlaceHolderAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Helper class for providing sample pro_title for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class Processes  {

    private static final String TAG = "Cases";

    /**
     * An array of sample (dummy) items.
     */
    public static List<Case> cases = new ArrayList<>();

    public Processes(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderAPI jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);
        Call<List<Case>> call = jsonPlaceHolderAPI.getUser("Bearer "+ AuthenticationSupervisor.getToken());

        call.enqueue(new Callback<List<Case>>() {
            @Override
            public void onResponse(@NonNull Call<List<Case>> call, @NonNull Response<List<Case>> response) {
                Processes.cases.clear();
                for (int i=0;i<response.body().size();i++){
                    Processes.cases.add(new Case(String.valueOf(i),response.body().get(i).getPro_title(),response.body().get(i).getPro_uid()));
                }
                Log.i(TAG,"Get Cases : Successful");
                //MainActivity.dialog.hide();
            }
            @Override
            public void onFailure(@NonNull Call<List<Case>> call, @NonNull Throwable t) {
                Log.i(TAG,"Get Cases : Failure");
            }
        });
    }



    public List<Case> getCases() {
        return cases;
    }

    public void setCases(List<Case> cases) {
        this.cases = cases;
    }

    public class Case {
        public final String tas_uid;
        public final String pro_title;
        public final String pro_uid;

        public Case(String tas_uid, String pro_title, String pro_uid) {
            this.tas_uid = tas_uid;
            this.pro_title = pro_title;
            this.pro_uid = pro_uid;
        }

        public String getTas_uid() {
            return tas_uid;
        }

        public String getPro_title() {
            return pro_title;
        }

        public String getPro_uid() {
            return pro_uid;
        }
    }
}
