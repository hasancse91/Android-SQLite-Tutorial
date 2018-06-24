package com.hellohasan.sqlite_multiple_three_tables_crud.features.taken_subject_crud.subject_assign;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.hellohasan.sqlite_multiple_three_tables_crud.R;
import com.hellohasan.sqlite_multiple_three_tables_crud.database.QueryContract;
import com.hellohasan.sqlite_multiple_three_tables_crud.database.QueryResponse;
import com.hellohasan.sqlite_multiple_three_tables_crud.database.TakenSubjectQueryImplementation;
import com.hellohasan.sqlite_multiple_three_tables_crud.model.Subject;

import java.util.List;

public class SubjectAssignListAdapter extends RecyclerView.Adapter<SubjectAssignViewHolder> {

    private Context context;
    private int studentId;
    private List<Subject> subjectList;

    public SubjectAssignListAdapter(Context context, int studentId, List<Subject> subjectList) {
        this.context = context;
        this.studentId = studentId;
        this.subjectList = subjectList;
    }

    @NonNull
    @Override
    public SubjectAssignViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_subject_assign, parent, false);
        return new SubjectAssignViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectAssignViewHolder holder,  int position) {
        final Subject subject = subjectList.get(position);

        holder.subjectNameTextView.setText(subject.getName());
        holder.courseCodeTextView.setText(context.getString(R.string.course_code, subject.getCode()));
        holder.creditTextView.setText(context.getString(R.string.course_credit, subject.getCredit()));

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    assignSubject(subject.getId());
                else
                    removeAssignedSubject(subject.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    private void assignSubject(int subjectId) {
        QueryContract.TakenSubjectQuery query = new TakenSubjectQueryImplementation();
        query.createTakenSubject(studentId, subjectId, new QueryResponse<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {

            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void removeAssignedSubject(int subjectId) {
        QueryContract.TakenSubjectQuery query = new TakenSubjectQueryImplementation();
        query.deleteTakenSubject(studentId, subjectId, new QueryResponse<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {

            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
