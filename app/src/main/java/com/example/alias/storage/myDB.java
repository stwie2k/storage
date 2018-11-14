package com.example.alias.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDB extends SQLiteOpenHelper {
    private static final String DB_NAME= "db_name";
    private static final String TABLE_NAME = "table_name";
    private static final int DB_VERSION = 1;
    public myDB(Context context) {
        super(context, "db_name.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE if not exists "
                + TABLE_NAME
                + " (_id INTEGER PRIMARY KEY, name TEXT, password TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int ii) {

    }
}
