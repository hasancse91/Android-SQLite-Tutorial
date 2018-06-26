package com.hellohasan.sqlite_multiple_three_tables_crud.util;

public class Constants {

    //column names of student table
    public static final String TABLE_STUDENT = "student";
    public static final String STUDENT_ID = "_id";
    public static final String STUDENT_NAME = "name";
    public static final String STUDENT_REGISTRATION_NUM = "registration_no";
    public static final String STUDENT_PHONE = "phone";
    public static final String STUDENT_EMAIL = "email";

    //column names of subject table
    public static final String TABLE_SUBJECT = "subject";
    public static final String SUBJECT_ID = "_id";
    public static final String SUBJECT_NAME = "name";
    public static final String SUBJECT_CODE = "code";
    public static final String SUBJECT_CREDIT = "credit";

    //column names of student_subject pivot table
    public static final String TABLE_STUDENT_SUBJECT = "student_subject";
    public static final String STUDENT_ID_FK = "student_id";
    public static final String SUBJECT_ID_FK = "subject_id";
    public static final String STUDENT_SUB_CONSTRAINT = "student_sub_unique";


    //others for general purpose key-value pair data
    public static final String TITLE = "title";
    public static final String CREATE_STUDENT = "create_student";
    public static final String UPDATE_STUDENT = "update_student";
    public static final String CREATE_SUBJECT = "create_subject";
    public static final String UPDATE_SUBJECT = "update_subject";
    public static final String STUDENT_REGISTRATION = "student_registration";
}