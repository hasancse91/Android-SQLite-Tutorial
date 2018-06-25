package com.hellohasan.sqlite_multiple_three_tables_crud.model;

public class TakenSubject extends Subject{

    private boolean isTaken;

    public TakenSubject(int id, String name, int code, double credit, boolean isTaken) {
        super(id, name, code, credit);
        this.isTaken = isTaken;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }
}
