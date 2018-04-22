package com.hellohasan.sqlite_multiple_three_tables_crud.Util;

public class Constants {

    //column names of student table
    public static final String TABLE_STUDENT = "student";
    public static final String COLUMN_STUDENT_ID = "_id";
    public static final String COLUMN_STUDENT_NAME = "name";
    public static final String COLUMN_STUDENT_REGISTRATION = "registration_no";
    public static final String COLUMN_STUDENT_PHONE = "phone";
    public static final String COLUMN_STUDENT_EMAIL = "email";

    //column names of subject table
    public static final String TABLE_SUBJECT = "subject";
    public static final String COLUMN_SUBJECT_ID = "_id";
    public static final String COLUMN_SUBJECT_NAME = "name";
    public static final String COLUMN_SUBJECT_CODE = "subject_code";
    public static final String COLUMN_SUBJECT_CREDIT = "credit";

    //column names of taken_subject table
    public static final String TABLE_TAKEN_SUBJECT = "taken_subject";
    public static final String COLUMN_TAKEN_SUBJECT_ID = "_id";
    public static final String COLUMN_STUDENT_ID_FK = "fk_student_id";
    public static final String COLUMN_SUBJECT_ID_FK = "fk_subject_id";
    public static final String STUDENT_SUB_CONSTRAINT = "student_sub_unique";


    //others for general purpose key-value pair data
    public static final String TITLE = "title";
    public static final String CREATE_STUDENT = "create_student";
    public static final String UPDATE_STUDENT = "update_student";
    public static final String CREATE_SUBJECT = "create_subject";
    public static final String UPDATE_SUBJECT = "update_subject";
    public static final String STUDENT_REGISTRATION = "student_registration";
}