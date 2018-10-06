package com.example.abdulmustafamemon.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Degree extends AppCompatActivity {
    TextView fullname,fatherName,rollNo,dept,cgpa ,doe,dod ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_degree);


        fullname = findViewById(R.id.firstname);
        fatherName = findViewById(R.id.fatherName);
        rollNo  =   findViewById(R.id.rollNo);
        dept   =    findViewById(R.id.dept);
        cgpa   =    findViewById(R.id.cgpa);
        dod   =    findViewById(R.id.dod);
        doe  =   findViewById(R.id.exam);


        Intent intent1 = getIntent();
        fullname.setText("Full Name: "+intent1.getStringExtra("fname"));
        fatherName.setText("Father Name: "+intent1.getStringExtra("fatName"));
        rollNo.setText("Roll No: "+intent1.getStringExtra("rollno"));
        cgpa.setText("CGPA: "+intent1.getStringExtra("cgpa"));


        doe.setText("Date of Examination: "+intent1.getStringExtra("doe"));
        dod.setText("Date of Declaration: "+intent1.getStringExtra("dod"));
        dept.setText("Department: "+intent1.getStringExtra("dept"));

    }
}