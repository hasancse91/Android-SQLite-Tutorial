package com.hellohasan.sqlite_project;

public class Config {
    public static final String TABLE_STUDENT = "student";
    public static final String COLUMN_STUDENT_ID = "id";
    public static final String COLUMN_STUDENT_NAME = "name";
    public static final String COLUMN_STUDENT_REGISTRATION = "registration_no";
    public static final String COLUMN_STUDENT_PHONE = "phone";
    public static final String COLUMN_STUDENT_EMAIL = "email";

    public static final String TABLE_SUBJECT = "subject";
    public static final String COLUMN_SUBJECT_ID = "id";
    public static final String COLUMN_REGISTRATION_NUMBER = "registration_no";
    public static final String COLUMN_SUBJECT_NAME = "name";
    public static final String COLUMN_SUBJECT_CODE = "subject_code";
    public static final String COLUMN_SUBJECT_CREDIT = "credit";
    public static final String STUDENT_SUB_CONSTRAINT = "student_sub_unique";
}
