package com.easylibs.sqlite.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.easylibs.sqlite.BaseTable;
import com.easylibs.sqlite.example.model.EmployeeModel;

import java.util.ArrayList;

/**
 * Created by sachin.gupta on 05-04-2016.
 */
public class EmployeeTable extends BaseTable<EmployeeModel> {

    private static final String TABLE_NAME_EMPLOYEES = "employee_table";

    public static final String SQL_CREATE_TABLE;

    private static final String CN_EMP_ID = "emp_id";
    private static final String CN_NAME = "name";
    private static final String CN_AGE = "age";

    private static final String SORT_ORDER = CN_NAME + " DESC";

    static {
        String createTable = "create table " + TABLE_NAME_EMPLOYEES + " ( ";
        String c1 = CN_ROW_ID + " integer primary key, ";
        String c3 = CN_EMP_ID + " text not null, ";
        String c2 = CN_NAME + " text not null, ";
        String c4 = CN_AGE + " integer )";
        SQL_CREATE_TABLE = createTable + c1 + c2 + c3 + c4;
    }

    /**
     * @param pContext
     */
    public EmployeeTable(Context pContext) {
        super(pContext, DbOneEasySQLiteHelper.getInstance(), TABLE_NAME_EMPLOYEES);
    }


    @Override
    protected ContentValues getContentValues(EmployeeModel pNewOrUpdatedModel, EmployeeModel pExistingModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CN_EMP_ID, pNewOrUpdatedModel.getEmpId());
        if (pExistingModel == null
                || pExistingModel.getName() == null
                || (pNewOrUpdatedModel.getName() != null && pNewOrUpdatedModel.getName().equals(pExistingModel.getName()))) {
            contentValues.put(CN_NAME, pNewOrUpdatedModel.getName());
        }
        if (pExistingModel == null || pExistingModel.getAge() != pNewOrUpdatedModel.getAge()) {
            contentValues.put(CN_AGE, pNewOrUpdatedModel.getAge());
        }

        return contentValues;
    }

    @Override
    protected ArrayList<EmployeeModel> getAllData(String pSelection, String[] pSelectionArgs) {
        ArrayList<EmployeeModel> modelsList = null;
        Cursor cursor = null;
        try {
            // query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
            cursor = mWritableDatabase.query(mTableName, null, pSelection, pSelectionArgs, null, null, SORT_ORDER);

            if (cursor.getCount() <= 0) {
                return null;
            }
            modelsList = new ArrayList<>(cursor.getCount());
            while (cursor.moveToNext()) {
                EmployeeModel model = new EmployeeModel();
                model.setRowId(cursor.getLong(cursor.getColumnIndex(CN_ROW_ID)));
                model.setEmpId(cursor.getString(cursor.getColumnIndex(CN_EMP_ID)));
                model.setName(cursor.getString(cursor.getColumnIndex(CN_NAME)));
                model.setAge(cursor.getInt(cursor.getColumnIndex(CN_AGE)));
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
    protected EmployeeModel getMatchingData(EmployeeModel pModel) {
        String whereClause = CN_EMP_ID + " = ?";
        String[] whereArgs = {"" + pModel.getEmpId()};
        ArrayList<EmployeeModel> list = getAllData(whereClause, whereArgs);
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }
}
