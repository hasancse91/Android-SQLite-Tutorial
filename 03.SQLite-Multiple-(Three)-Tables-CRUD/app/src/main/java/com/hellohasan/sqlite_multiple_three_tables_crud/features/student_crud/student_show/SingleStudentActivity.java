package com.hellohasan.sqlite_multiple_three_tables_crud.features.student_crud.student_show;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.hellohasan.sqlite_multiple_three_tables_crud.R;

import static com.hellohasan.sqlite_multiple_three_tables_crud.util.Constants.*;

public class SingleStudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_student);

        long studentId = getIntent().getLongExtra(STUDENT_REGISTRATION_NUM, -1);



    }
}
