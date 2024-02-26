package org.example.service;
import org.example.*;
import java.util.List;

public interface EmployerGrpcServiceInterface {
    Employer getEmployerById(EmployerRequest request);


    List<Salary> getSalaryById(EmployerRequest request);

    List<EmployerNoSalaries> getEmployers(EmployersPagination request);

    List<EmployerNoSalaries> getEmployersByCriteria(EmployerSearchCriteria request);


    DeleteEmployerResponse deleteEmployerById(EmployerRequest request);


    Employer addEmployer(Employer request);


    Employer updateEmployer(Employer request);


    LastEmployerNumber getLastEmployerNumber(LastEmployerNumber request);

    Count getEmployersByCriteriaCount(EmployerSearchCriteria request);

    Count getEmployersSalariesCount(EmployerRequest request);
}
