package com.hellohasan.sqlite_project;

public class Subject {
    private String name;
    private int code;
    private double credit;

    public Subject(String name, int code, double credit) {
        this.name = name;
        this.code = code;
        this.credit = credit;
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
