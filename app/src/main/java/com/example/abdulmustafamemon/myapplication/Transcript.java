package com.example.abdulmustafamemon.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Transcript extends AppCompatActivity {
    TextView fullname,fatherName,rollNo,dept,position,cgpa,dop,enroll,doa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transcript);

        fullname =  findViewById(R.id.firstname);
        fatherName =  findViewById(R.id.fatherName);
        rollNo =  findViewById(R.id.rollNo);
        dept =  findViewById(R.id.dept);

        cgpa =  findViewById(R.id.cgpa);
        dop =  findViewById(R.id.dop);
        enroll =  findViewById(R.id.enroll);
        doa =  findViewById(R.id.doa);
   position = findViewById(R.id.position);


        Intent intent1 = getIntent();
        fullname.setText("Full Name: "+intent1.getStringExtra("fname"));
        fatherName.setText("Father Name: "+intent1.getStringExtra("fatName"));
        rollNo.setText("Roll No: "+intent1.getStringExtra("rollno"));
        dept.setText("Department Name: "+intent1.getStringExtra("dept"));


        cgpa.setText("CGPA: "+intent1.getStringExtra("cgpa"));
        dop.setText("Date of Passing: "+intent1.getStringExtra("dop"));
        enroll.setText("Enrollment No: "+intent1.getStringExtra("enroll"));
        doa.setText("Date of Admission: "+intent1.getStringExtra("doa"));
        position.setText("Position: "+intent1.getStringExtra("position"));
    }
}
