package com.hellohasan.sqlite_multiple_three_tables_crud.features.taken_subject_crud.taken_subject_show;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hellohasan.sqlite_multiple_three_tables_crud.R;
import com.hellohasan.sqlite_multiple_three_tables_crud.database.*;
import com.hellohasan.sqlite_multiple_three_tables_crud.features.taken_subject_crud.TakenSubjectCrudListener;
import com.hellohasan.sqlite_multiple_three_tables_crud.features.taken_subject_crud.subject_assign.SubjectAssignActivity;
import com.hellohasan.sqlite_multiple_three_tables_crud.model.Student;
import com.hellohasan.sqlite_multiple_three_tables_crud.model.Subject;
import com.hellohasan.sqlite_multiple_three_tables_crud.model.TableRowCount;

import java.util.ArrayList;
import java.util.List;

import static com.hellohasan.sqlite_multiple_three_tables_crud.util.Constants.*;

public class StudentTakenSubjectActivity extends AppCompatActivity implements TakenSubjectCrudListener {

    private TextView nameTextView;
    private TextView registrationNumTextView;
    private TextView emailTextView;
    private TextView phoneTextView;
    private ImageView actionAddSubject;

    private RecyclerView recyclerView;
    private TextView noDataFoundTextView;
    private TextView studentCountTextView;
    private TextView subjectCountTextView;
    private TextView takenSubjectCountTextView;

    private int studentId;
    private List<Subject> takenSubjectList = new ArrayList<>();
    private TakenSubjectListAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_taken_subject);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initialization();

        studentId = getIntent().getIntExtra(STUDENT_ID, -1);

        adapter = new TakenSubjectListAdapter(this, studentId, takenSubjectList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        showStudentInfo();

        actionAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentTakenSubjectActivity.this, SubjectAssignActivity.class);
                intent.putExtra(STUDENT_ID, studentId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showTableRowCount();
        showTakenSubjectList();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onTakenSubjectUpdated(boolean isUpdated) {
        showTableRowCount();
    }

    private void showStudentInfo() {
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

    private void showTakenSubjectList() {
        QueryContract.TakenSubjectQuery query = new TakenSubjectQueryImplementation();
        query.readAllTakenSubjectByStudentId(studentId, new QueryResponse<List<Subject>>() {
            @Override
            public void onSuccess(List<Subject> data) {
                recyclerView.setVisibility(View.VISIBLE);
                noDataFoundTextView.setVisibility(View.GONE);

                takenSubjectList.clear();
                takenSubjectList.addAll(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                recyclerView.setVisibility(View.GONE);
                noDataFoundTextView.setVisibility(View.VISIBLE);
                noDataFoundTextView.setText(message);
            }
        });

    }

    private void showTableRowCount() {
        QueryContract.TableRowCountQuery query = new TableRowCountQueryImplementation();
        query.getTableRowCount(new QueryResponse<TableRowCount>() {
            @Override
            public void onSuccess(TableRowCount data) {
                studentCountTextView.setText(getString(R.string.student_count, data.getStudentRow()));
                subjectCountTextView.setText(getString(R.string.subject_count, data.getSubjectRow()));
                takenSubjectCountTextView.setText(getString(R.string.taken_subject_count, data.getTakenSubjectRow()));
            }

            @Override
            public void onFailure(String message) {
                studentCountTextView.setText(getString(R.string.table_row_count_failed));
                subjectCountTextView.setText(message);
                takenSubjectCountTextView.setText("");
            }
        });
    }

    private void initialization() {
        nameTextView = findViewById(R.id.nameTextView);
        registrationNumTextView = findViewById(R.id.registrationNumTextView);
        emailTextView = findViewById(R.id.emailTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        actionAddSubject = findViewById(R.id.action_add_subject);

        recyclerView = findViewById(R.id.recyclerView);
        noDataFoundTextView = findViewById(R.id.noDataFoundTextView);

        studentCountTextView = findViewById(R.id.studentCount);
        subjectCountTextView = findViewById(R.id.subjectCount);
        takenSubjectCountTextView = findViewById(R.id.takenSubjectCount);
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
