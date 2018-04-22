package com.hellohasan.sqlite_multiple_three_tables_crud.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.hellohasan.sqlite_multiple_three_tables_crud.Util.Constants.*;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "student-db";
    private static final int DATABASE_VERSION = 1;

    private static DatabaseHelper databaseHelper;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (databaseHelper == null)
            databaseHelper = new DatabaseHelper(context);

        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_STUDENT_TABLE = "CREATE TABLE " + TABLE_STUDENT + "("
                + COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_STUDENT_NAME + " TEXT NOT NULL, "
                + COLUMN_STUDENT_REGISTRATION + " INTEGER NOT NULL UNIQUE, "
                + COLUMN_STUDENT_PHONE + " TEXT, " //nullable
                + COLUMN_STUDENT_EMAIL + " TEXT " //nullable
                + ")";

        String CREATE_SUBJECT_TABLE = "CREATE TABLE " + TABLE_SUBJECT + "("
                + COLUMN_SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_SUBJECT_NAME + " TEXT NOT NULL, "
                + COLUMN_SUBJECT_CODE + " INTEGER NOT NULL, "
                + COLUMN_SUBJECT_CREDIT + " REAL" //nullable
                + ")";

        String CREATE_TAKEN_SUBJECT_TABLE = "CREATE TABLE " + TABLE_TAKEN_SUBJECT + "("
                + COLUMN_TAKEN_SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_STUDENT_ID_FK + " INTEGER NOT NULL, "
                + COLUMN_SUBJECT_ID_FK + " INTEGER NOT NULL, "
                + "FOREIGN KEY (" + COLUMN_STUDENT_ID_FK + ") REFERENCES " + TABLE_STUDENT + "(" + COLUMN_STUDENT_ID + ") ON UPDATE CASCADE ON DELETE CASCADE, "
                + "FOREIGN KEY (" + COLUMN_SUBJECT_ID_FK + ") REFERENCES " + TABLE_SUBJECT + "(" + COLUMN_SUBJECT_ID + ") ON UPDATE CASCADE ON DELETE CASCADE, "
                + "CONSTRAINT " + STUDENT_SUB_CONSTRAINT + " UNIQUE (" + COLUMN_STUDENT_ID_FK + "," + COLUMN_SUBJECT_ID_FK + ")"
                + ")";

        sqLiteDatabase.execSQL(CREATE_STUDENT_TABLE);
        sqLiteDatabase.execSQL(CREATE_SUBJECT_TABLE);
        sqLiteDatabase.execSQL(CREATE_TAKEN_SUBJECT_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TAKEN_SUBJECT);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        //enable foreign key constraints like ON UPDATE CASCADE, ON DELETE CASCADE
        db.execSQL("PRAGMA foreign_keys=ON;");
    }
}
