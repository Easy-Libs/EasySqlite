package com.easylibs.sqlite.example.database;

import android.database.sqlite.SQLiteDatabase;

import com.easylibs.sqlite.EasySQLiteHelper;

/**
 * Created by sachin.gupta on 05-04-2016.
 */
public class DbTwoEasySQLiteHelper implements EasySQLiteHelper {

    private static DbTwoEasySQLiteHelper sInstance;

    /**
     * NO synchronization, as even singleton is not strictly required
     *
     * @return
     */
    public static DbTwoEasySQLiteHelper getInstance() {
        if (sInstance == null) {
            sInstance = new DbTwoEasySQLiteHelper();
        }
        return sInstance;
    }

    /**
     * private constructor for singleton
     */
    private DbTwoEasySQLiteHelper() {
        //  nothing to do here
    }

    @Override
    public String getDbName() {
        return "example_app_db_two";
    }

    @Override
    public int getDbVersion() {
        return 1;
    }

    @Override
    public String[] getCreateTableQueries() {
        return new String[]{DepartmentTable.SQL_CREATE_TABLE};
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO
    }
}
