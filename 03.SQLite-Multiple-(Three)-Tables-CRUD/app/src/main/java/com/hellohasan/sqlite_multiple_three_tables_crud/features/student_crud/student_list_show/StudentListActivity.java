package com.hellohasan.sqlite_multiple_three_tables_crud.features.student_crud.student_list_show;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.hellohasan.sqlite_multiple_three_tables_crud.R;
import com.hellohasan.sqlite_multiple_three_tables_crud.database.*;
import com.hellohasan.sqlite_multiple_three_tables_crud.features.student_crud.student_create.*;
import com.hellohasan.sqlite_multiple_three_tables_crud.features.subject_crud.subject_list_show.SubjectListActivity;
import com.hellohasan.sqlite_multiple_three_tables_crud.model.Student;
import com.hellohasan.sqlite_multiple_three_tables_crud.model.TableRowCount;
import com.hellohasan.sqlite_multiple_three_tables_crud.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends AppCompatActivity implements StudentCrudListener {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private TextView studentCountTextView;
    private TextView subjectCountTextView;
    private TextView takenSubjectCountTextView;

    private List<Student> studentList = new ArrayList<>();
    private StudentListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initialization();

        adapter = new StudentListAdapter(this, studentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        showStudentList();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentCreateDialogFragment studentCreateDialogFragment = StudentCreateDialogFragment.newInstance("Create Student", StudentListActivity.this);
                studentCreateDialogFragment.show(getSupportFragmentManager(), Constants.CREATE_STUDENT);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showTableRowCount();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_show_subject){
            startActivity(new Intent(this, SubjectListActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStudentListUpdate(boolean isUpdated) {
        if(isUpdated) {
            showStudentList();
            showTableRowCount();
        }
    }

    private void showStudentList() {
        QueryContract.StudentQuery query = new StudentQueryImplementation();
        query.readAllStudent(new QueryResponse<List<Student>>() {
            @Override
            public void onSuccess(List<Student> data) {
                studentList.clear();
                studentList.addAll(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {

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

    private void initialization(){
        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);

        studentCountTextView = findViewById(R.id.studentCount);
        subjectCountTextView = findViewById(R.id.subjectCount);
        takenSubjectCountTextView = findViewById(R.id.takenSubjectCount);
    }
}
