package com.easylibs.sqlite.example.model;

import com.easylibs.sqlite.IModel;

/**
 * Created by sachin.gupta on 05-04-2016.
 */
public class DepartmentModel implements IModel {

    private long rowId;
    private String deptId;
    private String name;

    @Override
    public long getRowId() {
        return rowId;
    }

    @Override
    public void setRowId(long pRowId) {
        rowId = pRowId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return deptId + ": " + name;
    }
}
