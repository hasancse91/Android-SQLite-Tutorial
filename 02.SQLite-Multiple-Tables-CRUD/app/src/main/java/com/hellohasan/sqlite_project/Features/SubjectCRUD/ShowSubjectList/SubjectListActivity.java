package com.hellohasan.sqlite_project.Features.SubjectCRUD.ShowSubjectList;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hellohasan.sqlite_project.Database.DatabaseQueryClass;
import com.hellohasan.sqlite_project.Features.SubjectCRUD.CreateSubject.Subject;
import com.hellohasan.sqlite_project.Features.SubjectCRUD.CreateSubject.SubjectCreateDialogFragment;
import com.hellohasan.sqlite_project.Features.SubjectCRUD.CreateSubject.SubjectCreateListener;
import com.hellohasan.sqlite_project.R;
import com.hellohasan.sqlite_project.Util.Config;

import java.util.ArrayList;
import java.util.List;

public class SubjectListActivity extends AppCompatActivity implements SubjectCreateListener {

    private long studentRegNo;

    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);

    private List<Subject> subjectList = new ArrayList<>();

    private TextView summaryTextView;
    private TextView subjectListEmptyTextView;
    private RecyclerView recyclerView;
    private SubjectListRecyclerViewAdapter subjectListRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        summaryTextView = findViewById(R.id.summaryTextView);
        subjectListEmptyTextView = findViewById(R.id.emptyListTextView);

        studentRegNo = getIntent().getLongExtra(Config.STUDENT_REGISTRATION, -1);

        subjectList.addAll(databaseQueryClass.getAllSubjectsByRegNo(studentRegNo));

        subjectListRecyclerViewAdapter = new SubjectListRecyclerViewAdapter(this, subjectList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(subjectListRecyclerViewAdapter);

        viewVisibility();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSubjectCreateDialog();
            }
        });
    }

    private void printSummary() {
        long studentNum = databaseQueryClass.getNumberOfStudent();
        long subjectNum = databaseQueryClass.getNumberOfSubject();

        summaryTextView.setText(getResources().getString(R.string.database_summary, studentNum, subjectNum));
    }

    private void openSubjectCreateDialog() {
        SubjectCreateDialogFragment subjectCreateDialogFragment = SubjectCreateDialogFragment.newInstance(studentRegNo, this);
        subjectCreateDialogFragment.show(getSupportFragmentManager(), Config.CREATE_SUBJECT);
    }

    @Override
    public void onSubjectCreated(Subject subject) {
        subjectList.add(subject);
        subjectListRecyclerViewAdapter.notifyDataSetChanged();
        viewVisibility();
    }

    public void viewVisibility() {
        if(subjectList.isEmpty())
            subjectListEmptyTextView.setVisibility(View.VISIBLE);
        else
            subjectListEmptyTextView.setVisibility(View.GONE);
        printSummary();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_delete:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Are you sure, You wanted to delete all subjects?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                boolean isAllDeleted = databaseQueryClass.deleteAllSubjectsByRegNum(studentRegNo);
                                if(isAllDeleted){
                                    subjectList.clear();
                                    subjectListRecyclerViewAdapter.notifyDataSetChanged();
                                    viewVisibility();
                                }
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

}
