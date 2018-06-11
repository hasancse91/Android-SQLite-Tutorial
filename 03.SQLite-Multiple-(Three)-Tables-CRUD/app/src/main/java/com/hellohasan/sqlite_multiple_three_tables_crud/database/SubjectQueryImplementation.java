package com.hellohasan.sqlite_multiple_three_tables_crud.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.hellohasan.sqlite_multiple_three_tables_crud.model.Subject;

import java.util.ArrayList;
import java.util.List;

import static com.hellohasan.sqlite_multiple_three_tables_crud.util.Constants.SUBJECT_CODE;
import static com.hellohasan.sqlite_multiple_three_tables_crud.util.Constants.SUBJECT_CREDIT;
import static com.hellohasan.sqlite_multiple_three_tables_crud.util.Constants.SUBJECT_ID;
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
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        List<Subject> subjectList = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(TABLE_SUBJECT, null, null, null, null, null, null);

            if(cursor!=null && cursor.moveToFirst()){
                do {
                    Subject subject = getSubjectFromCursor(cursor);
                    subjectList.add(subject);
                } while (cursor.moveToNext());

                response.onSuccess(subjectList);
            } else
                response.onFailure("There are no subject in database");

        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if (cursor!=null)
                cursor.close();
        }
    }

    @Override
    public void updateSubject(Subject subject, QueryResponse<Boolean> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = getContentValuesFromSubject(subject);

        try {
            long rowCount = sqLiteDatabase.update(TABLE_SUBJECT, contentValues,
                    SUBJECT_ID + " =? ", new String[]{String.valueOf(subject.getId())});

            if(rowCount>0)
                response.onSuccess(true);
            else
                response.onFailure("No subject is updated at all");

        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
        }
    }

    @Override
    public void deleteSubject(int subjectId, QueryResponse<Boolean> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            long rowCount = sqLiteDatabase.delete(TABLE_SUBJECT,
                    SUBJECT_ID + " =? ", new String[]{String.valueOf(subjectId)});

            if(rowCount>0)
                response.onSuccess(true);
            else
                response.onFailure("No subject is deleted at all");

        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
        }
    }

    private Subject getSubjectFromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(SUBJECT_ID));
        String subjectName = cursor.getString(cursor.getColumnIndex(SUBJECT_NAME));
        int subjectCode = cursor.getInt(cursor.getColumnIndex(SUBJECT_CODE));
        double subjectCredit = cursor.getDouble(cursor.getColumnIndex(SUBJECT_CREDIT));

        return new Subject(id, subjectName, subjectCode, subjectCredit);
    }

    private ContentValues getContentValuesFromSubject(Subject subject) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(SUBJECT_NAME, subject.getName());
        contentValues.put(SUBJECT_CODE, subject.getCode());
        contentValues.put(SUBJECT_CREDIT, subject.getCredit());

        return contentValues;
    }
}
