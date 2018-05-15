package com.hellohasan.sqlite_multiple_three_tables_crud.model;

public class TakenSubject {
    private int takenSubjectId;
    private int studentId;
    private int subjectId;

    public int getTakenSubjectId() {
        return takenSubjectId;
    }

    public void setTakenSubjectId(int takenSubjectId) {
        this.takenSubjectId = takenSubjectId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
}
