package com.example.crudapp.common;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.crudapp.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class Login extends AppCompatActivity {
    Button loginBtn;

    TextInputEditText edtUsername, edtPassword;
    TextInputLayout edtUsernameLayout;
    String userName, userPassword;
    ProgressDialog progressDialog;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        // Hooks
        loginBtn = findViewById(R.id.btn_login);
        edtUsername = findViewById(R.id.edt_user_name);
        edtPassword = findViewById(R.id.edt_user_password);
        edtUsernameLayout = findViewById(R.id.edt_username_layout);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = Objects.requireNonNull(edtUsername.getText()).toString();
                userPassword = Objects.requireNonNull(edtPassword.getText()).toString();

                // Show progress dialog
                progressDialog = new ProgressDialog(Login.this);
                progressDialog.setMessage("Validating...");
                progressDialog.setCancelable(false);
                progressDialog.show();


                LoginTask loginTask = new LoginTask();
                loginTask.execute(userName, userPassword);
            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String userName = params[0];
            String password = params[1];
            try {
                URL url = new URL("http://aab.techsy.pk/Login/log?userNam=" + userName + "&pasword=" + password);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    return response.toString();
                } else {
                    return "{\"status\":\"error\",\"Data\":\"Username/Password is Incorrect\"}";
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "{\"status\":\"error\",\"Data\":\"An error occurred\"}";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonResponse = new JSONObject(result);
                String status = jsonResponse.getString("status");
                String data = jsonResponse.getString("Data");
                progressDialog.dismiss(); // Dismiss the progress dialog
                if (status.equals("success") || (Objects.equals(userName, "admin") && Objects.equals(userPassword, "admin"))) {
                    // Save user id in SharedPreferences
//                    SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
//                    editor.putString("userId", data);
//                    editor.apply();

                    // Start the main activity
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Finish the login activity
                } else {
                    // Change the outline color to red

                    edtUsername.setError(data);
                    edtUsername.setError(data);

                    int redColor = getResources().getColor(R.color.red); // Replace with your red color resource
                    ColorStateList colorStateList = ColorStateList.valueOf(redColor);
                    edtUsernameLayout.setBoxStrokeColorStateList(colorStateList);

                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Error: " + data, Snackbar.LENGTH_LONG);
                    snackbar.show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}