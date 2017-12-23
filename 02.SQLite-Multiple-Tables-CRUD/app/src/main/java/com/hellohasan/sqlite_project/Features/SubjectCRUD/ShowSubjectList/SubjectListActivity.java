package com.hellohasan.sqlite_project.Features.SubjectCRUD.ShowSubjectList;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    private TextView subjectListEmptyTextView;
    private RecyclerView recyclerView;
    private SubjectListRecyclerViewAdapter subjectListRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        subjectListEmptyTextView = (TextView) findViewById(R.id.emptyListTextView);

        studentRegNo = getIntent().getLongExtra(Config.STUDENT_REGISTRATION, -1);

        subjectList.addAll(databaseQueryClass.getAllSubjectsByRegNo(studentRegNo));

        subjectListRecyclerViewAdapter = new SubjectListRecyclerViewAdapter(this, subjectList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(subjectListRecyclerViewAdapter);

        viewVisibility();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSubjectCreateDialog();
            }
        });
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
    }
}
