package com.hellohasan.sqlite_multiple_three_tables_crud.features.taken_subject_crud.subject_assign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hellohasan.sqlite_multiple_three_tables_crud.R;
import com.hellohasan.sqlite_multiple_three_tables_crud.database.*;
import com.hellohasan.sqlite_multiple_three_tables_crud.database.SubjectQueryImplementation;
import com.hellohasan.sqlite_multiple_three_tables_crud.model.Subject;

import java.util.ArrayList;
import java.util.List;

import static com.hellohasan.sqlite_multiple_three_tables_crud.util.Constants.STUDENT_ID;

public class SubjectAssignActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView noDataFoundTextView;

    private int studentId;

    private List<Subject> subjectList = new ArrayList<>();
    private SubjectAssignListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_assign);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        recyclerView = findViewById(R.id.recyclerView);
        noDataFoundTextView = findViewById(R.id.noDataFoundTextView);

        studentId = getIntent().getIntExtra(STUDENT_ID, -1);

        adapter = new SubjectAssignListAdapter(this, studentId, subjectList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        QueryContract.SubjectQuery query = new SubjectQueryImplementation();
        query.readAllSubject(new QueryResponse<List<Subject>>() {
            @Override
            public void onSuccess(List<Subject> data) {
                recyclerView.setVisibility(View.VISIBLE);
                noDataFoundTextView.setVisibility(View.GONE);

                subjectList.clear();
                subjectList.addAll(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                recyclerView.setVisibility(View.GONE);
                noDataFoundTextView.setVisibility(View.VISIBLE);
            }
        });

    }

    public void closeActivity(View view) {
        finish();
    }
}
