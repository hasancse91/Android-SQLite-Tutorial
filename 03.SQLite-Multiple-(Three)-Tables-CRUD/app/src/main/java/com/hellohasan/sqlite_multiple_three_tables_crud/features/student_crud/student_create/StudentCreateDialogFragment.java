package com.hellohasan.sqlite_multiple_three_tables_crud.features.student_crud.student_create;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hellohasan.sqlite_multiple_three_tables_crud.R;
import com.hellohasan.sqlite_multiple_three_tables_crud.database.QueryContract;
import com.hellohasan.sqlite_multiple_three_tables_crud.database.QueryResponse;
import com.hellohasan.sqlite_multiple_three_tables_crud.database.StudentQueryImplementation;
import com.hellohasan.sqlite_multiple_three_tables_crud.model.Student;

import static com.hellohasan.sqlite_multiple_three_tables_crud.util.Constants.*;


public class StudentCreateDialogFragment extends DialogFragment {

    private static StudentCrudListener studentCrudListener;

    private EditText nameEditText;
    private EditText registrationEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private Button createButton;
    private Button cancelButton;

    private String nameString = "";
    private long registrationNumber = -1;
    private String phoneString = "";
    private String emailString = "";

    public StudentCreateDialogFragment() {
        // Required empty public constructor
    }

    public static StudentCreateDialogFragment newInstance(String title, StudentCrudListener listener){
        studentCrudListener = listener;
        StudentCreateDialogFragment studentCreateDialogFragment = new StudentCreateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        studentCreateDialogFragment.setArguments(args);

        studentCreateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return studentCreateDialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_create_dialog, container, false);

        nameEditText = view.findViewById(R.id.studentNameEditText);
        registrationEditText = view.findViewById(R.id.registrationEditText);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        createButton = view.findViewById(R.id.createButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        String title = getArguments().getString(TITLE);
        getDialog().setTitle(title);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameString = nameEditText.getText().toString();
                registrationNumber = Integer.parseInt(registrationEditText.getText().toString());
                phoneString = phoneEditText.getText().toString();
                emailString = emailEditText.getText().toString();

                final Student student = new Student(-1, nameString, registrationNumber, phoneString, emailString);

                QueryContract.StudentQuery studentQuery = new StudentQueryImplementation();
                studentQuery.createStudent(student, new QueryResponse<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        getDialog().dismiss();
                        studentCrudListener.onStudentListUpdate(data);
                        Toast.makeText(getContext(), "Student created successfully", Toast.LENGTH_LONG).show();
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
