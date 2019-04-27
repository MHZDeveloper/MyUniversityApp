package com.example.mhz.myuniversityapp.processes;

import android.os.AsyncTask;
import android.util.Log;

import com.example.mhz.myuniversityapp.authentication.AuthenticationSupervisor;
import com.example.mhz.myuniversityapp.util.JsonPlaceHolderAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Helper class for providing sample pro_title for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class Processes extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = "Cases";

    /**
     * An array of sample (dummy) items.
     */
    public static List<Case> cases = new ArrayList<>();

    public Processes(){


//        call.enqueue(new Callback<List<Case>>() {
//            @Override
//            public void onResponse(@NonNull Call<List<Case>> call, @NonNull Response<List<Case>> response) {
//                Processes.cases.clear();
//                for (int i=0;i<response.body().size();i++){
//                    Processes.cases.add(new Case(String.valueOf(i),response.body().get(i).getPro_title(),response.body().get(i).getPro_uid()));
//                }
//                Log.i(TAG,"Get Cases : Successful");
//                //MainActivity.dialog.hide();
//            }
//            @Override
//            public void onFailure(@NonNull Call<List<Case>> call, @NonNull Throwable t) {
//                Log.i(TAG,"Get Cases : Failure");
//            }
//        });
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderAPI jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);
        Call<List<Case>> call = jsonPlaceHolderAPI.getUser("Bearer "+ AuthenticationSupervisor.getToken());
        List<Case> cases = null;
        try {
            cases = (List<Case>) call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (cases!=null){
            Processes.cases.clear();
            for (int i=0;i<cases.size();i++){
                Processes.cases.add(new Case(cases.get(i).getTas_uid(),cases.get(i).getPro_title(),cases.get(i).getPro_uid()));
            }
            Log.i(TAG,"Get Cases : Successful");
            return true;
        }
        else{
            Log.i(TAG,"Get Cases : Failure");
            return false;
        }
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
