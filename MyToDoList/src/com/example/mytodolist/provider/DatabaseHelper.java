package com.example.mytodolist.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mytodo.db";
    private static final int DATABASE_VERSION = 1;

    //Tạo bảng
    private static final String USER_TABLE_CREATE =
            String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s TEXT, %s TEXT, %s TEXT)",
                    MyToDo.Users.TABLE_NAME, MyToDo.Users._ID, MyToDo.Users.COLUMN_NAME_ID, MyToDo.Users.COLUMN_NAME_USERNAME,
                    MyToDo.Users.COLUMN_NAME_PASSWORD, MyToDo.Users.COLUMN_NAME_FULL_NAME);

    private static final String TASK_TABLE_CREATE =
            String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT,%s INTEGER,%s INTEGER, %s TEXT, %s TEXT, %s INTEGER, %s INTEGER, %s INTEGER)",
                    MyToDo.Tasks.TABLE_NAME, MyToDo.Tasks._ID, MyToDo.Tasks.COLUMN_NAME_ID, MyToDo.Tasks.COLUMN_NAME_USER_ID, MyToDo.Tasks.COLUMN_NAME_NAME,
                    MyToDo.Tasks.COLUMN_NAME_DESCRIPTION, MyToDo.Tasks.COLUMN_NAME_REMINDER_DATE, MyToDo.Tasks.COLUMN_NAME_CREATE_DATE,
                    MyToDo.Tasks.COLUMN_NAME_UPDATE_DATE);

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(USER_TABLE_CREATE);
        db.execSQL(TASK_TABLE_CREATE);
    }

    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + MyToDo.Users.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MyToDo.Tasks.TABLE_NAME);
        onCreate(db);
    }
}
