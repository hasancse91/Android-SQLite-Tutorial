package com.hellohasan.sqlite_project.StudentCrud.CreateAndListShow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hellohasan.sqlite_project.R;
import com.hellohasan.sqlite_project.StudentCrud.Student;

import java.util.List;

public class StudentListRecyclerViewAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private Context context;
    private List<Student> studentList;

    public StudentListRecyclerViewAdapter(Context context, List<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Student student = studentList.get(position);

        holder.nameTextView.setText(student.getName());
        holder.registrationNumTextVeiw.setText(String.valueOf(student.getRegistrationNumber()));
        holder.emailTextView.setText(student.getEmail());
        holder.phoneTextView.setText(student.getPhoneNumber());

        holder.crossButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Remove", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }
}
