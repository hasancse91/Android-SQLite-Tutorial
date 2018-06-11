package com.hellohasan.sqlite_multiple_three_tables_crud.features.subject_crud.subject_list_show;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hellohasan.sqlite_multiple_three_tables_crud.R;
import com.hellohasan.sqlite_multiple_three_tables_crud.database.*;
import com.hellohasan.sqlite_multiple_three_tables_crud.database.SubjectQueryImplementation;
import com.hellohasan.sqlite_multiple_three_tables_crud.features.subject_crud.SubjectCrudListener;
import com.hellohasan.sqlite_multiple_three_tables_crud.features.subject_crud.subject_update.*;
import com.hellohasan.sqlite_multiple_three_tables_crud.model.Subject;

import java.util.List;

import static com.hellohasan.sqlite_multiple_three_tables_crud.util.Constants.*;

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectViewHolder> {

    private Context context;
    private List<Subject> subjectList;
    private SubjectCrudListener listener;

    public SubjectListAdapter(Context context, List<Subject> subjectList, SubjectCrudListener listener) {
        this.context = context;
        this.subjectList = subjectList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_subject_card_view, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        final Subject subject = subjectList.get(position);

        holder.subjectNameTextView.setText(subject.getName());
        holder.courseCodeTextView.setText(context.getString(R.string.course_code, subject.getCode()));
        holder.creditTextView.setText(context.getString(R.string.course_credit, subject.getCredit()));

        holder.editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubjectUpdateDialogFragment dialogFragment = SubjectUpdateDialogFragment.newInstance(subject, "Update Subject", new SubjectCrudListener() {
                    @Override
                    public void onSubjectListUpdate(boolean isUpdate) {
                        listener.onSubjectListUpdate(isUpdate);
                    }
                });
                dialogFragment.show(((SubjectListActivity) context).getSupportFragmentManager(), UPDATE_SUBJECT);
            }
        });

        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog(subject.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    private void showConfirmationDialog(final int subjectId) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Are you sure, You wanted to delete this subject?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        QueryContract.SubjectQuery query = new SubjectQueryImplementation();
                        query.deleteSubject(subjectId, new QueryResponse<Boolean>() {
                            @Override
                            public void onSuccess(Boolean data) {
                                listener.onSubjectListUpdate(data);
                                Toast.makeText(context, "Subject is deleted successfully", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(String message) {
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
