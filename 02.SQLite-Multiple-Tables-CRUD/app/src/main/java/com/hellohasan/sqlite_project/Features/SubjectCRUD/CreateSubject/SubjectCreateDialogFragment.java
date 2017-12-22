package com.hellohasan.sqlite_project.Features.SubjectCRUD.CreateSubject;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hellohasan.sqlite_project.Database.DatabaseQueryClass;
import com.hellohasan.sqlite_project.R;


public class SubjectCreateDialogFragment extends DialogFragment {

    private static long studentRegistrationNumber;
    private static SubjectCreateListener subjectCreateListener;

    private EditText subjectNameEditText;
    private EditText subjectCodeEditText;
    private EditText subjectCreditEditText;
    private Button createButton;
    private Button cancelButton;

    public SubjectCreateDialogFragment() {
        // Required empty public constructor
    }

    public static SubjectCreateDialogFragment newInstance(long studentRegNo, SubjectCreateListener listener){
        studentRegistrationNumber = studentRegNo;
        subjectCreateListener = listener;

        SubjectCreateDialogFragment subjectCreateDialogFragment = new SubjectCreateDialogFragment();

        subjectCreateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return subjectCreateDialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subject_create_dialog, container, false);
        getDialog().setTitle(getResources().getString(R.string.add_new_subject));

        subjectNameEditText = view.findViewById(R.id.subjectNameEditText);
        subjectCodeEditText = view.findViewById(R.id.subjectCodeEditText);
        subjectCreditEditText = view.findViewById(R.id.subjectCreditEditText);
        createButton = view.findViewById(R.id.createButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjectName = subjectNameEditText.getText().toString();
                int subjectCode = Integer.parseInt(subjectCodeEditText.getText().toString());
                double subjectCredit = Double.parseDouble(subjectCreditEditText.getText().toString());

                Subject subject = new Subject(-1, subjectName, subjectCode, subjectCredit);

                DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(getContext());

                long id = databaseQueryClass.insertSubject(subject, studentRegistrationNumber);

                if(id>0){
                    subject.setId(id);
                    subjectCreateListener.onSubjectCreated(subject);
                    getDialog().dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            //noinspection ConstantConditions
            dialog.getWindow().setLayout(width, height);
        }
    }

}
