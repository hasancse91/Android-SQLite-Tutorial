package com.hellohasan.sqlite_multiple_three_tables_crud.features.student_crud.student_list_show;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hellohasan.sqlite_multiple_three_tables_crud.R;
import com.hellohasan.sqlite_multiple_three_tables_crud.database.*;
import com.hellohasan.sqlite_multiple_three_tables_crud.features.student_crud.StudentCrudListener;
import com.hellohasan.sqlite_multiple_three_tables_crud.features.student_crud.student_create.StudentCreateDialogFragment;
import com.hellohasan.sqlite_multiple_three_tables_crud.features.student_crud.student_update.StudentUpdateDialogFragment;
import com.hellohasan.sqlite_multiple_three_tables_crud.model.Student;
import com.hellohasan.sqlite_multiple_three_tables_crud.util.Constants;

import java.util.List;

import static com.hellohasan.sqlite_multiple_three_tables_crud.util.Constants.*;

public class StudentListAdapter extends RecyclerView.Adapter<StudentViewHolder> {

    private Context context;
    private List<Student> studentList;
    private StudentCrudListener listener;

    StudentListAdapter(Context context, List<Student> studentList, StudentCrudListener listener) {
        this.context = context;
        this.studentList = studentList;
        this.listener = listener;
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
                StudentUpdateDialogFragment studentCreateDialogFragment = StudentUpdateDialogFragment.newInstance(student, "Update Student", new StudentCrudListener() {
                    @Override
                    public void onStudentListUpdate(boolean inUpdated) {
                        listener.onStudentListUpdate(inUpdated);
                    }
                });
                studentCreateDialogFragment.show(((StudentListActivity) context).getSupportFragmentManager(), Constants.CREATE_STUDENT);
            }
        });

        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog(student.getId());
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

    private void showConfirmationDialog(final int studentId) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Are you sure, You wanted to delete this student?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        QueryContract.StudentQuery query = new StudentQueryImplementation();
                        query.deleteStudent(studentId, new QueryResponse<Boolean>() {
                            @Override
                            public void onSuccess(Boolean data) {
                                if(data) {
                                    Toast.makeText(context, "Student deleted successfully", Toast.LENGTH_SHORT).show();
                                    listener.onStudentListUpdate(true);
                                }
                            }

                            @Override
                            public void onFailure(String message) {
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
