package com.hellohasan.sqlite_multiple_three_tables_crud.util;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {

    public Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

}
