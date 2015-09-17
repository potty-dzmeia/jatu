/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testjavatopython;

public class Main2{

    public static void main(String args[]) {

        JythonObjectFactory factory = new JythonObjectFactory(EmployeeType.class, "Employee", "Employee");

        EmployeeType employee = (EmployeeType) factory.createObject("Josh", "Juneau", "1");

        for(int i=0;i<1000; i++)
        {
            System.out.println(employee.getEmployeeFirst() + " " +
            employee.getEmployeeLast()+ " " +
            employee.getEmployeeId());
        }
    }

}
