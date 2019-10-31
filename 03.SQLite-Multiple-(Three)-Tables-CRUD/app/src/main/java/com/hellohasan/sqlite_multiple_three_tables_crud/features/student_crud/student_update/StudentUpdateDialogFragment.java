package com.hellohasan.sqlite_multiple_three_tables_crud.features.student_crud.student_update;

import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hellohasan.sqlite_multiple_three_tables_crud.R;
import com.hellohasan.sqlite_multiple_three_tables_crud.database.*;
import com.hellohasan.sqlite_multiple_three_tables_crud.features.student_crud.StudentCrudListener;
import com.hellohasan.sqlite_multiple_three_tables_crud.model.Student;

import static com.hellohasan.sqlite_multiple_three_tables_crud.util.Constants.TITLE;

public class StudentUpdateDialogFragment extends DialogFragment {

    private static StudentCrudListener studentCrudListener;

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

    private static Student student;

    public StudentUpdateDialogFragment() {
        // Required empty public constructor
    }

    public static StudentUpdateDialogFragment newInstance(Student std, String title, StudentCrudListener listener){
        student = std;
        studentCrudListener = listener;
        StudentUpdateDialogFragment studentUpdateDialogFragment = new StudentUpdateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        studentUpdateDialogFragment.setArguments(args);

        studentUpdateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return studentUpdateDialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_update_dialog, container, false);
        String title = getArguments().getString(TITLE);
        getDialog().setTitle(title);

        nameEditText = view.findViewById(R.id.studentNameEditText);
        registrationEditText = view.findViewById(R.id.registrationEditText);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        updateButton = view.findViewById(R.id.updateButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        nameEditText.setText(student.getName());
        registrationEditText.setText(String.valueOf(student.getRegistrationNumber()));
        phoneEditText.setText(student.getPhone());
        emailEditText.setText(student.getEmail());

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameString = nameEditText.getText().toString();
                registrationNumber = Integer.parseInt(registrationEditText.getText().toString());
                phoneString = phoneEditText.getText().toString();
                emailString = emailEditText.getText().toString();

                student.setName(nameString);
                student.setRegistrationNumber(registrationNumber);
                student.setPhone(phoneString);
                student.setEmail(emailString);

                QueryContract.StudentQuery studentQuery = new StudentQueryImplementation();
                studentQuery.updateStudent(student, new QueryResponse<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        getDialog().dismiss();
                        studentCrudListener.onStudentListUpdate(data);
                        Toast.makeText(getContext(), "Student updated successfully", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(String message) {
                        studentCrudListener.onStudentListUpdate(false);
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    }
                });

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
