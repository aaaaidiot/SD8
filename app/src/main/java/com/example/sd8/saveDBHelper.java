package com.example.sd8;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class saveDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "save.db";
    private static final int DATABASE_VERSION = 3;

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + saveDB.FeedEntry.TABLE_NAME + " (" +
                    saveDB.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    saveDB.FeedEntry.COLUMN_NAME_WORD + TEXT_TYPE + COMMA_SEP +
                    saveDB.FeedEntry.COLUMN_NAME_TENSU + TEXT_TYPE +
        " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + saveDB.FeedEntry.TABLE_NAME;

    saveDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        Log.d("debug", "onCreate(SQLiteDatabase db)");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}


