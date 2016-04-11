package com.easylibs.sqlite.example.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.easylibs.sqlite.IModel;
import com.easylibs.sqlite.example.R;
import com.easylibs.sqlite.example.database.EmployeeTable;
import com.easylibs.sqlite.example.model.EmployeeModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EmployeeTable employeeTable = new EmployeeTable(getApplication());

        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setEmpId("emp1");
        employeeModel.setName("Test Name");

        employeeTable.insertOrUpdate(employeeModel);

        printAllEmployees(employeeTable);

        employeeModel.setName(null);
        employeeModel.setAge(20);
        employeeTable.insertOrUpdate(employeeModel);

        printAllEmployees(employeeTable);
    }

    private void printAllEmployees(EmployeeTable employeeTable) {
        ArrayList<IModel> list = employeeTable.getAllData();
        if (list == null || list.isEmpty()) {
            Log.v(LOG_TAG, "No employees found.");
            return;
        }
        for (IModel model : list) {
            Log.v(LOG_TAG, model.toString());
        }
    }
}
