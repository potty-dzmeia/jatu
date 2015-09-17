/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testjavatopython;

import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

public class EmployeeFactory {

    public EmployeeFactory() {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("import sys");
        interpreter.exec("print sys.path");
        interpreter.exec("from Employee import Employee");
        jyEmployeeClass = interpreter.get("Employee");
    }

    public EmployeeType create(String first, String last, String id) {
        PyObject employeeObj = jyEmployeeClass.__call__(new PyString(first),
                                                        new PyString(last),
                                                        new PyString(id));
        return (EmployeeType)employeeObj.__tojava__(EmployeeType.class);
    }

    private PyObject jyEmployeeClass;
}