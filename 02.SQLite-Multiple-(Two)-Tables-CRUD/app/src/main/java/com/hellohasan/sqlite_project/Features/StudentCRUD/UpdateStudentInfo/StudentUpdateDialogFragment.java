package com.hellohasan.sqlite_project.Features.StudentCRUD.UpdateStudentInfo;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hellohasan.sqlite_project.Database.DatabaseQueryClass;
import com.hellohasan.sqlite_project.Features.StudentCRUD.CreateStudent.Student;
import com.hellohasan.sqlite_project.R;
import com.hellohasan.sqlite_project.Util.Config;


public class StudentUpdateDialogFragment extends DialogFragment {

    private static long studentRegNo;
    private static int studentItemPosition;
    private static StudentUpdateListener studentUpdateListener;

    private Student mStudent;

    private EditText nameEditText;
    private EditText registrationEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private Button updateButton;
    private Button cancelButton;

    private String nameString = "";
    private long registrationNumber = -1;
    private String phoneString = "";
    private String emailString = "";

    private DatabaseQueryClass databaseQueryClass;

    public StudentUpdateDialogFragment() {
        // Required empty public constructor
    }

    public static StudentUpdateDialogFragment newInstance(long registrationNumber, int position, StudentUpdateListener listener){
        studentRegNo = registrationNumber;
        studentItemPosition = position;
        studentUpdateListener = listener;
        StudentUpdateDialogFragment studentUpdateDialogFragment = new StudentUpdateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", "Update student information");
        studentUpdateDialogFragment.setArguments(args);

        studentUpdateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return studentUpdateDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_update_dialog, container, false);

        databaseQueryClass = new DatabaseQueryClass(getContext());

        nameEditText = view.findViewById(R.id.studentNameEditText);
        registrationEditText = view.findViewById(R.id.registrationEditText);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        updateButton = view.findViewById(R.id.updateStudentInfoButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        mStudent = databaseQueryClass.getStudentByRegNum(studentRegNo);

        if(mStudent!=null){
            nameEditText.setText(mStudent.getName());
            registrationEditText.setText(String.valueOf(mStudent.getRegistrationNumber()));
            phoneEditText.setText(mStudent.getPhoneNumber());
            emailEditText.setText(mStudent.getEmail());

            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nameString = nameEditText.getText().toString();
                    registrationNumber = Integer.parseInt(registrationEditText.getText().toString());
                    phoneString = phoneEditText.getText().toString();
                    emailString = emailEditText.getText().toString();

                    mStudent.setName(nameString);
                    mStudent.setRegistrationNumber(registrationNumber);
                    mStudent.setPhoneNumber(phoneString);
                    mStudent.setEmail(emailString);

                    long id = databaseQueryClass.updateStudentInfo(mStudent);

                    if(id>0){
                        studentUpdateListener.onStudentInfoUpdated(mStudent, studentItemPosition);
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

        }

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
