package com.company.employee;

import java.time.LocalDate;

public class Employee {

    public String name;
    public String jobTitle;
    public LocalDate hireDate;
    public int annualSalary;

    public Employee(String name, String jobTitle, LocalDate hireDate, int annualSalary) {
        this.name = name;
        this.jobTitle = jobTitle;
        this.hireDate = hireDate;
        this.annualSalary = annualSalary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public int getAnnualSalary() {
        return annualSalary;
    }

    public void setAnnualSalary(int annualSalary) {
        this.annualSalary = annualSalary;
    }
}
