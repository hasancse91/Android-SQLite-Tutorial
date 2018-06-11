package com.hellohasan.sqlite_multiple_three_tables_crud.features.taken_subject_crud.taken_subject_show;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hellohasan.sqlite_multiple_three_tables_crud.R;
import com.hellohasan.sqlite_multiple_three_tables_crud.model.Subject;

import java.util.List;

public class TakenSubjectListAdapter extends RecyclerView.Adapter<TakenSubjectViewHolder> {

    private Context context;
    private List<Subject> subjectList;

    public TakenSubjectListAdapter(Context context, List<Subject> subjectList) {
        this.context = context;
        this.subjectList = subjectList;
    }

    @NonNull
    @Override
    public TakenSubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_taken_subject, parent, false);
        return new TakenSubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TakenSubjectViewHolder holder, int position) {
        Subject subject = subjectList.get(position);

        holder.subjectNameTextView.setText(subject.getName());
        holder.courseCodeTextView.setText(context.getString(R.string.course_code, subject.getCode()));
        holder.creditTextView.setText(context.getString(R.string.course_credit, subject.getCredit()));

        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }
}
