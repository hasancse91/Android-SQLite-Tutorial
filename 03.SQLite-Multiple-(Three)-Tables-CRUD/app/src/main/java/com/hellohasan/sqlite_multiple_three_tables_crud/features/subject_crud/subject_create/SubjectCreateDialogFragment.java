package com.hellohasan.sqlite_multiple_three_tables_crud.features.subject_crud.subject_create;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.hellohasan.sqlite_multiple_three_tables_crud.database.SubjectQueryImplementation;
import com.hellohasan.sqlite_multiple_three_tables_crud.features.student_crud.student_create.StudentCreateDialogFragment;
import com.hellohasan.sqlite_multiple_three_tables_crud.model.Student;
import com.hellohasan.sqlite_multiple_three_tables_crud.model.Subject;
import com.hellohasan.sqlite_multiple_three_tables_crud.util.Constants;

public class SubjectCreateDialogFragment extends DialogFragment {

    private EditText subjectNameEditText;
    private EditText subjectCodeEditText;
    private EditText subjectCreditEditText;
    private Button createButton;
    private Button cancelButton;

    private static SubjectCrudListener subjectCrudListener;

    public SubjectCreateDialogFragment() {
    }

    public static SubjectCreateDialogFragment newInstance(String title, SubjectCrudListener listener){
        subjectCrudListener = listener;
        SubjectCreateDialogFragment subjectCreateDialogFragment = new SubjectCreateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        subjectCreateDialogFragment.setArguments(args);

        subjectCreateDialogFragment.setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return subjectCreateDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subject_create_dialog, container, false);

        subjectNameEditText = view.findViewById(R.id.subjectNameEditText);
        subjectCodeEditText = view.findViewById(R.id.subjectCodeEditText);
        subjectCreditEditText = view.findViewById(R.id.subjectCreditEditText);
        createButton = view.findViewById(R.id.createButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        String title = getArguments().getString(Constants.TITLE);
        getDialog().setTitle(title);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjectName = subjectNameEditText.getText().toString();
                int subjectCode = Integer.parseInt(subjectCodeEditText.getText().toString());
                double subjectCredit = Double.parseDouble(subjectCreditEditText.getText().toString());

                final Subject subject = new Subject(-1, subjectName, subjectCode, subjectCredit);

                QueryContract.SubjectQuery query = new SubjectQueryImplementation();
                query.createSubject(subject, new QueryResponse<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        getDialog().dismiss();
                        subjectCrudListener.onSubjectListUpdate(true);
                        Toast.makeText(getContext(), "Subject created successfully", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(String message) {
                        subjectCrudListener.onSubjectListUpdate(false);
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
