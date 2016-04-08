package com.easylibs.sqlite.example.data;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.easylibs.sqlite.BaseTable;
import com.easylibs.sqlite.IModel;
import com.easylibs.sqlite.example.model.EmployeeModel;

import java.util.ArrayList;

/**
 * Created by sachin.gupta on 05-04-2016.
 */
public class EmployeeTable extends BaseTable {

    private static final String TABLE_NAME_EMPLOYEES = "employee_table";

    public static final String CREATE_TABLE_EMPLOYEE;

    private static final String CN_EMP_ID = "emp_id";
    private static final String CN_NAME = "name";
    private static final String CN_AGE = "age";

    private static final String SORT_ORDER = CN_NAME + " DESC";

    static {
        String createTable = "create table " + TABLE_NAME_EMPLOYEES + " ( ";
        String c1 = CN_ROW_ID + " integer primary key, ";
        String c3 = CN_EMP_ID + " text not null, ";
        String c2 = CN_NAME + " text not null, ";
        String c4 = CN_AGE + " integer)";
        CREATE_TABLE_EMPLOYEE = createTable + c1 + c2 + c3 + c4;
    }

    /**
     * @param pApplication
     */
    public EmployeeTable(Application pApplication) {
        super(pApplication, TABLE_NAME_EMPLOYEES);
    }


    @Override
    protected ContentValues getContentValues(IModel pModel, IModel pExistingModel) {
        EmployeeModel existingModel = null;
        if (pExistingModel instanceof EmployeeModel) {
            existingModel = (EmployeeModel) pExistingModel;
        }
        EmployeeModel newOrUpdatedModel;
        if (pModel instanceof EmployeeModel) {
            newOrUpdatedModel = (EmployeeModel) pModel;
        } else {
            return null;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(CN_EMP_ID, newOrUpdatedModel.getEmpId());
        if (existingModel == null
                || existingModel.getName() == null
                || (newOrUpdatedModel.getName() != null && newOrUpdatedModel.getName().equals(existingModel.getName()))) {
            contentValues.put(CN_NAME, newOrUpdatedModel.getName());
        }
        if (existingModel == null || existingModel.getAge() != newOrUpdatedModel.getAge()) {
            contentValues.put(CN_AGE, newOrUpdatedModel.getAge());
        }

        return contentValues;
    }

    @Override
    protected ArrayList<IModel> getAllData(String pSelection, String[] pSelectionArgs) {
        ArrayList<IModel> modelsList = null;
        Cursor cursor = null;
        try {
            // query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
            cursor = mWritableDatabase.query(mTableName, null, pSelection, pSelectionArgs, null, null, SORT_ORDER);

            if (cursor.getCount() <= 0) {
                return null;
            }
            modelsList = new ArrayList<IModel>(cursor.getCount());
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
    protected IModel getMatchingData(IModel pModel) {
        EmployeeModel empModel;
        if (pModel instanceof EmployeeModel) {
            empModel = (EmployeeModel) pModel;
        } else {
            return null;
        }
        String whereClause = CN_EMP_ID + " = ?";
        String[] whereArgs = {"" + empModel.getEmpId()};
        ArrayList<IModel> list = getAllData(whereClause, whereArgs);
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }
}
