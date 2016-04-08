package com.easylibs.sqlite.example.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.easylibs.sqlite.EasySQLiteHelper;
import com.easylibs.sqlite.example.data.EmployeeTable;

/**
 * Created by sachin.gupta on 05-04-2016.
 */
public class DemoApplication extends Application implements EasySQLiteHelper {

    @Override
    public String getDbName() {
        return getPackageName();
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
