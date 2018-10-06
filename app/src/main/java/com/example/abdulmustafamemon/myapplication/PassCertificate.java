package com.example.abdulmustafamemon.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class PassCertificate extends AppCompatActivity {
    TextView fullname,fatherName,rollNo,dept ,doe,position,cgpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_certificate);
        fullname =   findViewById(R.id.firstname);
        fatherName =   findViewById(R.id.fatherName);
        rollNo =   findViewById(R.id.rollNo);
        dept =   findViewById(R.id.dept);
         doe =   findViewById(R.id.exam);

        position =   findViewById(R.id.position);
        cgpa =   findViewById(R.id.cgpa);




        Intent intent1 = getIntent();
        fullname.setText("Full Name: "+intent1.getStringExtra("fname"));
        fatherName.setText("Father Name: "+intent1.getStringExtra("fatName"));
        rollNo.setText("Roll No: "+intent1.getStringExtra("rollno"));
        dept.setText("Department Name: "+intent1.getStringExtra("dept"));
         doe.setText("Date of Examination: "+intent1.getStringExtra("doe"));

         position.setText("Position: "+intent1.getStringExtra("position"));
        cgpa.setText("CGPA: "+intent1.getStringExtra("cgpa"));


    }
}
