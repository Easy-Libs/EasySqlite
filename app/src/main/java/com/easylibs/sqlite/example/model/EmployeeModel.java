package com.easylibs.sqlite.example.model;

import com.easylibs.sqlite.IModel;

/**
 * Created by sachin.gupta on 05-04-2016.
 */
public class EmployeeModel implements IModel {

    private long rowId;
    private String empId;
    private String name;
    private int age;

    @Override
    public long getRowId() {
        return rowId;
    }

    @Override
    public void setRowId(long pRowId) {
        rowId = pRowId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return empId + ": " + name + ", " + age;
    }
}
