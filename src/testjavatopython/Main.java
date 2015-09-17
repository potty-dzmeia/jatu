/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testjavatopython;


public class Main
{

    private static void print(EmployeeType employee) {
        System.out.println("Name: " + employee.getEmployeeFirst() + " "
                + employee.getEmployeeLast());
        System.out.println("Id: " + employee.getEmployeeId());
    }

    public static void main(String[] args) {
        EmployeeFactory factory = new EmployeeFactory();
        EmployeeType employee = factory.create("Josh", "Juneau", "1");
        
        for(int i=0;i<1000; i++)
        {
            System.out.println(employee.getEmployeeFirst() + " " +
            employee.getEmployeeLast()+ " " +
            employee.getEmployeeId());
        }
    }
}