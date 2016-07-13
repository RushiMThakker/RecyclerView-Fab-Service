package Model;

import java.util.Date;

/**
 * Created by Astound Rushi on 13-07-2016.
 */
public class Employee
{
    String employeeName,employeeDesignation;
    Date joiningDate;

    public Employee(String name,String designation,Date joinDate)
    {
        employeeName=name;
        employeeDesignation=designation;
        joiningDate=joinDate;
    }
    public String getEmployeeDesignation()
    {
        return employeeDesignation;
    }

    public void setEmployeeDesignation(String employeeDesignation)
    {
        this.employeeDesignation = employeeDesignation;
    }

    public String getEmployeeName()
    {
        return employeeName;
    }

    public void setEmployeeName(String employeeName)
    {
        this.employeeName = employeeName;
    }

    public Date getJoiningDate()
    {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate)
    {
        this.joiningDate = joiningDate;
    }
}
