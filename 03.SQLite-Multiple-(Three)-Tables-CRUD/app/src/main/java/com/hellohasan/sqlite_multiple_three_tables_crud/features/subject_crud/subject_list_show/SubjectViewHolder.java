package com.hellohasan.sqlite_multiple_three_tables_crud.features.subject_crud.subject_list_show;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hellohasan.sqlite_multiple_three_tables_crud.R;

public class SubjectViewHolder extends RecyclerView.ViewHolder {

    TextView subjectNameTextView;
    TextView courseCodeTextView;
    TextView creditTextView;
    ImageView editIcon;
    ImageView deleteIcon;

    public SubjectViewHolder(View itemView) {
        super(itemView);

        subjectNameTextView = itemView.findViewById(R.id.subjectNameTextView);
        courseCodeTextView = itemView.findViewById(R.id.courseCodeTextView);
        creditTextView = itemView.findViewById(R.id.creditTextView);
        editIcon = itemView.findViewById(R.id.editIcon);
        deleteIcon = itemView.findViewById(R.id.deleteIcon);
    }
}
