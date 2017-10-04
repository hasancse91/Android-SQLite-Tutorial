package com.hellohasan.sqlite_project.StudentCrud;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hellohasan.sqlite_project.Config;
import com.hellohasan.sqlite_project.StudentCrud.CreateStudent.StudentCreateDialogFragment;
import com.hellohasan.sqlite_project.StudentCrud.CreateStudent.StudentCreateListener;
import com.hellohasan.sqlite_project.R;
import com.hellohasan.sqlite_project.Student;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class StudentListActivity extends AppCompatActivity implements StudentCreateListener{

    private Student mStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Logger.addLogAdapter(new AndroidLogAdapter());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                StudentCreateListener studentCreateListener = new StudentCreateListener() {
//                    @Override
//                    public void onStudentCreated(Student student) {
//                        mStudent = student;
//                        Logger.d(mStudent.getName());
//                    }
//                };
                openDialog();



            }
        });
    }

    private void openDialog() {
        StudentCreateDialogFragment studentCreateDialogFragment = StudentCreateDialogFragment.newInstance("Create Student", this);
        studentCreateDialogFragment.show(getSupportFragmentManager(), Config.CREATE_STUDENT);
    }

    @Override
    public void onStudentCreated(Student student) {
        mStudent = student;
        Logger.d(mStudent.getName());
    }
}
