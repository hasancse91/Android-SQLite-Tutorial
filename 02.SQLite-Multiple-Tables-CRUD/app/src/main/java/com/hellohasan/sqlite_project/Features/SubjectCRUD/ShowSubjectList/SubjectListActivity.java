package com.hellohasan.sqlite_project.Features.SubjectCRUD.ShowSubjectList;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hellohasan.sqlite_project.Features.SubjectCRUD.CreateSubject.Subject;
import com.hellohasan.sqlite_project.Features.SubjectCRUD.CreateSubject.SubjectCreateDialogFragment;
import com.hellohasan.sqlite_project.Features.SubjectCRUD.CreateSubject.SubjectCreateListener;
import com.hellohasan.sqlite_project.R;
import com.hellohasan.sqlite_project.Util.Config;

public class SubjectListActivity extends AppCompatActivity implements SubjectCreateListener {

    private long studentRegNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);

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

    }
}
