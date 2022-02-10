package com.solarix.bd.eshitafor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.solarix.bd.eshitafor.httpd.GetLocationInfo;
import com.solarix.bd.eshitafor.httpd.HttpClientPost;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuestionsPage extends AppCompatActivity {
    Button btnQns1Yes, btnQns2Yes, btnQns3Yes, btnQns4Yes, btnQns5Yes,
            btnQns1No, btnQns2No, btnQns3No, btnQns4No, btnQns5No,
            btnNext;

    String strQus1 = "", strQus2 = "", strQus3 = "", strQus4 = "", strQus5 = "";
    double lat = 0.0, lon = 0.0;

    GetLocationInfo getLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions_page);

        btnQns1Yes = findViewById(R.id.btnQns1Yes);
        btnQns2Yes = findViewById(R.id.btnQns2Yes);
        btnQns3Yes = findViewById(R.id.btnQns3Yes);
        btnQns4Yes = findViewById(R.id.btnQns4Yes);
        btnQns5Yes = findViewById(R.id.btnQns5Yes);

        btnQns1No = findViewById(R.id.btnQns1No);
        btnQns2No = findViewById(R.id.btnQns2No);
        btnQns3No = findViewById(R.id.btnQns3No);
        btnQns4No = findViewById(R.id.btnQns4No);
        btnQns5No = findViewById(R.id.btnQns5No);

        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!strQus1.equals("")) {
                    if (!strQus2.equals("")) {
                        if (!strQus3.equals("")) {
                            if (!strQus4.equals("")) {
                                if (!strQus5.equals("")) {
                                    if (!String.valueOf(lat).equals("0.0") || !String.valueOf(lon).equals("0.0")) {
                                        UserLogIn userLogIn = new UserLogIn(strQus1, strQus2, strQus3, strQus4, strQus5);
                                        userLogIn.execute();
                                    } else {
                                        errorAlert1("Answer is Not Ok \n Madam Please Try Again After Few Minutes!!");
                                    }
                                } else {
                                    errorAlert("Answer the Question 5 !!");
                                }
                            } else {
                                errorAlert("Answer the Question 4 !!");
                            }
                        } else {
                            errorAlert("Answer the Question 3 !!");
                        }
                    } else {
                        errorAlert("Answer the Question 2 !!");
                    }
                } else {
                    errorAlert("Answer the Question 1 !!");
                }
            }
        });

        btnQns1Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strQus1 = "Yes";
                btnQns1Yes.setEnabled(false);
                btnQns1No.setEnabled(true);
            }
        });

        btnQns2Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strQus2 = "Yes";
                btnQns2Yes.setEnabled(false);
                btnQns2No.setEnabled(true);
            }
        });

        btnQns3Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strQus3 = "Yes";
                btnQns3Yes.setEnabled(false);
                btnQns3No.setEnabled(true);
            }
        });

        btnQns4Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strQus4 = "Yes";
                btnQns4Yes.setEnabled(false);
                btnQns4No.setEnabled(true);
            }
        });

        btnQns5Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strQus5 = "Yes";
                btnQns5Yes.setEnabled(false);
                btnQns5No.setEnabled(true);
            }
        });

        btnQns1No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strQus1 = "NO";
                btnQns1Yes.setEnabled(true);
                btnQns1No.setEnabled(false);
            }
        });

        btnQns2No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strQus2 = "NO";
                btnQns2Yes.setEnabled(true);
                btnQns2No.setEnabled(false);
            }
        });

        btnQns3No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strQus3 = "NO";
                btnQns3Yes.setEnabled(true);
                btnQns3No.setEnabled(false);
            }
        });

        btnQns4No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strQus4 = "NO";
                btnQns4Yes.setEnabled(true);
                btnQns4No.setEnabled(false);
            }
        });

        btnQns5No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strQus5 = "NO";
                btnQns5Yes.setEnabled(true);
                btnQns5No.setEnabled(false);
            }
        });

    }

    private class UserLogIn extends AsyncTask<Void, Void, String> {
        AppCompatDialog progressDialog;
        String Qus1, Qus2, Qus3, Qus4, Qus5;
        String test = "", role = "", pId = "", usernamefull = "";

        public UserLogIn(String Qus1, String Qus2, String Qus3, String Qus4, String Qus5) {
            this.Qus1 = Qus1;
            this.Qus2 = Qus2;
            this.Qus3 = Qus3;
            this.Qus4 = Qus4;
            this.Qus5 = Qus5;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new AppCompatDialog(QuestionsPage.this);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.prograsbar_layout);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {


            List<Pair<String, String>> postParameters = new ArrayList<>();
            postParameters.add(new Pair("Qus1", Qus1));
            postParameters.add(new Pair("Qus2", Qus2));
            postParameters.add(new Pair("Qus3", Qus3));
            postParameters.add(new Pair("Qus4", Qus4));
            postParameters.add(new Pair("Qus5", Qus5));
            postParameters.add(new Pair("lat", String.valueOf(lat)));
            postParameters.add(new Pair("lon", String.valueOf(lon)));
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
            startActivity(new Intent(QuestionsPage.this, FinalPage.class));
        }
    }

    private boolean CheckGPS() {
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle("No GPS Data")
                    .setMessage("Please enable your gps settings")
                    .setPositiveButton("Enable",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int arg1) {
                                    Intent gps = new Intent(
                                            Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivity(gps);
                                    dialog.dismiss();
                                }
                            }).show();

            return false;
        } else {

            return true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        CheckGPS();
        if (Build.VERSION.SDK_INT > 22) {
            if (ContextCompat.checkSelfPermission(QuestionsPage.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(QuestionsPage.this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return;
            } else {
                getLastLocation = new GetLocationInfo(QuestionsPage.this);
                lat = getLastLocation.getlat();
                lon = getLastLocation.getLon();
            }
        } else {
            getLastLocation = new GetLocationInfo(QuestionsPage.this);
            lat = getLastLocation.getlat();
            lon = getLastLocation.getLon();
        }
    }


    public void errorAlert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(QuestionsPage.this);
        builder.setMessage(msg);

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }


    public void errorAlert1(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(QuestionsPage.this);
        builder.setMessage(msg);

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(QuestionsPage.this,QuestionsPage.class));
                        finish();
                    }
                });
        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }
}