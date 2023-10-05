package com.example.crudapp.common;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crudapp.R;
import com.example.crudapp.database.DbHelper;
import com.example.crudapp.tasks.TaskOne;

import java.util.ArrayList;
import java.util.Objects;

public class AddNew extends AppCompatActivity {
    EditText edtName, edtAddress;
    TextView title;
    Spinner dropDown;
    Button addBtn;


    // String for saving
    String studentName, studentAddress, studentGender;

    int studentId =0;

    ArrayList<String> arrGender = new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_add_new);

        // Finding Ids
        edtName = findViewById(R.id.edt_name);
        edtAddress = findViewById(R.id.edt_address);
        dropDown = findViewById(R.id.genders_list);
        addBtn = findViewById(R.id.btn_add_record_act);
        title = findViewById(R.id.add_title);

        // adding data into Gender ArrayList
        arrGender.add("Male");
        arrGender.add("Female");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,arrGender);
        dropDown.setAdapter(adapter);

        gettingAndSettingValues();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
            }
        });





    }
    public void addData(){
        DbHelper dbHelper = new DbHelper(AddNew.this);
        String name = Objects.requireNonNull(edtName.getText()).toString();
        String address = Objects.requireNonNull(edtAddress.getText()).toString();
        String gender = dropDown.getSelectedItem().toString();


        if (studentId==0) {

            if (validateName() || validateAddress()) {
                boolean insert = dbHelper.addStudent(name, address, gender);

                if(insert){
                    Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddNew.this, TaskOne.class);
                    edtName.setText("");
                    edtAddress.setText("");
                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(this, "Adding Failed", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "please enter you name", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            if (validateName() || validateAddress()) {
                //Calling update method from DbHelper
                dbHelper.updateStudent(studentId, edtName.getText().toString(), edtAddress.getText().toString(), dropDown.getSelectedItem().toString() );

                // after updating going back to mainActivity
                Toast.makeText(AddNew.this, "Student Updated", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(AddNew.this, TaskOne.class);
                startActivity(i);
                finish();
            }

        }
    }
    public void gettingAndSettingValues(){

        //  Getting values through Intent bundle Passing
        studentId = getIntent().getIntExtra("id", 0);
        studentName = getIntent().getStringExtra("name");
        studentAddress = getIntent().getStringExtra("address");
        studentGender = getIntent().getStringExtra("gender");
        title.setText(getIntent().getStringExtra("title"));
        addBtn.setText(getIntent().getStringExtra("btnText"));

        // Setting Values in those EditTextViews
        edtName.setText(studentName);
        edtAddress.setText(studentAddress);
        dropDown.setSelected(Boolean.parseBoolean(studentGender));

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

    private Boolean validateAddress() {
        String address = Objects.requireNonNull(edtAddress.getText()).toString();
        if (address.isEmpty()) {
            edtAddress.setError("Address is required");
            return false;
        } else {
            edtAddress.setError(null);
            return true;
        }

    }
}