package com.easylibs.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * base class with common DB methods to be used as super class for all tables
 *
 * @author sachin.gupta
 */
public abstract class BaseTable<T extends IModel> {

    protected static final String CN_ROW_ID = "_ID";

    protected SQLiteDatabase mWritableDatabase;
    protected String mTableName;

    /**
     * Get the global instance of the SQLiteDatabase.
     *
     * @param pContext
     * @param pEasySQLiteHelper
     * @param pTableName
     */
    public BaseTable(Context pContext, EasySQLiteHelper pEasySQLiteHelper, String pTableName) {
        EasySQLiteOpenHelper easySQLiteOpenHelper = EasySQLiteOpenHelper.getInstance(pContext, pEasySQLiteHelper);
        mWritableDatabase = easySQLiteOpenHelper.getWritableDatabase();
        mTableName = pTableName;
    }

    /**
     * @param pModel
     * @return the rowId of newly inserted row, -1 in case of any error
     */
    public final long insertData(T pModel) {
        try {
            return mWritableDatabase.insert(mTableName, null, getContentValues(pModel, null));
        } catch (Exception e) {
            Log.e(mTableName, "insertData()", e);
            return -1;
        }
    }

    /**
     * inserts or updates data by primary key
     *
     * @param pModel
     * @return
     */
    public final boolean insertOrUpdate(T pModel) {
        T existingModel = getMatchingData(pModel);
        if (existingModel == null) {
            return insertData(pModel) > 0;
        } else {
            return updateData(pModel, existingModel) > 0;
        }
    }

    /**
     * delete data by whereClause and whereArgs
     *
     * @param pWhereClause
     * @param pWhereArgs
     * @return count of deleted rows
     */
    protected final int deleteData(String pWhereClause, String[] pWhereArgs) {
        try {
            return mWritableDatabase.delete(mTableName, pWhereClause, pWhereArgs);
        } catch (Exception e) {
            Log.e(mTableName, "deleteData()", e);
            return 0;
        }
    }

    /**
     * delete data by primary key
     *
     * @param pPrimaryKey
     * @return true if one or more rows are deleted
     */
    public final boolean deleteData(long pPrimaryKey) {
        String whereClause = CN_ROW_ID + " = ?";
        String[] whereArgs = {"" + pPrimaryKey};
        return deleteData(whereClause, whereArgs) > 0;
    }

    /**
     * update data by whereClause and whereArgs
     *
     * @param contentValues
     * @param whereClause
     * @param whereArgs
     * @return count of affected rows
     */
    protected final int updateData(ContentValues contentValues, String whereClause, String[] whereArgs) {
        try {
            return mWritableDatabase.update(mTableName, contentValues, whereClause, whereArgs);
        } catch (Exception e) {
            Log.e(mTableName, "updateData()", e);
            return 0;
        }
    }

    /**
     * update data by primary key
     *
     * @param pUpdatedModel
     * @param pExistingModel
     * @return count of affected rows
     */
    public final int updateData(T pUpdatedModel, T pExistingModel) {
        String whereClause = CN_ROW_ID + " = ?";
        String[] whereArgs = {"" + pExistingModel.getRowId()};
        return updateData(getContentValues(pUpdatedModel, pExistingModel), whereClause, whereArgs);
    }

    /**
     * @return array list of all data in table
     */
    public final ArrayList<T> getAllData() {
        return getAllData(null, null);
    }

    /**
     * @return the number of rows deleted
     */
    public final int deleteAll() {
        return deleteData("1", null);
    }

    /**
     * @return count of total rows in table, -1 in case of any exception.
     */
    public final int getRowsCount() {
        String columnName = "rowsCount";
        String query = "select count(*) as " + columnName + "  from " + mTableName;
        int rowsCount = -1;
        Cursor cursor = null;
        try {
            cursor = mWritableDatabase.rawQuery(query, null);
            if (cursor.moveToNext()) {
                rowsCount = cursor.getInt(0);
            }
        } catch (Exception e) {
            Log.e(mTableName, "getRowsCount()", e);
        } finally {
            closeCursor(cursor);
        }
        return rowsCount;
    }

    /**
     * Closes the pCursor.
     *
     * @param pCursor
     */
    protected final void closeCursor(Cursor pCursor) {
        if (pCursor != null && !pCursor.isClosed()) {
            pCursor.close();
        }
    }

    /**
     * Helper method to create content value from pNewOrUpdatedModel
     *
     * @param pNewOrUpdatedModel
     * @param pExistingModel
     * @return
     */
    protected abstract ContentValues getContentValues(T pNewOrUpdatedModel, T pExistingModel);

    /**
     * @param pSelection
     * @param pSelectionArgs
     * @return array list of data selected from table
     */
    protected abstract ArrayList<T> getAllData(String pSelection, String[] pSelectionArgs);

    /**
     * @param pModel
     * @return
     */
    protected T getMatchingData(T pModel) {
        throw new UnsupportedOperationException("Operation not implemented yet.");
    }
}
