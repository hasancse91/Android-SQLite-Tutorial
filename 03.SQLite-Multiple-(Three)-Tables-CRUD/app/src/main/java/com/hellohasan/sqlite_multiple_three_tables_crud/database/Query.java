package com.hellohasan.sqlite_multiple_three_tables_crud.database;

public class Query {

    public interface Student {
        void createStudent();

        void readStudent();

        void readAllStudent();

        void updateStudent();

        void deleteStudent();
    }

    public interface Subject {
        void createSubject();

        void readSubject();

        void readAllSubject();

        void updateSubject();

        void deleteSubject();
    }

    public interface TakenSubject {
        void createTakenSubject();

        void readTakenSubject();

        void readAllTakenSubject();

        void updateTakenSubject();

        void deleteTakenSubject();
    }

}
