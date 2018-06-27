package com.hellohasan.sqlite_multiple_three_tables_crud.database;

public interface QueryResponse<T> {
    void onSuccess(T data);
    void onFailure(String message);
}