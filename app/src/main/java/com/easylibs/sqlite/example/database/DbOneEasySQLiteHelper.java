package com.easylibs.sqlite.example.database;

import android.database.sqlite.SQLiteDatabase;

import com.easylibs.sqlite.EasySQLiteHelper;

/**
 * Created by sachin.gupta on 05-04-2016.
 */
public class DbOneEasySQLiteHelper implements EasySQLiteHelper {

    private static DbOneEasySQLiteHelper sInstance;

    /**
     * NO synchronization, as even singleton is not strictly required
     *
     * @return
     */
    public static DbOneEasySQLiteHelper getInstance() {
        if (sInstance == null) {
            sInstance = new DbOneEasySQLiteHelper();
        }
        return sInstance;
    }

    /**
     * private constructor for singleton
     */
    private DbOneEasySQLiteHelper() {
        //  nothing to do here
    }

    @Override
    public String getDbName() {
        return "example_app_db";
    }

    @Override
    public int getDbVersion() {
        return 1;
    }

    @Override
    public String[] getCreateTableQueries() {
        return new String[]{EmployeeTable.CREATE_TABLE_EMPLOYEE};
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO
    }
}
