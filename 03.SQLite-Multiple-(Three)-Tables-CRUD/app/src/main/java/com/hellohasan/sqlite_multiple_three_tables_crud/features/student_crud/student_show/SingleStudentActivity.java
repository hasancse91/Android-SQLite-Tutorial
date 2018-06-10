package com.hellohasan.sqlite_multiple_three_tables_crud.features.student_crud.student_show;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.hellohasan.sqlite_multiple_three_tables_crud.R;
import com.hellohasan.sqlite_multiple_three_tables_crud.database.QueryContract;
import com.hellohasan.sqlite_multiple_three_tables_crud.database.QueryResponse;
import com.hellohasan.sqlite_multiple_three_tables_crud.database.StudentQueryImplementation;
import com.hellohasan.sqlite_multiple_three_tables_crud.model.Student;

import static com.hellohasan.sqlite_multiple_three_tables_crud.util.Constants.*;

public class SingleStudentActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView registrationNumTextView;
    private TextView emailTextView;
    private TextView phoneTextView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_student);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initialization();

        int studentId = getIntent().getIntExtra(STUDENT_ID, -1);

        showStudentInfo(studentId);

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

    private void initialization() {
        nameTextView = findViewById(R.id.nameTextView);
        registrationNumTextView = findViewById(R.id.registrationNumTextView);
        emailTextView = findViewById(R.id.emailTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
