package com.hellohasan.sqlite_multiple_three_tables_crud.database;

import com.hellohasan.sqlite_multiple_three_tables_crud.model.Student;
import com.hellohasan.sqlite_multiple_three_tables_crud.model.Subject;
import com.hellohasan.sqlite_multiple_three_tables_crud.model.TakenSubject;

public class QueryContract {

    public interface StudentQuery {
        void createStudent(Student student);

        void readStudent();

        void readAllStudent();

        void updateStudent();

        void deleteStudent();
    }

    public interface SubjectQuery {
        void createSubject(Subject subject);

        void readSubject();

        void readAllSubject();

        void updateSubject();

        void deleteSubject();
    }

    public interface TakenSubjectQuery {
        void createTakenSubject(TakenSubject takenSubject);

        void readTakenSubject();

        void readAllTakenSubject();

        void updateTakenSubject();

        void deleteTakenSubject();
    }

}
