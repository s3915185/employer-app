package org.example.service.employerService.impl;
import org.example.*;
import org.example.mapper.EmployerClientMapper;
import org.example.service.employerService.EmployerClientService;
import org.example.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Service
public class EmployerClientServiceImpl implements EmployerClientService {

    @Autowired
    private EmployerClientMapper employerClientMapper;
    @Autowired
    EmployerServiceGrpc.EmployerServiceBlockingStub employerServiceBlockingStub;
    @Override
    public List<EmployerNoSalaries> getEmployers(EmployersPagination employersPagination) {
        Iterator<EmployerNoSalaries> employerIterator = employerServiceBlockingStub.getEmployers(EmployersPagination.getDefaultInstance());
        return Utils.iteratorToList(employerIterator);
    }

    @Override
    public Employer getEmployerById(Long id) {
        EmployerRequest employerRequest = EmployerRequest.newBuilder().setRequestId(id.toString()).build();
        return employerServiceBlockingStub.getEmployerById(employerRequest);
    }

    @Override
    public List<Salary> getSalaryByEmployerId(Long id, int row, int page) {
        EmployerRequest employerRequest = EmployerRequest.newBuilder()
                .setRequestId(String.valueOf(id)).setMaxRow(row).setPageIndex(page).build();
        Iterator<Salary> salaryIterator = employerServiceBlockingStub.getSalaryById(employerRequest);
        return Utils.iteratorToList(salaryIterator);
    }
    @Override
    public Count getEmployerSalariesCount(Long id) {
        EmployerRequest employerRequest = EmployerRequest.newBuilder().setRequestId(id.toString()).build();
        return employerServiceBlockingStub.getEmployersSalariesCount(employerRequest);
    }

    @Override
    public List<EmployerNoSalaries> getEmployersByCriteria(String type, String number, String name, String numberIDE, LocalDate dateAffiliation, LocalDate dateRadiation, int row, int page) {
        EmployerSearchCriteria employerCriteria = employerClientMapper.toEmployerSearchCriteria(type, number, name, numberIDE, dateAffiliation, dateRadiation, row, page);
        Iterator<EmployerNoSalaries> employerIterator = employerServiceBlockingStub.getEmployersByCriteria(employerCriteria);
        List<EmployerNoSalaries> employerList = Utils.iteratorToList(employerIterator);
        employerList.sort(Comparator.comparing(EmployerNoSalaries::getId));
        return employerList;
    }

    @Override
    public Count getEmployersByCriteriaCount(String type, String number, String name, String numberIDE, LocalDate dateAffiliation, LocalDate dateRadiation) {
        EmployerSearchCriteria employerCriteria = employerClientMapper.toEmployerSearchCriteria(type, number, name, numberIDE, dateAffiliation, dateRadiation);
        return employerServiceBlockingStub.getEmployersByCriteriaCount(employerCriteria);
    }

    @Override
    public LastEmployerNumber getLastEmployerNumber() {
        LastEmployerNumber lastEmployerNumber = LastEmployerNumber.getDefaultInstance();
        return employerServiceBlockingStub.getLastEmployerNumber(lastEmployerNumber);
    }

    @Override
    public DeleteEmployerResponse deleteEmployerById(Long id) {
        EmployerRequest employerRequest = EmployerRequest.newBuilder().setRequestId(id.toString()).build();
        return employerServiceBlockingStub.deleteEmployerById(employerRequest);
    }

    @Override
    public Employer addEmployer(String type, String number, String name, String numberIDE, LocalDate dateAffiliation, LocalDate dateRadiation, List<Salary> salaries) {
        Employer employer = employerClientMapper.toEmployer(type, number, name, numberIDE, dateAffiliation, dateRadiation, salaries);
        return employerServiceBlockingStub.addEmployer(employer);
    }

    @Override
    public Employer updateEmployer(Long id, String type, String number, String name, String numberIDE, LocalDate dateAffiliation, LocalDate dateRadiation, List<Salary> salaries) {
        Employer employer = employerClientMapper.toEmployer(id, type, number, name, numberIDE, dateAffiliation, dateRadiation, salaries);
        return employerServiceBlockingStub.updateEmployer(employer);
    }

}
