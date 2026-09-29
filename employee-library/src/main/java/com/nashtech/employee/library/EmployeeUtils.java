package com.nashtech.employee.library;

public final class EmployeeUtils {

    private EmployeeUtils() {
    }

    public static String getEmployeeDisplayName(Employee employee) {
        return employee.getName() + " (" + employee.getEmail() + ")";
    }
}