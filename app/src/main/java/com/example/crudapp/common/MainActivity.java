package com.example.crudapp.common;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.crudapp.R;
import com.example.crudapp.taskFour.TaskFourActivity;
import com.example.crudapp.tasks.TaskOne;
import com.example.crudapp.tasks.TaskThree;
import com.example.crudapp.tasks.TaskTwo;

public class MainActivity extends AppCompatActivity {
Button taskOne, taskTwo, taskThree, taskFour;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskOne = findViewById(R.id.taskOne);
        taskTwo = findViewById(R.id.taskTwo);
        taskThree = findViewById(R.id.taskThree);
        taskFour = findViewById(R.id.taskFour);

        taskOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TaskOne.class);
                startActivity(intent);
            }
        });
        taskTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TaskTwo.class);
                startActivity(intent);
            }
        });

        taskThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TaskThree.class);
                startActivity(intent);
            }
        });

        taskFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TaskFourActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}