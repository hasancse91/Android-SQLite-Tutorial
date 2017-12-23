package com.hellohasan.sqlite_project.Features.SubjectCRUD.ShowSubjectList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hellohasan.sqlite_project.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    TextView subjectNameTextView;
    TextView subjectCodeTextView;
    TextView subjectCreditTextView;
    ImageView crossButtonImageView;
    ImageView editButtonImageView;

    public CustomViewHolder(View itemView) {
        super(itemView);

        subjectNameTextView = itemView.findViewById(R.id.subjectNameTextView);
        subjectCodeTextView = itemView.findViewById(R.id.subjectCodeTextView);
        subjectCreditTextView = itemView.findViewById(R.id.subjectCreditTextView);
        crossButtonImageView = itemView.findViewById(R.id.crossImageView);
        editButtonImageView = itemView.findViewById(R.id.editImageView);
    }
}
