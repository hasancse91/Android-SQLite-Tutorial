package com.hellohasan.sqlite_multiple_three_tables_crud.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.hellohasan.sqlite_multiple_three_tables_crud.model.Subject;
import com.hellohasan.sqlite_multiple_three_tables_crud.model.TakenSubject;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

import static com.hellohasan.sqlite_multiple_three_tables_crud.util.Constants.*;

public class TakenSubjectQueryImplementation implements QueryContract.TakenSubjectQuery {

    private DatabaseHelper databaseHelper = DatabaseHelper.getInstance();

    @Override
    public void createTakenSubject(int studentId, int subjectId, QueryResponse<Boolean> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(STUDENT_ID_FK, studentId);
        contentValues.put(SUBJECT_ID_FK, subjectId);

        try {
            long rowCount = sqLiteDatabase.insertOrThrow(TABLE_TAKEN_SUBJECT, null, contentValues);

            if (rowCount>0)
                response.onSuccess(true);
            else
                response.onFailure("Subject assign failed");

        } catch (SQLiteException e) {
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
        }
    }

    @Override
    public void readTakenSubject(int takenSubjectId, QueryResponse<TakenSubject> response) {

    }

    @Override
    public void readAllTakenSubjectByStudentId(int studentId, QueryResponse<List<Subject>> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        String QUERY = "SELECT _id, name, subject_code, credit FROM subject JOIN taken_subject ON subject._id = taken_subject.fk_subject_id WHERE taken_subject.fk_student_id = " + studentId;
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.rawQuery(QUERY, null);

            Logger.addLogAdapter(new AndroidLogAdapter());
            Logger.d(cursor.getColumnCount());

        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if (cursor!=null)
                cursor.close();
        }
    }

    @Override
    public void updateTakenSubject(TakenSubject takenSubject, QueryResponse<Boolean> response) {

    }

    @Override
    public void deleteTakenSubject(int studentId, int subjectId, QueryResponse<Boolean> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            long rowCount = sqLiteDatabase.delete(TABLE_TAKEN_SUBJECT,
                    STUDENT_ID_FK + " =?" + SUBJECT_ID_FK + " =?",
                    new String[]{String.valueOf(studentId), String.valueOf(subjectId)});

            if (rowCount>0)
                response.onSuccess(true);
            else
                response.onFailure("Assigned subject deletion failed");

        } catch (Exception e) {
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
        }
    }
}
