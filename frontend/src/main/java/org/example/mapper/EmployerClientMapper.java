package org.example.mapper;

import org.example.Employer;
import org.example.EmployerNoSalaries;
import org.example.EmployerSearchCriteria;
import org.example.Salary;
import org.example.factory.ObservableResourceFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public interface EmployerClientMapper {
    EmployerSearchCriteria toEmployerSearchCriteria(String type, String number, String name, String numberIDE, LocalDate dateAffiliation, LocalDate dateRadiation);
    EmployerSearchCriteria toEmployerSearchCriteria(String type, String number, String name, String numberIDE, LocalDate dateAffiliation, LocalDate dateRadiation, int row, int page);
    Employer toEmployer(String type, String number, String name, String numberIDE, LocalDate dateAffiliation, LocalDate dateRadiation, List<Salary> salaries);

    EmployerNoSalaries toEmployerNoSalaries(Employer employer);

    EmployerNoSalaries toEmployerNoSalariesDisplayType(EmployerNoSalaries employerNoSalaries, Locale locale);
    List<EmployerNoSalaries> toEmployersNoSalariesDisplayType(List<EmployerNoSalaries> employerNoSalaries, Locale locale);
    EmployerNoSalaries toEmployerNoSalariesDatabaseDefined(EmployerNoSalaries employerNoSalaries, Locale locale);
    Employer toEmployer(Long id, String type, String number, String name, String numberIDE, LocalDate dateAffiliation, LocalDate dateRadiation, List<Salary> salaries);
}
