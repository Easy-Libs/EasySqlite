package com.easylibs.sqlite.example.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.easylibs.sqlite.BaseTable;
import com.easylibs.sqlite.IModel;
import com.easylibs.sqlite.example.R;
import com.easylibs.sqlite.example.database.DepartmentTable;
import com.easylibs.sqlite.example.database.EmployeeTable;
import com.easylibs.sqlite.example.model.DepartmentModel;
import com.easylibs.sqlite.example.model.EmployeeModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EmployeeTable employeeTable = new EmployeeTable(this);

        Log.v(LOG_TAG, "Employees Count: " + employeeTable.getRowsCount());

        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setEmpId("emp1");
        employeeModel.setName("Test Name");

        employeeTable.insertOrUpdate(employeeModel);

        printRows(employeeTable);

        employeeModel.setName(null);
        employeeModel.setAge(20);
        employeeTable.insertOrUpdate(employeeModel);

        printRows(employeeTable);

        DepartmentTable departmentTable = new DepartmentTable(this);
        DepartmentModel departmentModel = new DepartmentModel();
        departmentModel.setDeptId("dep1");
        departmentModel.setName("Development");
        departmentTable.insertOrUpdate(departmentModel);

        printRows(departmentTable);
    }

    private void printRows(BaseTable pAnyTable) {
        ArrayList<IModel> list = pAnyTable.getAllData();
        if (list == null || list.isEmpty()) {
            Log.v(LOG_TAG, "No record found.");
            return;
        }
        for (IModel model : list) {
            Log.v(LOG_TAG, model.toString());
        }
    }
}
