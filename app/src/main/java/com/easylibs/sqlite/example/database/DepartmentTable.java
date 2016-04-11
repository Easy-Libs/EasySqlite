package com.easylibs.sqlite.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.easylibs.sqlite.BaseTable;
import com.easylibs.sqlite.example.model.DepartmentModel;

import java.util.ArrayList;

/**
 * Created by sachin.gupta on 05-04-2016.
 */
public class DepartmentTable extends BaseTable<DepartmentModel> {

    private static final String TABLE_NAME_DEPARTMENTS = "department_table";

    public static final String SQL_CREATE_TABLE;

    private static final String CN_DEPT_ID = "dept_id";
    private static final String CN_NAME = "name";

    private static final String SORT_ORDER = CN_NAME + " DESC";

    static {
        String createTable = "create table " + TABLE_NAME_DEPARTMENTS + " ( ";
        String c1 = CN_ROW_ID + " integer primary key, ";
        String c2 = CN_DEPT_ID + " text not null, ";
        String c3 = CN_NAME + " text not null )";
        SQL_CREATE_TABLE = createTable + c1 + c2 + c3;
    }

    /**
     * @param pContext
     */
    public DepartmentTable(Context pContext) {
        super(pContext, DbTwoEasySQLiteHelper.getInstance(), TABLE_NAME_DEPARTMENTS);
    }


    @Override
    protected ContentValues getContentValues(DepartmentModel pNewOrUpdatedModel, DepartmentModel pExistingModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CN_DEPT_ID, pNewOrUpdatedModel.getDeptId());
        if (pExistingModel == null
                || pNewOrUpdatedModel.getName().equals(pExistingModel.getName())) {
            contentValues.put(CN_NAME, pNewOrUpdatedModel.getName());
        }
        return contentValues;
    }

    @Override
    protected ArrayList<DepartmentModel> getAllData(String pSelection, String[] pSelectionArgs) {
        ArrayList<DepartmentModel> modelsList = null;
        Cursor cursor = null;
        try {
            // query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
            cursor = mWritableDatabase.query(mTableName, null, pSelection, pSelectionArgs, null, null, SORT_ORDER);

            if (cursor.getCount() <= 0) {
                return null;
            }
            modelsList = new ArrayList<>(cursor.getCount());
            while (cursor.moveToNext()) {
                DepartmentModel model = new DepartmentModel();
                model.setRowId(cursor.getLong(cursor.getColumnIndex(CN_ROW_ID)));
                model.setDeptId(cursor.getString(cursor.getColumnIndex(CN_DEPT_ID)));
                model.setName(cursor.getString(cursor.getColumnIndex(CN_NAME)));
                modelsList.add(model);
            }
        } catch (Exception e) {
            Log.e(mTableName, "getAllData", e);
        } finally {
            closeCursor(cursor);
        }
        return modelsList;
    }

    @Override
    protected DepartmentModel getMatchingData(DepartmentModel pModel) {
        String whereClause = CN_DEPT_ID + " = ?";
        String[] whereArgs = {"" + pModel.getDeptId()};
        ArrayList<DepartmentModel> list = getAllData(whereClause, whereArgs);
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }
}
