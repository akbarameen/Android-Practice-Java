package com.example.crudapp.taskFive;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.crudapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TaskFiveActivity extends AppCompatActivity {
FloatingActionButton fBtn_add;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_five);

        // Assuming you have a button named "openDialogButton"
        fBtn_add = findViewById(R.id.fBtn_add);

        fBtn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate your dialog layout and find the EditText fields
                AlertDialog.Builder builder = new AlertDialog.Builder(TaskFiveActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.add_counter_sale_dialog, null);
                final EditText inputField1 = dialogView.findViewById(R.id.inputField1);
                final EditText inputField2 = dialogView.findViewById(R.id.inputField2);

                // Set initial value of inputField2
                inputField2.setText(String.valueOf(300));

                // Add a TextWatcher to inputField1
                inputField1.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // Not used, but required to implement
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // Parse the entered number and multiply with price
                        try {
                            int enteredValue = Integer.parseInt(s.toString());
                            int result = enteredValue * 300;
                            inputField2.setText(String.valueOf(result));
                        } catch (NumberFormatException e) {
                            // Handle invalid input
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        // Not used, but required to implement
                    }
                });

                // Set other properties of the dialog (if any) and show it
                builder.setView(dialogView);
                builder.create().show();
            }
        });

    }
    public void onMinusClick(View view) {
        EditText inputField1 = findViewById(R.id.inputField1);
        int value = Integer.parseInt(inputField1.getText().toString());
        value--;
        inputField1.setText(String.valueOf(value));
    }

    public void onPlusClick(View view) {
        EditText inputField1 = findViewById(R.id.inputField1);
        int value = Integer.parseInt(inputField1.getText().toString());
        value++;
        inputField1.setText(String.valueOf(value));
    }

}