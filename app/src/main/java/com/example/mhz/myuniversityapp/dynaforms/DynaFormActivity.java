package com.example.mhz.myuniversityapp.dynaforms;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mhz.myuniversityapp.R;

import java.util.concurrent.TimeUnit;

public class DynaFormActivity extends AppCompatActivity {

    private View dynaforms;
    private View dynaformProgress;
    private LinearLayout formContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dyna_form);

        dynaforms=findViewById(R.id.dynaforms);
        dynaformProgress = findViewById(R.id.dynaform_progress);
        formContainer = findViewById(R.id.form_container);
        LoadTask task = new LoadTask();
        task.execute();
    }


    public class LoadTask extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            TextView textView = new TextView(getBaseContext());
            textView.setText("LOOOOOL");
            formContainer.addView(textView);
            dynaformProgress.setVisibility(View.GONE);
            dynaforms.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
