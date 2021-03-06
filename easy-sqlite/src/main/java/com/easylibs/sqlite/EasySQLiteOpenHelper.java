package com.easylibs.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Helper class to execute create table queries when installed.
 *
 * @author sachin.gupta
 */
public class EasySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = EasySQLiteOpenHelper.class.getSimpleName();

    private static HashMap<String, EasySQLiteOpenHelper> mEasySQLiteOpenHelpersMap;

    /**
     * @param pContext
     * @return
     */
    public static synchronized EasySQLiteOpenHelper getInstance(Context pContext, EasySQLiteHelper pEasySQLiteHelper) {
        if (mEasySQLiteOpenHelpersMap == null) {
            mEasySQLiteOpenHelpersMap = new HashMap<>();
        }
        EasySQLiteOpenHelper instance = mEasySQLiteOpenHelpersMap.get(pEasySQLiteHelper.getDbName());
        if (instance == null) {
            instance = new EasySQLiteOpenHelper(pContext, pEasySQLiteHelper);
            mEasySQLiteOpenHelpersMap.put(pEasySQLiteHelper.getDbName(), instance);
        }
        return instance;
    }

    private EasySQLiteHelper mEasySQLiteHelper;

    /**
     * @param pContext
     */
    private EasySQLiteOpenHelper(Context pContext, EasySQLiteHelper pEasySQLiteHelper) {
        super(pContext, pEasySQLiteHelper.getDbName(), null, pEasySQLiteHelper.getDbVersion());
        mEasySQLiteHelper = pEasySQLiteHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) {
            Log.v(LOG_TAG, "onCreate start");
        }
        if (mEasySQLiteHelper != null) {
            String[] createTableQueries = mEasySQLiteHelper.getCreateTableQueries();
            if (createTableQueries != null) {
                for (String aQuery : createTableQueries) {
                    db.execSQL(aQuery);
                }
            }
        }
        if (BuildConfig.DEBUG) {
            Log.v(LOG_TAG, "onCreate return");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (BuildConfig.DEBUG) {
            Log.i(LOG_TAG, "DB upgrade. New: " + newVersion + ", Old: " + oldVersion);
        }
        mEasySQLiteHelper.onUpgrade(db, oldVersion, newVersion);
    }
}