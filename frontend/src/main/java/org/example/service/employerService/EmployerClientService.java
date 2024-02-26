package org.example.service.employerService;

import org.example.*;

import java.time.LocalDate;
import java.util.List;

public interface EmployerClientService {
    List<EmployerNoSalaries> getEmployers(EmployersPagination employersPagination);
    Employer getEmployerById(Long id);
    List<Salary> getSalaryByEmployerId(Long id, int row, int page);
    Count getEmployerSalariesCount(Long id);

    List<EmployerNoSalaries> getEmployersByCriteria(String type, String number, String name, String numberIDE, LocalDate dateAffiliation, LocalDate dateRadiation, int row, int page);
    Count getEmployersByCriteriaCount(String type, String number, String name, String numberIDE, LocalDate dateAffiliation, LocalDate dateRadiation);
    LastEmployerNumber getLastEmployerNumber();

    DeleteEmployerResponse deleteEmployerById(Long id);

    Employer addEmployer(String type, String number, String name, String numberIDE, LocalDate dateAffiliation, LocalDate dateRadiation, List<Salary> salaries);

    Employer updateEmployer(Long id, String type, String number, String name, String numberIDE, LocalDate dateAffiliation, LocalDate dateRadiation, List<Salary> salaries);
}
