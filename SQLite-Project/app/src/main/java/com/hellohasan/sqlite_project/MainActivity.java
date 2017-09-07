package com.hellohasan.sqlite_project;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hellohasan.sqlite_project.CreateStudent.StudentCreateDialogFragment;
import com.hellohasan.sqlite_project.CreateStudent.StudentCreateListener;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity {

    private Student mStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Logger.addLogAdapter(new AndroidLogAdapter());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentCreateListener studentCreateListener = new StudentCreateListener() {
                    @Override
                    public void onStudentCreated(Student student) {
                        mStudent = student;
                        Logger.d(mStudent.getName());
                    }
                };

                StudentCreateDialogFragment studentCreateDialogFragment = StudentCreateDialogFragment.newInstance("Create Student", studentCreateListener);
                studentCreateDialogFragment.show(getSupportFragmentManager(), "create_student");

            }
        });
    }

}
