package com.hellohasan.sqlite_multiple_three_tables_crud.features.student_crud.student_list_show;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hellohasan.sqlite_multiple_three_tables_crud.R;
import com.hellohasan.sqlite_multiple_three_tables_crud.database.*;
import com.hellohasan.sqlite_multiple_three_tables_crud.model.Student;

import static com.hellohasan.sqlite_multiple_three_tables_crud.util.Constants.*;

public class SingleStudentActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView registrationNumTextView;
    private TextView emailTextView;
    private TextView phoneTextView;
    private ImageView actionAddSubject;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_student);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initialization();

        int studentId = getIntent().getIntExtra(STUDENT_ID, -1);

        showStudentInfo(studentId);
        showTakenSubjectList(studentId);

        actionAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void showStudentInfo(final int studentId) {
        QueryContract.StudentQuery query = new StudentQueryImplementation();
        query.readStudent(studentId, new QueryResponse<Student>() {
            @Override
            public void onSuccess(Student student) {
                nameTextView.setText(student.getName());
                registrationNumTextView.setText(String.valueOf(student.getRegistrationNumber()));
                emailTextView.setText(student.getEmail());
                phoneTextView.setText(student.getPhone());
            }

            @Override
            public void onFailure(String message) {
                showToast(message);
            }
        });
    }

    private void showTakenSubjectList(int studentId) {

    }

    private void initialization() {
        nameTextView = findViewById(R.id.nameTextView);
        registrationNumTextView = findViewById(R.id.registrationNumTextView);
        emailTextView = findViewById(R.id.emailTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        actionAddSubject = findViewById(R.id.action_add_subject);
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
