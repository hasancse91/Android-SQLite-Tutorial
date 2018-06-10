package com.hellohasan.sqlite_multiple_three_tables_crud.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.hellohasan.sqlite_multiple_three_tables_crud.model.Subject;

import java.util.List;

import static com.hellohasan.sqlite_multiple_three_tables_crud.util.Constants.SUBJECT_CODE;
import static com.hellohasan.sqlite_multiple_three_tables_crud.util.Constants.SUBJECT_CREDIT;
import static com.hellohasan.sqlite_multiple_three_tables_crud.util.Constants.SUBJECT_NAME;
import static com.hellohasan.sqlite_multiple_three_tables_crud.util.Constants.TABLE_STUDENT;
import static com.hellohasan.sqlite_multiple_three_tables_crud.util.Constants.TABLE_SUBJECT;

public class SubjectQueryImplementation implements QueryContract.SubjectQuery {

    private DatabaseHelper databaseHelper = DatabaseHelper.getInstance();

    @Override
    public void createSubject(Subject subject, QueryResponse<Boolean> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SUBJECT_NAME, subject.getName());
        contentValues.put(SUBJECT_CODE, subject.getCode());
        contentValues.put(SUBJECT_CREDIT, subject.getCredit());

        try {
            long id = sqLiteDatabase.insertOrThrow(TABLE_SUBJECT, null, contentValues);
            if(id>0) {
                response.onSuccess(true);
            }
            else
                response.onFailure("Failed to create student. Unknown Reason!");
        } catch (SQLiteException e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
        }
    }

    @Override
    public void readSubject(int subjectId, QueryResponse<Subject> response) {

    }

    @Override
    public void readAllSubject(QueryResponse<List<Subject>> response) {

    }

    @Override
    public void updateSubject(Subject subject, QueryResponse<Boolean> response) {

    }

    @Override
    public void deleteSubject(int subjectId, QueryResponse<Boolean> response) {

    }
}
