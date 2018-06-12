package com.hellohasan.sqlite_multiple_three_tables_crud.database;

import com.hellohasan.sqlite_multiple_three_tables_crud.model.Student;
import com.hellohasan.sqlite_multiple_three_tables_crud.model.Subject;
import com.hellohasan.sqlite_multiple_three_tables_crud.model.TableRowCount;
import com.hellohasan.sqlite_multiple_three_tables_crud.model.TakenSubject;

import java.util.List;

public class QueryContract {

    public interface StudentQuery {
        void createStudent(Student student, QueryResponse<Boolean> response);
        void readStudent(int studentId, QueryResponse<Student> response);
        void readAllStudent(QueryResponse<List<Student>> response);
        void updateStudent(Student student, QueryResponse<Boolean> response);
        void deleteStudent(int studentId, QueryResponse<Boolean> response);
    }

    public interface SubjectQuery {
        void createSubject(Subject subject, QueryResponse<Boolean> response);
        void readSubject(int subjectId, QueryResponse<Subject> response);
        void readAllSubject(QueryResponse<List<Subject>> response);
        void updateSubject(Subject subject, QueryResponse<Boolean> response);
        void deleteSubject(int subjectId, QueryResponse<Boolean> response);
    }

    public interface TakenSubjectQuery {
        void createTakenSubject(int studentId, int subjectId, QueryResponse<Boolean> response);
        void readTakenSubject(int takenSubjectId, QueryResponse<TakenSubject> response);
        void readAllTakenSubjectByStudentId(int studentId, QueryResponse<List<Subject>> response);
        void updateTakenSubject(TakenSubject takenSubject, QueryResponse<Boolean> response);
        void deleteTakenSubject(int studentId, int subjectId, QueryResponse<Boolean> response);
    }

    public interface TableRowCountQuery {
        void getTableRowCount(QueryResponse<TableRowCount> response);
    }
}