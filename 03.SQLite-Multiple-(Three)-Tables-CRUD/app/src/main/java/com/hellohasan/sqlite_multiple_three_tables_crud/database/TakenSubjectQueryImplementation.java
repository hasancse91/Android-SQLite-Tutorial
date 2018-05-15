package com.hellohasan.sqlite_multiple_three_tables_crud.database;

import com.hellohasan.sqlite_multiple_three_tables_crud.model.TakenSubject;

import java.util.List;

public class TakenSubjectQueryImplementation implements QueryContract.TakenSubjectQuery {

    @Override
    public void createTakenSubject(TakenSubject takenSubject, QueryResponse<Integer> response) {

    }

    @Override
    public void readTakenSubject(int takenSubjectId, QueryResponse<TakenSubject> response) {

    }

    @Override
    public void readAllTakenSubjectByStudentId(int studentId, QueryResponse<List<TakenSubject>> response) {

    }

    @Override
    public void updateTakenSubject(TakenSubject takenSubject, QueryResponse<Boolean> response) {

    }

    @Override
    public void deleteTakenSubject(int takenSubjectId, QueryResponse<Boolean> response) {

    }
}
