package com.github.shyamking.deathapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DatabaseName = "Database.db";
    private static final int DatabaseVersion = 3;

    public SQLiteHelper(Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLContract.CrimeFavourites.CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQLContract.CrimeFavourites.DELETE_QUERY);
        onCreate(db);
    }
}
