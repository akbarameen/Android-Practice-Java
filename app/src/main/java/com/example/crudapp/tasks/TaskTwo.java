package com.example.crudapp.tasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.WindowManager;

import com.example.crudapp.R;
import com.example.crudapp.adapters.ApiCallAdapter;
import com.example.crudapp.models.ApiCallModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TaskTwo extends AppCompatActivity {

    private RecyclerView recyclerView;
    ApiCallAdapter apiCallAdapter;
    public static ArrayList<ApiCallModel> apiDataList;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_task_two);

        // Policy for calling APi from the main Thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialization of this array is very Important
        apiDataList = new ArrayList<ApiCallModel>();


        // Calling the function
        fetchDataFromApi();
    }

    public void fetchDataFromApi(){
        try {
            final String apiUrl = "http://test.techsy.pk/temp/gettestingdata";
            URL url = new URL(apiUrl);
            HttpURLConnection httpURLConnection1 = (HttpURLConnection) url.openConnection();
            InputStream inputStream1 = httpURLConnection1.getInputStream();
            BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream1));
            String line1 = "";
            String data1 = "";

            while (line1 != null) {
                line1 = bufferedReader1.readLine();
                data1 = data1 + line1;
            }
            //  Log.e("DataItem", "Value of response " + data1);

            JSONObject jsonObject = new JSONObject(data1);
            JSONArray dataArray = jsonObject.getJSONArray("Data");

            // Log.e("DataItem", "Value of " + dataArray.length());

            // Loop through the data array
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject item = dataArray.getJSONObject(i);

                String name = item.getString("name");
                String stdClass = item.getString("Class");
                String admissionDate = item.getString("admissiondts");



                String status = item.getString("status");

            //  Log.e("DataItem", name);

                ApiCallModel a = new ApiCallModel();
                a.setName(name);
                a.setStdClass(stdClass);
                a.setAdmissionDate(admissionDate);
                a.setStatus(status);
                // Log.e("DataItem", "Value of " + dataArray.length());
                apiDataList.add(a);
            }
            Log.d("DataItem", "Value of "+String.valueOf(apiDataList.size())); // Log the item
            recyclerView.setLayoutManager(new LinearLayoutManager(TaskTwo.this));
            apiCallAdapter = new ApiCallAdapter((Context) TaskTwo.this, apiDataList);
            recyclerView.setAdapter(apiCallAdapter);
        }catch (Exception rt){
           // Log.e("DataItem",  String.valueOf(rt.toString())); // Log the item
        }
    }


}


