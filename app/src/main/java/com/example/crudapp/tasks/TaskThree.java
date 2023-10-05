package com.example.crudapp.tasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;

import com.example.crudapp.R;
import com.example.crudapp.adapters.LlApiAdapter;
import com.example.crudapp.models.LlApiModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TaskThree extends AppCompatActivity {
    FloatingActionButton fBtnAdd;
    private RecyclerView recyclerView;
    LlApiAdapter adapter;
    public static ArrayList<LlApiModel> llApiDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // For Hiding the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_task_three);

        //Hooks
        fBtnAdd = findViewById(R.id.fBtn_Add);
        recyclerView = findViewById(R.id.recyclerView);
        llApiDataList = new ArrayList<LlApiModel>();


        // Calling the function
        fetchDataFromApi();

        fBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TaskThree.this, TaskThreeAddEdit.class));

            }
        });
        
    }

    public void fetchDataFromApi(){
        // Policy for calling APi from the main Thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            final String apiUrl = "http://sms1.logicslabs.com/temp/testfill";
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

                int id = item.getInt("id");
                String name = item.getString("name");
                String fname = item.getString("fname");
                String gender = item.getString("gender");

                //  Log.e("DataItem", name);
                LlApiModel a = new LlApiModel(id,name, fname,gender);

                llApiDataList.add(a);
            }
            // Log.d("DataItem", "Value of "+String.valueOf(llApiDataList.size())); // Log the item
            recyclerView.setLayoutManager(new LinearLayoutManager(TaskThree.this));
            adapter = new LlApiAdapter((Context) TaskThree.this, llApiDataList);
            recyclerView.setAdapter(adapter);

        }catch (Exception rt){
            // Log.e("DataItem",  String.valueOf(rt.toString())); // Log the item
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}