package com.example.crudapp.tasks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.crudapp.R;

import android.content.Intent;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class TaskThreeAddEdit extends AppCompatActivity {

    ImageView imgBtnBack;
    Spinner genderDropDown;
    ArrayList<String> arrGender = new ArrayList<>();

    EditText edtName, edtFName;
    Button btnAddEdit;

    int id = 0;
    String name, fName, gender;
    TextView title ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // For Hiding the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_task_three_add_edit);

        // Policy for calling APi from the main Thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //  Hooks
        genderDropDown = findViewById(R.id.genders_list);
        imgBtnBack = findViewById(R.id.img_btn_back);
        edtName= findViewById(R.id.edt_name);
        edtFName = findViewById(R.id.edt_fName);
        title = findViewById(R.id.task_three_heading);
        btnAddEdit = findViewById(R.id.btn_add_edit);

        // default Values
        title.setText("Add New");
        btnAddEdit.setText("Add");

        // Getting data form intent Extra and setting it in EditTextFields
        gettingAndSettingValues();


        // adding data into Gender ArrayList
        arrGender.add("Male");
        arrGender.add("Female");



        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,arrGender);
        genderDropDown.setAdapter(adapter);

        btnAddEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             addEditData();
            }
        });

        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void addEditData() {
        name = Objects.requireNonNull(edtName.getText()).toString();
        fName = Objects.requireNonNull(edtFName.getText()).toString();
        String dropDownChecked = genderDropDown.getSelectedItem().toString();
        if (dropDownChecked.equals("Male")) {
            gender = "M";
        }else{
            gender = "F";
        }
        if (validateName() || validateFName()) {
            try {
                final String apiUrl = "http://sms1.logicslabs.com/temp/testAddEdit?id="+id+"&name="+name+"&fname="+fName+"&gender="+gender;
                URL url = new URL(apiUrl);
                HttpURLConnection httpURLConnection1 = (HttpURLConnection) url.openConnection();
//            Log.e("newData" , "value of httpURLConnection1 " + httpURLConnection1);
                InputStream inputStream1 = httpURLConnection1.getInputStream();
                BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream1));
                String line1 = "";
                String data1 = "";

                while (line1 != null) {
                    line1 = bufferedReader1.readLine();
                    data1 = data1 + line1;
                }

//            Log.e("newData" , "value of data " + data1);

                JSONObject jsonObject = new JSONObject(data1);
                String data = jsonObject.getString("Data");
                String status = jsonObject.getString("status");
                if (status.equals("success")) {
                    Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
                    // Log.e("DataItem", "Value of " + dataArray.length());
                    Intent intent = new Intent(TaskThreeAddEdit.this, TaskThree.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
                }

            }catch (Exception rt){
                // Log.e("DataItem",  String.valueOf(rt.toString())); // Log the item
            }
        }



    }


    private void gettingAndSettingValues() {
        //  Getting values through Intent bundle Passing
        id = getIntent().getIntExtra("id", 0);
        name = getIntent().getStringExtra("name");
        fName = getIntent().getStringExtra("fName");
        gender = getIntent().getStringExtra("gender");
        if (id==0) {
            title.setText("Add New");
            btnAddEdit.setText("Add");
        }else{
            title.setText(getIntent().getStringExtra("title"));
            btnAddEdit.setText(getIntent().getStringExtra("btnText"));
        }


        // Setting Values in those EditTextViews
        edtName.setText(name);
        edtFName.setText(fName);
        genderDropDown.setSelected(Boolean.parseBoolean(gender));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // validations
    private Boolean validateName() {
        String name = Objects.requireNonNull(edtName.getText()).toString();
        if (name.isEmpty()) {
            edtName.setError("Name is required");
            return false;
        } else {
            edtName.setError(null);
            return true;
        }

    }

    private Boolean validateFName() {
        String address = Objects.requireNonNull(edtFName.getText()).toString();
        if (address.isEmpty()) {
            edtFName.setError("Father Name is required");
            return false;
        } else {
            edtFName.setError(null);
            return true;
        }

    }
}