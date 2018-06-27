package com.hellohasan.sqlite_multiple_three_tables_crud.features.taken_subject_crud.subject_assign;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hellohasan.sqlite_multiple_three_tables_crud.R;

public class SubjectAssignViewHolder extends RecyclerView.ViewHolder {

    CheckBox checkBox;
    TextView subjectNameTextView;
    TextView courseCodeTextView;
    TextView creditTextView;

    public SubjectAssignViewHolder(View itemView) {
        super(itemView);
        checkBox = itemView.findViewById(R.id.checkbox);
        subjectNameTextView = itemView.findViewById(R.id.subjectNameTextView);
        courseCodeTextView = itemView.findViewById(R.id.courseCodeTextView);
        creditTextView = itemView.findViewById(R.id.creditTextView);
    }
}
