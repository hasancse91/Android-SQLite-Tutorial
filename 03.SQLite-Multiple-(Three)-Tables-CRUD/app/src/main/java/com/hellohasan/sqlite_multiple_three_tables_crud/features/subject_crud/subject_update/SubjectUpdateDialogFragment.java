package com.hellohasan.sqlite_multiple_three_tables_crud.features.subject_crud.subject_update;

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
import com.hellohasan.sqlite_multiple_three_tables_crud.features.subject_crud.SubjectCrudListener;
import com.hellohasan.sqlite_multiple_three_tables_crud.model.Subject;

import static com.hellohasan.sqlite_multiple_three_tables_crud.util.Constants.TITLE;

public class SubjectUpdateDialogFragment extends DialogFragment {

    private static SubjectCrudListener subjectCrudListener;

    private EditText subjectNameEditText;
    private EditText subjectCodeEditText;
    private EditText subjectCreditEditText;
    private Button updateButton;
    private Button cancelButton;

    private static Subject subject;

    public SubjectUpdateDialogFragment() {
        // Required empty public constructor
    }

    public static SubjectUpdateDialogFragment newInstance(Subject sub, String title, SubjectCrudListener listener){
        subject = sub;
        subjectCrudListener = listener;
        SubjectUpdateDialogFragment subjectUpdateDialogFragment = new SubjectUpdateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        subjectUpdateDialogFragment.setArguments(args);

        subjectUpdateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return subjectUpdateDialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subject_update_dialog, container, false);
        String title = getArguments().getString(TITLE);
        getDialog().setTitle(title);

        subjectNameEditText = view.findViewById(R.id.subjectNameEditText);
        subjectCodeEditText = view.findViewById(R.id.subjectCodeEditText);
        subjectCreditEditText = view.findViewById(R.id.subjectCreditEditText);
        updateButton = view.findViewById(R.id.updateButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        subjectNameEditText.setText(subject.getName());
        subjectCodeEditText.setText(String.valueOf(subject.getCode()));
        subjectCreditEditText.setText(String.valueOf(subject.getCredit()));

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjectName = subjectNameEditText.getText().toString();
                int subjectCode = Integer.parseInt(subjectCodeEditText.getText().toString());
                double subjectCredit = Double.parseDouble(subjectCreditEditText.getText().toString());

                subject.setName(subjectName);
                subject.setCode(subjectCode);
                subject.setCredit(subjectCredit);

                QueryContract.SubjectQuery subjectQuery = new SubjectQueryImplementation();
                subjectQuery.updateSubject(subject, new QueryResponse<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        getDialog().dismiss();
                        subjectCrudListener.onSubjectListUpdate(data);
                        Toast.makeText(getContext(), "Subject updated successfully", Toast.LENGTH_LONG).show();
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
