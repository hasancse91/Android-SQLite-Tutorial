package com.hellohasan.sqlite_multiple_three_tables_crud.features.taken_subject_crud.subject_assign;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hellohasan.sqlite_multiple_three_tables_crud.R;
import com.hellohasan.sqlite_multiple_three_tables_crud.model.Subject;

import java.util.List;

public class SubjectAssignListAdapter extends RecyclerView.Adapter<SubjectAssignViewHolder> {

    private Context context;
    private List<Subject> subjectList;

    public SubjectAssignListAdapter(Context context, List<Subject> subjectList) {
        this.context = context;
        this.subjectList = subjectList;
    }

    @NonNull
    @Override
    public SubjectAssignViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_subject_assign, parent, false);
        return new SubjectAssignViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectAssignViewHolder holder, int position) {
        Subject subject = subjectList.get(position);

        holder.subjectNameTextView.setText(subject.getName());
        holder.courseCodeTextView.setText(context.getString(R.string.course_code, subject.getCode()));
        holder.creditTextView.setText(context.getString(R.string.course_credit, subject.getCredit()));
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }
}
