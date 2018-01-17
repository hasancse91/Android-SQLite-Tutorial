package com.hellohasan.sqlite_project.Features.SubjectCRUD.CreateSubject;

public class Subject {
    private long id;
    private String name;
    private int code;
    private double credit;

    public Subject(long id, String name, int code, double credit) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.credit = credit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }
}