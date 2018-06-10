package com.hellohasan.sqlite_multiple_three_tables_crud.features.student_crud.student_show;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hellohasan.sqlite_multiple_three_tables_crud.R;

class StudentViewHolder extends RecyclerView.ViewHolder {

    TextView nameTextView;
    TextView registrationNumTextView;
    TextView emailTextView;
    TextView phoneTextView;
    ImageView editImageView;
    ImageView deleteImageView;

    public StudentViewHolder(View itemView) {
        super(itemView);

        nameTextView = itemView.findViewById(R.id.nameTextView);
        registrationNumTextView = itemView.findViewById(R.id.registrationNumTextView);
        emailTextView = itemView.findViewById(R.id.emailTextView);
        phoneTextView = itemView.findViewById(R.id.phoneTextView);
        editImageView = itemView.findViewById(R.id.editImageView);
        deleteImageView = itemView.findViewById(R.id.deleteImageView);
    }
}
