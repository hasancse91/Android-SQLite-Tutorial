package com.hellohasan.sqlite_multiple_three_tables_crud.features.subject_crud.subject_list_show;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.hellohasan.sqlite_multiple_three_tables_crud.R;
import com.hellohasan.sqlite_multiple_three_tables_crud.database.QueryContract;
import com.hellohasan.sqlite_multiple_three_tables_crud.database.QueryResponse;
import com.hellohasan.sqlite_multiple_three_tables_crud.database.TableRowCountQueryImplementation;
import com.hellohasan.sqlite_multiple_three_tables_crud.features.student_crud.student_create.StudentCreateDialogFragment;
import com.hellohasan.sqlite_multiple_three_tables_crud.features.student_crud.student_list_show.StudentListActivity;
import com.hellohasan.sqlite_multiple_three_tables_crud.features.subject_crud.subject_create.SubjectCreateDialogFragment;
import com.hellohasan.sqlite_multiple_three_tables_crud.features.subject_crud.subject_create.SubjectCrudListener;
import com.hellohasan.sqlite_multiple_three_tables_crud.model.TableRowCount;
import com.hellohasan.sqlite_multiple_three_tables_crud.util.Constants;

public class SubjectListActivity extends AppCompatActivity implements SubjectCrudListener {

    private TextView studentCountTextView;
    private TextView subjectCountTextView;
    private TextView takenSubjectCountTextView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initialization();

        showTableRowCount();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubjectCreateDialogFragment dialogFragment = SubjectCreateDialogFragment.newInstance("Create Subject", SubjectListActivity.this);
                dialogFragment.show(getSupportFragmentManager(), Constants.CREATE_STUDENT);

            }
        });
    }

    @Override
    public void onSubjectListUpdate(boolean isUpdate) {
        if(isUpdate) {

            showTableRowCount();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
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
        studentCountTextView = findViewById(R.id.studentCount);
        subjectCountTextView = findViewById(R.id.subjectCount);
        takenSubjectCountTextView = findViewById(R.id.takenSubjectCount);

        fab = findViewById(R.id.fab);
    }


}
