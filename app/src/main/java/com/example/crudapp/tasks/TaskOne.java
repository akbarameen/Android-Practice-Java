package com.example.crudapp.tasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.crudapp.common.AddNew;
import com.example.crudapp.database.DbHelper;
import com.example.crudapp.R;
import com.example.crudapp.adapters.RVStudentAdapter;
import com.example.crudapp.models.StudentModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class TaskOne extends AppCompatActivity {
    FloatingActionButton btnAddDialog, btnAddActivity;
    // ArrayList for gender Dropdown
    ArrayList<String> arrGender = new ArrayList<>();

    // dialog items
    EditText edtName, edtAddress;
    Spinner dropDown;
    RecyclerView RVStudent;
    ArrayList<StudentModel> studentModelArrayList;
    RVStudentAdapter studentAdapter;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // for removing the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tast_one);

        btnAddActivity = findViewById(R.id.btn_add);
        btnAddDialog = findViewById(R.id.btn_dialog);

        //adding data into arrayList
        arrGender.add("Male");
        arrGender.add("Female");

        btnAddActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskOne.this, AddNew.class);
                intent.putExtra("title", "Add New Record");
                intent.putExtra("btnText", "Add");
                startActivity(intent);

            }
        });

        btnAddDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDataDialog();
            }
        });

        // Calling method getStudents
        getStudents();
    }





    public void addDataDialog(){
        // Dialog data

        final Dialog dialog = new Dialog(TaskOne.this);
        dialog.setContentView(R.layout.custom_dialog);

        // Getting IDs
        Button BtnDialogAdd = (Button) dialog.findViewById(R.id.btn_add_record);
        edtName = dialog.findViewById(R.id.edt_name);
        edtAddress = dialog.findViewById(R.id.edt_address);
        Spinner dropDown = (Spinner) dialog.findViewById(R.id.list_gender);

        // Setting Adapter on Dropdown List
        ArrayAdapter<String> adapter = new ArrayAdapter<>(TaskOne.this, android.R.layout.simple_spinner_dropdown_item,arrGender);
        dropDown.setAdapter(adapter);

        // Saving Data
        BtnDialogAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Getting data from editTextViews and parsing them into String
                DbHelper dbHelper = new DbHelper(TaskOne.this);
                String name = Objects.requireNonNull(edtName.getText()).toString();
                String address = Objects.requireNonNull(edtAddress.getText()).toString();
                String gender = dropDown.getSelectedItem().toString();

                if (validateName() || validateAddress()) {
                    // Calling the method inside dpHelper class and putting parameters
                    boolean insert = dbHelper.addStudent(name, address, gender);

                    if(insert){
                        Toast.makeText(TaskOne.this, "Added Successfully", Toast.LENGTH_SHORT).show();

                        getStudents();
                        // Intent intent = new Intent(TaskOne.this, TaskOne.class);
                        // startActivity(intent);
                        edtName.setText("");
                        edtAddress.setText("");


                        studentAdapter.notifyItemChanged(studentModelArrayList.size() -1);
                        RVStudent.scrollToPosition(studentModelArrayList.size()-1);

                    }else{
                        Toast.makeText(TaskOne.this, "Adding Failed", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }

            }
        });
        dialog.show();
    }

    // for displaying data form the database
    public void getStudents(){
        RVStudent = findViewById(R.id.rv_student);
        studentModelArrayList = new ArrayList<>();
        dbHelper = new DbHelper(this);


        studentModelArrayList = dbHelper.selectStudent();

        studentAdapter = new RVStudentAdapter(this, studentModelArrayList);
        RVStudent.setLayoutManager(new LinearLayoutManager(this));
        RVStudent.setAdapter(studentAdapter);

        studentAdapter.notifyItemChanged(studentModelArrayList.size() -1);
        RVStudent.scrollToPosition(studentModelArrayList.size()-1);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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