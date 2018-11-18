package com.example.alias.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDB extends SQLiteOpenHelper {
    private static final String DB_NAME= "store";
    private static final String TABLE_NAME = "table_name";
    private static final int DB_VERSION = 1;
    public myDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE if not exists "
                + "User"
                + " (UID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, password TEXT,uri TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        String CREATE_TABLE2 = "CREATE TABLE if not exists "
                + "Comment"
                + " (CID INTEGER PRIMARY KEY AUTOINCREMENT, comment TEXT, username TEXT, date TEXT, starnum INTEGER)";
        sqLiteDatabase.execSQL(CREATE_TABLE2);

        String CREATE_TABLE3 = "CREATE TABLE if not exists "
                + "Star"
                + " (SID INTEGER PRIMARY KEY AUTOINCREMENT, CID TEXT, username TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE3);
    }
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int ii) {

    }
}
