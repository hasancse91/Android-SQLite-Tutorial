package com.hellohasan.sqlite_multiple_three_tables_crud.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hellohasan.sqlite_multiple_three_tables_crud.util.MyApp;

import static com.hellohasan.sqlite_multiple_three_tables_crud.util.Constants.*;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "student-db";
    private static final int DATABASE_VERSION = 1;

    private static DatabaseHelper databaseHelper;

    private DatabaseHelper() {
        super(MyApp.context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance() {

        if (databaseHelper == null) {
            synchronized (DatabaseHelper.class){ //thread safe singleton
                if (databaseHelper == null)
                    databaseHelper = new DatabaseHelper();
            }
        }

        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_STUDENT_TABLE = "CREATE TABLE " + TABLE_STUDENT + "("
                + STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + STUDENT_NAME + " TEXT NOT NULL, "
                + STUDENT_REGISTRATION_NUM + " INTEGER NOT NULL UNIQUE, "
                + STUDENT_PHONE + " TEXT, " //nullable
                + STUDENT_EMAIL + " TEXT " //nullable
                + ")";

        String CREATE_SUBJECT_TABLE = "CREATE TABLE " + TABLE_SUBJECT + "("
                + SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SUBJECT_NAME + " TEXT NOT NULL, "
                + SUBJECT_CODE + " INTEGER NOT NULL, "
                + SUBJECT_CREDIT + " REAL" //nullable
                + ")";

        String CREATE_TAKEN_SUBJECT_TABLE = "CREATE TABLE " + TABLE_TAKEN_SUBJECT + "("
                + TAKEN_SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + STUDENT_ID_FK + " INTEGER NOT NULL, "
                + SUBJECT_ID_FK + " INTEGER NOT NULL, "
                + "FOREIGN KEY (" + STUDENT_ID_FK + ") REFERENCES " + TABLE_STUDENT + "(" + STUDENT_ID + ") ON UPDATE CASCADE ON DELETE CASCADE, "
                + "FOREIGN KEY (" + SUBJECT_ID_FK + ") REFERENCES " + TABLE_SUBJECT + "(" + SUBJECT_ID + ") ON UPDATE CASCADE ON DELETE CASCADE, "
                + "CONSTRAINT " + STUDENT_SUB_CONSTRAINT + " UNIQUE (" + STUDENT_ID_FK + "," + SUBJECT_ID_FK + ")"
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
