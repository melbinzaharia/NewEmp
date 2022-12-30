package hostbook.employeemanager.employeemanager.repo;


import hostbook.employeemanager.employeemanager.entity.Employee;
import hostbook.employeemanager.employeemanager.entity.PaginationCriteria;
import hostbook.employeemanager.employeemanager.exception.EmpException;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface EmployeeCustomRepo {


    Employee findEmployeeByEmpcode(String empc,Integer id) ;
    List<Employee> findAll(PaginationCriteria paginationCriteria) throws EmpException;

}
