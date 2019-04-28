package com.example.mhz.myuniversityapp.dynaforms;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mhz.myuniversityapp.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class DynaFormActivity extends AppCompatActivity {

    private static final String TAG = "dynaforms (activity)";
    LinearLayout.LayoutParams params;
    private View dynaforms;
    private View dynaformProgress;
    private LinearLayout formContainer;
    private LinearLayout tmpLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dyna_form);

        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(10,10,10,30);

        tmpLayout = new LinearLayout(getBaseContext());
        dynaforms = findViewById(R.id.dynaforms);
        dynaformProgress = findViewById(R.id.dynaform_progress);
        formContainer = findViewById(R.id.form_container);
        LoadTask task = new LoadTask();
        task.execute();
    }


    @SuppressLint("StaticFieldLeak")
    public class LoadTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            DynaFormSupervisor dynaFormSupervisor = new DynaFormSupervisor();
            Log.i(TAG, "pro_uid = " + getIntent().getStringExtra("pro_uid"));
            Log.i(TAG, "tas_uid = " + getIntent().getStringExtra("tas_uid"));
            JsonArray jarray = dynaFormSupervisor.decryptArray(getIntent().getStringExtra("pro_uid"), getIntent().getStringExtra("tas_uid"));
            for (int i = 0; i < jarray.size(); i++) {
                for (int j = 0; j < jarray.get(i).getAsJsonArray().size(); j++) {
                    JsonObject tmpObject = jarray.get(i).getAsJsonArray().get(j).getAsJsonObject();
                    if (tmpObject != null &&
                            tmpObject.getAsJsonObject() != null &&
                            tmpObject.getAsJsonObject().get("type")!=null &&
                            tmpObject.getAsJsonObject().get("label")!=null) {
                        String type = tmpObject.getAsJsonObject().get("type").toString();
                        String label = tmpObject.getAsJsonObject().get("label").toString();
                        if (type.contains("image")) {
                            ImageView imageView = new ImageView(getApplicationContext());
                            imageView.setImageResource(R.mipmap.logo);
                            imageView.setLayoutParams(params);
                            tmpLayout.addView(imageView);

                        } else if (type.contains("textarea")) {
                            EditText editText = new EditText(getBaseContext());
                            editText.setHint(tmpObject.getAsJsonObject().get("label").toString().replaceAll("\"","").replaceAll("\\\\n",""));
                            editText.setTextSize(20);
                            editText.setLayoutParams(params);
                            editText.setBackgroundColor(Color.WHITE);
                            editText.setHintTextColor(Color.GRAY);
                            editText.setGravity(Gravity.CENTER);
                            editText.setTextColor(Color.BLACK);
                            editText.setSingleLine(false);
                            editText.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                            editText.setPadding(5,5,5,5);
                            tmpLayout.addView(editText);
                        }
                        else if (type.contains("text")) {
                            EditText editText = new EditText(getBaseContext());
                            editText.setHint(tmpObject.getAsJsonObject().get("label").toString().replaceAll("\"","").replaceAll("\\\\n",""));
                            editText.setTextSize(25);
                            editText.setLayoutParams(params);
                            editText.setBackgroundColor(Color.WHITE);
                            editText.setHintTextColor(Color.GRAY);
                            editText.setGravity(Gravity.CENTER);
                            editText.setTextColor(Color.BLACK);
                            editText.setPadding(5,5,5,5);
                            tmpLayout.addView(editText);
                        }else if (type.contains("subtitle")) {
                            TextView textView = new TextView(getBaseContext());
                            textView.setLayoutParams(params);
                            textView.setTextColor(Color.BLACK);
                            textView.setGravity(Gravity.CENTER_HORIZONTAL);
                            textView.setTextSize(20);
                            textView.setPadding(5,5,5,5);
                            textView.setText(tmpObject.getAsJsonObject().get("label").toString().replaceAll("\"","").replaceAll("\\\\n",""));
                            tmpLayout.addView(textView);
                        }
                        else if (type.contains("title")) {
                            TextView textView = new TextView(getBaseContext(),null,R.style.TitleStyle);
                            textView.setLayoutParams(params);
                            textView.setTypeface(null, Typeface.BOLD);
                            textView.setTextColor(Color.BLACK);
                            textView.setGravity(Gravity.CENTER_HORIZONTAL);
                            textView.setTextSize(25);
                            textView.setPadding(5,5,5,5);
                            textView.setText(tmpObject.getAsJsonObject().get("label").toString().replaceAll("\"","").replaceAll("\\\\n",""));
                            tmpLayout.addView(textView);
                        }
                        else if (type.contains("submit")) {
                            Button button = new Button(getBaseContext());
                            button.setLayoutParams(params);
                            //button.setTextColor(Color.BLACK);
                            button.setPadding(5,5,5,5);
                            button.setText(tmpObject.getAsJsonObject().get("label").toString().replaceAll("\"","").replaceAll("\\\\n",""));
                            tmpLayout.addView(button);
                        }
                    }
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            formContainer.addView(tmpLayout);
            dynaformProgress.setVisibility(View.GONE);
            dynaforms.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPreExecute() {
            formContainer.removeAllViewsInLayout();
            tmpLayout.removeAllViewsInLayout();
            tmpLayout.setLayoutParams(params);
            tmpLayout.setPadding(5,5,5,5);
            tmpLayout.setOrientation(LinearLayout.VERTICAL);
            tmpLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
