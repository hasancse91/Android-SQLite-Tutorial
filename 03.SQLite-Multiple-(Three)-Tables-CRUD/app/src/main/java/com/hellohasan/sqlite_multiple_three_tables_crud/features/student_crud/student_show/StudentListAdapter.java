package com.hellohasan.sqlite_multiple_three_tables_crud.features.student_crud.student_show;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hellohasan.sqlite_multiple_three_tables_crud.R;
import com.hellohasan.sqlite_multiple_three_tables_crud.model.Student;

import java.util.List;

import static com.hellohasan.sqlite_multiple_three_tables_crud.util.Constants.*;

public class StudentListAdapter extends RecyclerView.Adapter<StudentViewHolder> {

    private Context context;
    private List<Student> studentList;

    StudentListAdapter(Context context, List<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_student_card_view, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        final Student student = studentList.get(position);

        holder.nameTextView.setText(student.getName());
        holder.registrationNumTextView.setText(String.valueOf(student.getRegistrationNumber()));
        holder.emailTextView.setText(student.getEmail());
        holder.phoneTextView.setText(student.getPhone());

        holder.editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleStudentActivity.class);
                intent.putExtra(STUDENT_ID, student.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }
}
