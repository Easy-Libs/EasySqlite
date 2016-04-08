package com.easylibs.sqlite;

import android.database.sqlite.SQLiteDatabase;

/**
 * This class holds some application-global instances.
 */
public interface EasySQLiteHelper {

    String getDbName();

    int getDbVersion();

    String[] getCreateTableQueries();

    void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
}
