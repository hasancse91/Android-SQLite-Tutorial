package com.hellohasan.sqlite_multiple_three_tables_crud.database;

import com.hellohasan.sqlite_multiple_three_tables_crud.model.Subject;

import java.util.List;

public class SubjectQueryImplementation implements QueryContract.SubjectQuery {

    @Override
    public void createSubject(Subject subject, QueryResponse<Integer> response) {

    }

    @Override
    public void readSubject(int subjectId, QueryResponse<Subject> response) {

    }

    @Override
    public void readAllSubject(QueryResponse<List<Subject>> response) {

    }

    @Override
    public void updateSubject(Subject subject, QueryResponse<Boolean> response) {

    }

    @Override
    public void deleteSubject(int subjectId, QueryResponse<Boolean> response) {

    }
}
