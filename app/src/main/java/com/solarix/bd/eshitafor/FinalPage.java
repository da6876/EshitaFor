package com.solarix.bd.eshitafor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.core.util.Pair;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.solarix.bd.eshitafor.httpd.HttpClientPost;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FinalPage extends AppCompatActivity {
    Button btnQns1Yes11,btnQns1No11;
    String strAnser="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_page);

        btnQns1Yes11 = findViewById(R.id.btnQns1Yes11);
        btnQns1No11 = findViewById(R.id.btnQns1No11);

        btnQns1Yes11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strAnser="Yes";
                btnQns1Yes11.setEnabled(false);
                btnQns1No11.setEnabled(true);
                UserLogIn userLogIn = new UserLogIn(strAnser);
                userLogIn.execute();
            }
        });

        btnQns1No11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strAnser="No";
                btnQns1Yes11.setEnabled(true);
                btnQns1No11.setEnabled(false);
                UserLogIn userLogIn = new UserLogIn(strAnser);
                userLogIn.execute();
            }
        });
    }

    private class UserLogIn extends AsyncTask<Void, Void, String> {
        AppCompatDialog progressDialog;
        String Qus1;
        String test = "", role = "", pId = "", usernamefull = "";

        public UserLogIn(String Qus1) {
            this.Qus1 = Qus1;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new AppCompatDialog(FinalPage.this);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.prograsbar_layout);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {


            List<Pair<String, String>> postParameters = new ArrayList<>();
            postParameters.add(new Pair("QusFianl", Qus1));
            String result = "";
            try {
                String response = HttpClientPost.execute("http://103.91.54.60/EshitaApp.php", postParameters);
                result = response.toString();
                result = result.replaceAll("\n", "");

            } catch (Exception e) {
                Log.e("log_tag", "Error in http connection!!" + e.toString());
            }
            try {
                JSONArray jArray = new JSONArray(result.toString());

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);

                    test = json_data.getString("yesno");
                    role = json_data.getString("role");
                    pId = json_data.getString("pId");
                    usernamefull = json_data.getString("username");

                }
            } catch (Exception e) {
                Log.e("log_tag", "Error in http connection!!" + e.toString());
            }

            return test;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            finish();
            startActivity(new Intent(FinalPage.this, Lastpage.class));
        }
    }
}