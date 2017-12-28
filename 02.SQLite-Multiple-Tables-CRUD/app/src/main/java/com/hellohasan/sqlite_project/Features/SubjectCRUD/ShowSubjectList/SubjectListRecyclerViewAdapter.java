package com.hellohasan.sqlite_project.Features.SubjectCRUD.ShowSubjectList;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hellohasan.sqlite_project.Database.DatabaseQueryClass;
import com.hellohasan.sqlite_project.Features.SubjectCRUD.CreateSubject.Subject;
import com.hellohasan.sqlite_project.Features.SubjectCRUD.UpdateSubjectInfo.SubjectUpdateDialogFragment;
import com.hellohasan.sqlite_project.Features.SubjectCRUD.UpdateSubjectInfo.SubjectUpdateListener;
import com.hellohasan.sqlite_project.R;
import com.hellohasan.sqlite_project.Util.Config;

import java.util.List;

public class SubjectListRecyclerViewAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private Context context;
    private List<Subject> subjectList;

    public SubjectListRecyclerViewAdapter(Context context, List<Subject> subjectList) {
        this.context = context;
        this.subjectList = subjectList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_subject, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final int listPosition = position;
        final Subject subject = subjectList.get(position);

        holder.subjectNameTextView.setText(subject.getName());
        holder.subjectCodeTextView.setText(String.valueOf(subject.getCode()));
        holder.subjectCreditTextView.setText(String.valueOf(subject.getCredit()));

        holder.crossButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure, You wanted to delete this subject?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                deleteSubject(subject);
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
        });

        holder.editButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editSubject(subject.getId(), listPosition);
            }
        });
    }

    private void editSubject(long subjectId, int listPosition){
        SubjectUpdateDialogFragment subjectUpdateDialogFragment = SubjectUpdateDialogFragment.newInstance(subjectId, listPosition, new SubjectUpdateListener() {
            @Override
            public void onSubjectInfoUpdate(Subject subject, int position) {
                subjectList.set(position, subject);
                notifyDataSetChanged();
            }
        });
        subjectUpdateDialogFragment.show(((SubjectListActivity) context).getSupportFragmentManager(), Config.UPDATE_SUBJECT);
    }

    private void deleteSubject(Subject subject) {
        DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(context);
        boolean isDeleted = databaseQueryClass.deleteSubjectById(subject.getId());

        if(isDeleted) {
            subjectList.remove(subject);
            notifyDataSetChanged();
            ((SubjectListActivity) context).viewVisibility();
        } else
            Toast.makeText(context, "Cannot delete!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }
}
