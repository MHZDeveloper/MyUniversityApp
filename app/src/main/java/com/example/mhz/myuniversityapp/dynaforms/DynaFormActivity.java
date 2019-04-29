package com.example.mhz.myuniversityapp.dynaforms;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.Toast;

import com.example.mhz.myuniversityapp.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class DynaFormActivity extends AppCompatActivity {

    private static final String TAG = "dynaforms (activity)";
    LinearLayout.LayoutParams params;
    private Context context;
    private View dynaforms;
    private View dynaformProgress;
    private LinearLayout formContainer;
    private LinearLayout buttonsLayout;
    private LinearLayout tmpLayout;
    private DynaFormSupervisor dynaFormSupervisor;

    private String postObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dyna_form);
        context = this;

        dynaFormSupervisor = new DynaFormSupervisor();

        postObject = "";
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(10, 10, 10, 30);

        tmpLayout = new LinearLayout(context);
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
            Log.i(TAG, "pro_uid = " + getIntent().getStringExtra("pro_uid"));
            Log.i(TAG, "tas_uid = " + getIntent().getStringExtra("tas_uid"));
            JsonArray jarray = dynaFormSupervisor.decryptArray(getIntent().getStringExtra("pro_uid"), getIntent().getStringExtra("tas_uid"));
            for (int i = 0; i < jarray.size(); i++) {
                for (int j = 0; j < jarray.get(i).getAsJsonArray().size(); j++) {
                    JsonObject tmpObject = jarray.get(i).getAsJsonArray().get(j).getAsJsonObject();
                    if (tmpObject != null &&
                            tmpObject.getAsJsonObject() != null &&
                            tmpObject.getAsJsonObject().get("type") != null &&
                            tmpObject.getAsJsonObject().get("label") != null) {
                        String type = tmpObject.getAsJsonObject().get("type").toString();
                        if (type.contains("image")) { //    IMAGE
                            ImageView imageView = new ImageView(context);
                            imageView.setImageResource(R.mipmap.logo);
                            tmpLayout.addView(imageView);

                        } else if (type.contains("textarea")) { //  TEXTAREA
                            EditText editText = new EditText(context);
                            editText.setTag(tmpObject.getAsJsonObject().get("var_name").toString());
                            editText.setHint(tmpObject.getAsJsonObject().get("label").toString().replaceAll("\"", "").replaceAll("\\\\n", ""));
                            editText.setTextSize(25);
                            editText.setLayoutParams(params);
                            editText.setSingleLine(false);
                            editText.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                            editText.setPadding(5, 5, 5, 5);
                            tmpLayout.addView(editText);
                        } else if (type.contains("text")) { // TEXT
                            EditText editText = new EditText(context);
                            editText.setTag(tmpObject.getAsJsonObject().get("var_name").toString());
                            editText.setHint(tmpObject.getAsJsonObject().get("label").toString().replaceAll("\"", "").replaceAll("\\\\n", ""));
                            editText.setTextSize(25);
                            editText.setSingleLine(true);
                            editText.setLayoutParams(params);
                            editText.setGravity(Gravity.CENTER);
                            editText.setPadding(5, 5, 5, 5);
                            tmpLayout.addView(editText);
                        } else if (type.contains("subtitle")) { // SUBTITLE
                            TextView textView = new TextView(context);
                            textView.setTextColor(Color.BLACK);
                            textView.setLayoutParams(params);
                            textView.setGravity(Gravity.CENTER_HORIZONTAL);
                            textView.setTextSize(20);
                            textView.setPadding(5, 5, 5, 5);
                            textView.setText(tmpObject.getAsJsonObject().get("label").toString().replaceAll("\"", "").replaceAll("\\\\n", ""));
                            tmpLayout.addView(textView);
                        } else if (type.contains("title")) { // TITLE
                            TextView textView = new TextView(context);
                            textView.setLayoutParams(params);
                            textView.setTextColor(Color.BLACK);
                            textView.setTypeface(null, Typeface.BOLD);
                            textView.setGravity(Gravity.CENTER_HORIZONTAL);
                            textView.setTextSize(25);
                            textView.setPadding(5, 5, 5, 5);
                            textView.setText(tmpObject.getAsJsonObject().get("label").toString().replaceAll("\"", "").replaceAll("\\\\n", ""));
                            tmpLayout.addView(textView);
                        }
                    }
                }
                //buttons Layout
                // SUBMIT
                Button submitButton = new Button(context);
                submitButton.setPadding(5, 5, 5, 5);
                submitButton.setText("Envoyer");
                submitButton.setOnClickListener(view -> {
                    if (checkNoEmptyArea_PreparePostObject()) {
                        PostRequestTask postRequestTask = new PostRequestTask();
                        postRequestTask.execute();
                    }
                });
                //RESET
                Button cancelButton = new Button(context);
                cancelButton.setPadding(5, 5, 5, 5);
                cancelButton.setText("Annuler");
                cancelButton.setOnClickListener(view -> {
                    finish();
                });
                //Layout
                buttonsLayout = new LinearLayout(context);
                buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);
                buttonsLayout.setLayoutParams(params);
                buttonsLayout.setGravity(Gravity.CENTER);
                buttonsLayout.addView(cancelButton);
                buttonsLayout.addView(submitButton);
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            tmpLayout.addView(buttonsLayout);
            formContainer.addView(tmpLayout);
            dynaformProgress.setVisibility(View.GONE);
            dynaforms.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPreExecute() {
            formContainer.removeAllViewsInLayout();
            tmpLayout.removeAllViewsInLayout();
            tmpLayout.setLayoutParams(params);
            tmpLayout.setPadding(5, 5, 5, 5);
            tmpLayout.setOrientation(LinearLayout.VERTICAL);
            tmpLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        }


        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        public boolean checkNoEmptyArea_PreparePostObject() {
            boolean resultEmpty = true;
            postObject = "[";
            for (int i = 0; i < tmpLayout.getChildCount(); i++) {
                if (tmpLayout.getChildAt(i) instanceof EditText) {
                    if (((EditText) tmpLayout.getChildAt(i)).getText().toString().replaceAll(" ", "").isEmpty()) {
                        resultEmpty = false;
                        Toast.makeText(context, "Champ vide", Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        postObject = postObject + "{" + tmpLayout.getChildAt(i).getTag().toString() + ":\"" + ((EditText) tmpLayout.getChildAt(i)).getText().toString() + "\"},";
                    }
                }
            }
            postObject = postObject.substring(0, postObject.length() - 1);
            postObject = postObject + "]";
            return resultEmpty;
        }

    }

    @SuppressLint("StaticFieldLeak")
    public class PostRequestTask extends AsyncTask<Void, Void, Boolean> {

        private boolean result;


        @Override
        protected Boolean doInBackground(Void... voids) {
            result = dynaFormSupervisor.submit(getIntent().getStringExtra("pro_uid"),
                    getIntent().getStringExtra("tas_uid"),
                    postObject);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (result) {
                Toast.makeText(getApplicationContext(), "Request sent", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Problem", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
